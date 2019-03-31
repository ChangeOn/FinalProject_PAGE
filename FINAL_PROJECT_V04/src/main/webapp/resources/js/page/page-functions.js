/* 주요 기능별 메소드 정의 */

/* 호출 함수 관련 */
function $F(caller) {
     var f = arguments.callee.caller;
     if(caller) f = f.caller;
     var pat = /^function\s+([a-zA-Z0-9_]+)\s*\(/i;
     pat.exec(f);
     var func = new Object();
     func.name = RegExp.$1;
     return func;
}

/*/호출 함수 관련 */

/* 주어진 객체의 에디터 타입 찾기 */
function find_editor_type(obj) {
	
	if(obj.hasClass("text")) {
		return "text";
	}
	else if(obj.hasClass("table")) {
		return "table";
	}
}
/*/주어진 객체의 에디터 타입 찾기 */

/* 상단 네비게이션 바 작업 전 초기화 설정 */
function before_alert() {
	
	// 경고창이 띄워져 있는 경우
	if(	$("[id*=warnning][class*=show]").length>0){
		$("[id*=warnning][class*=show]").collapse('hide');
	}
	// 타 상세 메뉴바 숨기기
	else if($("[id*=details][class*=show]").length > 0) {

		$("[id*=details][class*=show]").collapse('hide');
	}
	else {
		return;
	}
}
/* /상단 네비게이션 바 작업 전 초기화 설정 */

/* 중복 에디터 사용 여부 파악 */
function check_same_editor(editor) {
	
	editor.parent("[class~=draggable]").addClass("checking");
	
	// 이미 실행중인 동일 에디터 파악
	var editor_type = $("[class~=draggable][class~=checking]").attr('class').split(' ');
	var same_editor = $("[class~=draggable][class~="+editor_type[1]+"]").has("[class~=editor]");

	console.log(editor_type[1]);
	if(same_editor.length > 0) {
		console.log('중복 에디터 발견');
		
		// 경고창 표시
		$("#same-editor-warnning").collapse('show');
		
		editor.parent("[class~=draggable]").removeClass("checking");
		return same_editor;
	}
	else {
		console.log('중복 에디터 미발견');
		
		editor.parent("[class~=draggable]").removeClass("checking");
		return null;
	}
}
/*/중복 에디터 사용 여부 파악 */

/* 에디터 사용 여부 스위치 설정 */
function editor_switch(ONOFF, obj) {
	// 특정 객체 적용
	if(obj!=null) {
		if(ONOFF == 'ON') {
			console.log(obj.attr('class'));
			
			// 파라미터로 전해받은 객체에 대한 에디터 찾기
			// 텍스트 에디터
			if(obj.hasClass("text")) {
				
				console.log("텍스트 에디터 실행");
				
				obj.find("[id^=jqte]").jqte();
				resizable_switch('ON', $(".jqte"));
				
				// 에디터 실행시 부모 DIV 드래그 기능 해제
				draggable_switch('OFF', obj.parent("[class~=draggable]"));
			}
			// 테이블 에디터
			else if(obj.hasClass("table")) {
				
				console.log("테이블 에디터");
				
				obj.SetEditable({
			        
				       $addButton: true
					});
			}
			
			// 에디터 모드 상태 전환
			obj.removeClass("OFF").removeClass("plain");
			obj.addClass("ON").addClass("editor");
		}
		else if(ONOFF == 'OFF') {
			obj.removeClass("ON").removeClass("editor");
			obj.addClass("OFF").addClass("plain");
		}
	}
}
/*/에디터 사용 여부 스위치 설정 */

/* Draggable DIV 스위치 설정 */
function draggable_switch(ONOFF, obj) {
	
	// DIV 전체 적용
	if(obj==null) {
		if(ONOFF == 'ON') {
			$('[class*=draggable]').draggable();
			$('[class*=draggable]').draggable(
					{ 
						disabled : false
						,containment: ".container-fluid"
			});
		}
		else if(ONOFF == 'OFF') {
			$("[class^=draggable]").draggable();
			$("[class^=draggable]").draggable("option", "disabled", true)
		}
	}
	// 특정 객체 적용
	else {
		if(ONOFF == 'ON') {
			obj.draggable();
			obj.draggable(
					{ 
						disabled : false
						,containment: ".container-fluid"
			});
		}
		else if(ONOFF == 'OFF') {
			obj.draggable();
			obj.draggable("option", "disabled", true)
		}
	}
}
/*/Draggable DIV 스위치 설정 */

/* Resizable DIV 스위치 설정 */
function resizable_switch(ONOFF, obj) {
	
	// DIV 전체 적용
	if(obj==null) {
		if(ONOFF == 'ON') {
			
			$("[class~=ui-resizable]").resizable();
			$("[class~=ui-resizable]").resizable(
					{
						disabled : false
			});
		}
		else if(ONOFF == 'OFF') {
		
			$("[class~=ui-resizable]").resizable();
			$("[class~=ui-resizable]").resizable("option", "disabled", true);
		}
	}
	// 특정 객체 적용
	else {
		if(ONOFF == 'ON') {
			
			obj.resizable();
			obj.resizable(
					{
						disabled : false
			});
		}
		else if(ONOFF == 'OFF') {
		
			obj.resizable();
			obj.resizable("option", "disabled", true);
		}
	}
}
/*/Resizable DIV 스위치 설정 */

/* 상단 경고창 표시 관련 */
var pending = {};
function mySetTimeout(callback, delay) {
	
  var t;
  t = setTimeout(function() {
	  delete pending[t];
	  callback()}, delay)
	  
  pending[t]=1;
}

function clearAllTimeouts() {
	
  for (var t in pending) if (pending.hasOwnProperty(t)) {
	  
	  console.log(pending);
	  console.log(pending[t]);
	 
	  clearTimeout(t);
	  delete pending[t];
  }
}
/* /상단 경고창 표시 관련 */

/*/주요 기능별 메소드 정의 */

/* 페이지 편집 모드 스위치 */
function page_edit_mode_switch(ONOFF) {
	
	var clicked_button = $("[class~=edit-mode-switch]");
	
	/* 페이지 편집 모드 ON */
	if(ONOFF == 'ON') {
		console.log("페이지 편집 모드 시작");

		clicked_button.blur();

		//버튼 효과 및 문구 변경
		clicked_button.removeClass("btn-secondary");
		clicked_button.addClass("btn-light");
		clicked_button.html('종료하기');
		
		//페이지 편집 메뉴창 토글
		$("[class~=d-flex]").toggleClass("toggled");
		
		// 드래그 및 리사이즈 기능 전체 설정
		draggable_switch('ON');
		resizable_switch('ON');
	}
	/*/페이지 편집 모드 ON */
	/*페이지 편집 모드 OFF */
	else if(ONOFF == 'OFF') {
	
		console.log("페이지 편집 모드 종료 시도");
		
		clicked_button.blur();
		
		// 종료 전 다른 에디터가 실행 중인 경우
		if($(document).find("[class~=editor]").length > 0) {
			console.log("종료 전 에디터가 실행 중인 경우")
			
			// 경고창 표시
			$("#using-editor-warnning").collapse('show');
			return;
		}
		// 실행중인 에디터가 없는 경우
		else{
			console.log("실행 중인 에디터가 없는 경우")
			
			//버튼 효과 및 문구 변경
			clicked_button.removeClass("btn-light");
			clicked_button.addClass("btn-secondary");
			clicked_button.html('시작하기');
			
			//페이지 편집 메뉴창 토글
			$("[class~=d-flex]").toggleClass("toggled");
			
			// 전체 드래그 기능 해제
			draggable_switch('OFF');
			resizable_switch('OFF');
			
			console.log("페이지 요소의 드래그 기능 제거 완료");
			console.log("페이지 편집 모드 종료 완료");
			
			
			/* 
			 * 페이지 편집 모드 종료시 완성된 페이지의 모습을 
			 * 데이터베이스에 저장하기 위해 HTML을 JSON의 형태로 전송한다.
			 * */
			
			var actived_page_tab_name = $("[class~=page-tab-group]")
			.find("[class~=actived]").html();
			console.log(actived_page_tab_name.length);
			
			//편집된 HTML을 담고 있는 객체
			var $container = $(".container-fluid").clone();
			//JSON 오브젝트 생성
		    var json_object = new Object();
		    //JSON 오브젝트에 PageVO 모델 바인딩
		    json_object.page_name = actived_page_tab_name
		    json_object.page_content = $container[0].innerHTML;
		    
		    //AJAX를 통해 JSON 오브젝트 전달
		    
		    $.ajax({
		    	    type : "POST",
		    	    dataType : 'json',
		    	    data : json_object,
		    	    url : "/page/save",
		    	    success : function(data) {
		    	       
		    	    	if(data.message = "true") {
		    	    		
		    				// 알림창 표시
		    				$("#update-tab-warnning").collapse('show');
		    	    	}
		    	    }
		    });
		    
		}
	}
	/*/페이지 편집 모드 OFF */
}
/*/페이지 편집 모드 스위치 */

$(function() {
	
	$("[class~=container-fluid]")
	/* 윈도우 스크롤 감지 이벤트 */
	.on('scroll', function() {
		
		var bookmark_scrolltop = $("[id~=input-bookmark-details-warnning]").find("[id~=bookmark_scrolltop]");
		var page_no = $("[id~=input-bookmark-details-warnning]").find("[id~=page_no]");
		var actived_page_tab_no = parseInt($("[class~=page-tab-group]").find("[class~=actived]").attr("id"));
		
		/* 스크롤이 이동할 때마다 북마크 생성 폼의 
		 * INPUT 값을 갱신한다. */
		bookmark_scrolltop.val($(".container-fluid").scrollTop());
		page_no.val(actived_page_tab_no);
	})
	
	$(document)
	.ready(function() {
		
		/* 
		 * 페이지가 로드될때 메인페이지 정보를 불러온다.
		 * 동작 화면의 크기가 모바일일 경우 데스크톱 사용을 권장하는 알림을 띄우고,
		 * 인덱스 페이지로 돌려보낸다.
		 * */
		
		if(!($(window).width()>=1024)) {
			
			alert("모바일 환경으로는 본서비스를 이용하기 어려우니," 
					+" 데스크톱 사용을 권장합니다.");
			window.location.href="/";
		}
		
		//메인 페이지 탭 비활성화
		var main_page_tab = $("[class~=page-tab-group]").find("[class~=main]");
		main_page_tab.removeClass("btn-light")
		.addClass("btn-primary").addClass("actived");
		
		//북마크 추가를 위한 메인 페이지 탭 번호 설정
		var page_no = $("[id~=input-bookmark-details-warnning]").find("[id~=page_no]");
		var actived_page_tab_no = parseInt($("[class~=page-tab-group]").find("input[id~=page_no]").val());
		page_no.val(actived_page_tab_no);
		
		//JSON 오브젝트 생성
	    var json_object = new Object();
	    
	    //JSON 오브젝트에 PageVO 모델 바인딩
	    json_object.page_name = main_page_tab.html();
	    
	    //AJAX를 통해 JSON 오브젝트 전달
	    $.ajax({
	    	    type : "POST",
	    	    dataType : 'json',
	    	    data : json_object,
	    	    url : "/page/load",
	    	    success : function(data) {
	    	       
	    	    	//기존 페이지 컨텐츠를 제거하고 불러온 컨텐츠를 적용시킨다.
	    	    	$("[class~=container-fluid]").empty();
	    	    	$("[class~=container-fluid]").append(data.page_content);
	    	    }
	    });
	})
	/* COLLAPSE 관련 */
	// 전체 토글 실행 시
	.on("show.bs.collapse", ".collapse" , function() {
		
		console.log("상단 네비게이션 바 초기화");
		
		// 상단 네비게이션 바 초기화
		before_alert();
	})
	// 토글 진행 후
	.on('shown.bs.collapse', function () {

		clearAllTimeouts();
		mySetTimeout(before_alert, 10000);
	})
	/*/COLLAPSE 관련 */
	
	// 더블 클릭 이벤트 설정
	.on("dblclick", ".draggable", function() {
		
		if(event.stopImmediatePropagation) event.stopImmediatePropagation(); //MOZILLA
		else event.isImmediatePropagationEnabled = false; //IE
		
		if(event.preventDefault) event.preventDefault(); //MOZILLA
		else event.returnValue = false; //IE
		
		// 에디터가 켜져있지 않을 경우, 더블 클릭시 에디터 실행 및 ID 값 변경
		if($(this).find("*").hasClass("OFF")) {
			
			// 더블클릭 상태로 전환
			$(this).addClass("dblclicked");
				
			// 에디터는 한 개만 운용 가능하도록 설정
			// 동일 에디터가 실행 중이지 않는 경우
			if(check_same_editor($(this).find("[class~=OFF]"))==null){
				
				console.log("동일 에디터가 실행 중이지 않는 경우");
				
				// 텍스트 에디터 실행
				if($(this).hasClass("text")) {
					
					// 설정값 변경 및 스위치 ON
					editor_switch('ON', $(this).find('[class~=plain]'));
				}
				// 테이블 에디터 실행
				else if($(this).hasClass("table")) {
					
					// 설정값 변경 및 스위치 ON
					editor_switch('ON', $(this).find("[class~=plain]"));
				}
				
				// 더블클릭 상태 해제
				$("[class~=draggable][class~=dblclicked]").removeClass("dblclicked");
			}
			// 이미 동일 에디터가 실행 중인 경우
			else {
				return;
			}
		}
		// 에디터가 켜져있을 경우, 더블 클릭시 에디터에 포커스
		else if($(this).find("*").hasClass("ON")) {
			
			// 텍스트 에디터 더블 클릭 시
			if($(this).hasClass("text")){
				
				console.log("텍스트 에디터 포커스");
				$(".jqte_editor").focus();
			}
			//테이블 더블 클릭 시
			else if($(this).hasClass("table")) {
				
				console.log('테이블 에디터 포커스');
				$(this).find("[class~=editor][class~=table]").focus();
				
			}
			
			// 더블클릭 상태 해제
			$("[class~=draggable][class~=dblclicked]").removeClass("dblclicked");
		}
	})
	// 페이지 편집 토글 버튼 클릭 시
	.on("click", ".edit-mode-switch", function(e) {
		
		event.preventDefault();
		
		if($(this).hasClass("btn-light")) {
			// 알림창 표시
			$("#page-edit-mode-off-warnning").collapse('show');
		}
		else {
			// 알림창 표시
			$("#page-edit-mode-on-warnning").collapse('show');
		}
	})
	// 페이지 탭 추가 생성
	.on("click", "#new-page", function(e) {
		
		var page_default_name = ["첫번째", "두번째", "세번째"];
		var now_page_default_name = page_default_name[$("[class~=page-tab-group]").find("[class~=page-tab]").length];
		var now_page_tab_size = $("[class~=page-tab-group]").find("[class~=page-tab]").length;
		$(this).blur();
		event.preventDefault();
		
		console.log(now_page_tab_size);
		//페이지 탭 생성은 총 3개까지만 한정
		if(now_page_tab_size < 3) {
			
			//JSON 오브젝트 생성
		    var json_object = new Object();
		    //JSON 오브젝트에 PageVO 모델 바인딩
		    json_object.page_name = now_page_default_name
			
		    //AJAX를 통해 유저별 페이지 탭 정보 저장
		    $.ajax({
		    	    type : "POST",
		    	    dataType : 'json',
		    	    data : json_object,
		    	    async: false,
		    	    url : "/page/new_tab",
		    	    success : function(data) {
		    	       
		    	    	if(data.message == "true")
		    			$("[id~=new-page]").before(
		    					'<button type="button" class="btn btn-light page-tab">'
		    					+ page_default_name[$("[class~=page-tab-group]").find("[class~=page-tab]").length]
		    					+'</button>');
		    	    }
		    });
			
			//페이지 추가 생성을 막기 위해 마지막 페이지 탭 생성시 버튼 삭제
			if(now_page_tab_size == 2) {
				$("[id=new-page]").remove();
			}
		}
	})
	// 페이지 탭 클릭 이벤트
	.on("click", ".page-tab", function(e) {
		
		event.preventDefault();
		$(this).blur();
		
		/* 
		 * 페이지 편집 모드가 활성화 되어 있는 경우 경고창 출력
		 * */
		
		if($("[class~=d-flex]").hasClass("toggled")) {
			
			//이미 활성화 되어있는 페이지를 클릭했을 경우
			if($(this).hasClass("actived")) {
				
				return;
			} 
			//다른 페이지 탭을 클릭했을 경우
			else {
				
				//이미 활성화 되어있는 페이지 탭 비활성화
				$("[class~=page-tab-group]").find("[class~=actived]")
				.removeClass("btn-primary").removeClass("actived")
				.addClass("btn-light");
				
				$(this).removeClass("btn-light")
				.addClass("btn-primary").addClass("actived");
				
				//북마크 생성을 위한 페이지 탭 번호 갱신
				var actived_page_tab_no = parseInt($("[class~=page-tab-group]").find("[class~=actived]").attr("id"));
				var page_no = $("[id~=input-bookmark-details-warnning]").find("[id~=page_no]");
				page_no.val(actived_page_tab_no);
				
				var bookmark_scrolltop = $("[id~=input-bookmark-details-warnning]").find("[id~=bookmark_scrolltop]");
				bookmark_scrolltop.val(0);
				
				/* 
				 * 다른 페이지 탭을 클릭했을 때, 해당 페이지에 저장해놓은 상태를 불러오기 위해
				 * AJAX를 통해 페이지 정보를 받아와서 HTML을 수정한다.
				 * */
				
				//JSON 오브젝트 생성
			    var json_object = new Object();
			    
			    //JSON 오브젝트에 PageVO 모델 바인딩
			    json_object.page_name = $(this).html();
			    
			    //AJAX를 통해 JSON 오브젝트 전달
			    $.ajax({
			    	    type : "POST",
			    	    dataType : 'json',
			    	    data : json_object,
			    	    url : "/page/load",
			    	    success : function(data) {
			    	       
			    	    	//기존 페이지 컨텐츠를 제거하고 불러온 컨텐츠를 적용시킨다.
			    	    	$("[class~=container-fluid]").empty();
			    	    	$("[class~=container-fluid]").append(data.page_content);
			    	    }
			    });
			}
		}
		//페이지 편집 모드가 활성화중인 경우
		else {
		
			// 경고창 표시
			$("#other-tab-warnning").collapse('show');
			return;
		}
	})
});

function Page_Edit_Mode_ON_Warnning(YN) {
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 페이지 
	if(YN == 'Y') {
		
		page_edit_mode_switch('ON');
	}
	else {
		return;
	}
}

function Page_Edit_Mode_OFF_Warnning(YN) {
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 페이지 
	if(YN == 'Y') {
		
		page_edit_mode_switch('OFF');
	}
	else {
		return;
	}
}

function Using_Editor_Warnning(YN) {
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 비저장 종료 선택
	if(YN == 'Y') {
		
		// 실행중인 에디터 전체 종료
		$(document).find("[class~=editor]").closest("[class^=draggable]").remove();
		
		// 포커스 제거 및 편집 모드 토글 버튼 색상 변경
		$("#menu-toggle").attr('class', 'btn btn-secondary');
		$("#wrapper").toggleClass("toggled");
		
		// 전체 드래그 기능 및 리사이즈 해제
		draggable_switch('OFF');
		resizable_switch('OFF');
		console.log("페이지 요소의 드래그 기능 제거 완료");
		console.log("페이지 편집 모드 종료 완료");
	}
	// 비저장 종료 거부
	else {
		
		console.log("페이지 편집 모드 종료 거부");
		return;
	}
}

function Same_Editor_Warnning(YN, id) {

	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 비저장 종료 선택
	if(YN == 'Y') {
		
		// 이미 실행중인 동일 에디터 제거
		check_same_editor($("[class~=draggable][class~=dblclicked]").find('.plain')).remove();
		
		// 설정값 변경 및 스위치 ON
		editor_switch('ON', $("[class~=draggable][class~=dblclicked]").find('.plain'));
		
		// 더블클릭 상태 해제
		$("[class~=draggable][class~=dblclicked]").removeClass("dblclicked");

	}
	// 비저장 종료 거부
	else {
		console.log("페이지 편집 모드 종료 거부");
		return;
	}
}

function Show_Add_Bookmark_Warnning() {
	
	// 경고창 표시
	$("#add-bookmark-warnning").collapse('show');
	return;
}
function Add_Bookmark_Warnning(YN) {
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 북마크 생성
	if(YN == 'Y') {
		
		// 경고창 표시
		$("#input-bookmark-details-warnning").collapse('show');
		return;
	}
}
function Show_Bookmark_Page(bookmark_no) {
	
	/* INPUT 값으로 미리 지정되어 있는 북마크 번호를 기준으로,
	 * 페이지 정보를 조회한다. */
	
	/* 
	 * 다른 페이지 탭을 클릭했을 때, 해당 페이지에 저장해놓은 상태를 불러오기 위해
	 * AJAX를 통해 페이지 정보를 받아와서 HTML을 수정한다.
	 * */
	
	//JSON 오브젝트 생성
    var json_object = new Object();
    
    //JSON 오브젝트에 PageVO 모델 바인딩
    json_object.bookmark_no = bookmark_no;
    
    //AJAX를 통해 JSON 오브젝트 전달
    $.ajax({
    	    type : "POST",
    	    dataType : 'json',
    	    data : json_object,
    	    url : "/bookmark/load",
    	    success : function(data) {
    	       
    	    	console.log(data.page_content);
    	    	console.log(data.bookmark_scrolltop);
    	    	
				//이미 활성화 되어있는 페이지 탭 비활성화
				$("[class~=page-tab-group]").find("[class~=actived]")
				.removeClass("btn-primary").removeClass("actived")
				.addClass("btn-light");
				
				$("[class~=page-tab-group]").find("[id~="+data.page_no+"]").removeClass("btn-light")
				.addClass("btn-primary").addClass("actived");
				
	   	    	//기존 페이지 컨텐츠를 제거하고 불러온 컨텐츠를 적용시킨다.
    	    	$("[class~=container-fluid]").empty();
    	    	$("[class~=container-fluid]").append(data.page_content);
    	    }
    });
}

/*/북마크 관련 */

/* 텍스트 관련 */
function Add_PlainText() {

	// 텍스트 에디터 플러그인 JQTE
	// 첫 에디터 생성시
	if ($("[class~=editor][class~=text]").length == 0) {
		console.log("텍스트 에디터 생성");

		$(".container-fluid").prepend(
				"<div class='draggable text'>"
						+"<div class='editor text ON'>" 
						+"<div id='jqte-div'>"
						+"</div></div></div>").trigger("create");

		// 텍스트 에디터 실행
		$("#jqte-div").jqte();
		
		// 에디터에 리사이즈 기능 설정
		resizable_switch('ON', $(".jqte"));

		// 에디터가 종료되었을 때 드래그 가능한 DIV OFF 설정
		draggable_switch('OFF',$("[class~=editor][class~=text]").closest('[class*=draggable]'));

	}
	// 추가 텍스트 에디터 생성시
	else {
		console.log("추가 텍스트 에디터 생성 시도");
		
		// 경고창 표시
		$("#create-editor-warnning").collapse('show');
		$("#create-editor-warnning").focus();
		return;
	}
}
/*/텍스트 관련 */

/* 테이블 관련 */
function Add_Table() {
	
	// 첫 에디터 생성시
	if ($("[class~=editor][class~=table]").length == 0) {
		
		$(".container-fluid").prepend(
				"<div class='draggable table border border-dark rounded'>"
					+"<table class='table table-hover table-hover editor ON'>"
							+"<thead><tr>"
								+"<th>Column 1</th>"
								+"<th>Column 2</th>"
								+"<th>Column 3</th>"     
								+"<th>Column 4</th>" 
							+"</tr></thead>"
							+"<tbody><tr>"
					            +"<td>Data 1</td>"
					            +"<td>Data 2</td>"
					            +"<td>Data 3</td>"
					            +"<td>Data 4</td>"
					    +"</tr></tbody></table>"
				+"</div>"
						);
		
		// 테이블 에디터 실행
		$('[class~=editor][class~=table]').SetEditable({
	        
	       $addButton: true
		});
		
		// 테이블 에디터 드래그 및 리사이즈 기능 설정
		draggable_switch('ON', $('[class~=editor][class~=table]').closest('[class*=draggable]'));
		resizable_switch('ON', $('[class~=editor][class~=table]'));
	}
	// 추가 텍스트 에디터 생성시
	else {
		console.log("추가 테이블 에디터 생성 시도");
		
		// 경고창 표시
		$("#create-editor-warnning").collapse('show');
		return;
	}
}

// 테이블 최소 행 유지 요구 에러
function Table_Row_Warnning(YN) {
	console.log("테이블 최소 행 유지 요구 에러 발생");
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 테이블 유지 선택
	if(YN == 'Y') {
		
		console.log("테이블 및 편집창 유지");
		return;
	}
	// 테이블 삭제 선택
	else {
		
		// 테이블 및 편집창 제거
		$('[class~=editor][class~=table]').parent('[class^=draggable]').remove();
		console.log("테이블 및 편집창 제거");
		return;
	}
	
}

// 테이블 편집 종료 시 테이블 상태 선택 안내
function Table_Destroy_Warnning(YN) {
	console.log("테이블 편집 종료 시 테이블 상태 선택 안내");
	
	// 상단 네비게이션 바 초기화
	before_alert();
	
	// 테이블 유지 선택
	if(YN == 'Y') {
		console.log("테이블 및 편집창 유지");
		
		// 에디터 요소 제거
		$("[name=th-buttons]").remove();
		$("[name=buttons]").remove();
		
		editor_switch('OFF', $('[class~=editor][class~=table]'))
		return;
	}
	// 테이블 삭제 선택
	else {
		
		// 테이블 및 편집창 제거
		$('[class~=editor][class~=table]').parent('[class^=draggable]').remove();
		console.log("테이블 및 편집창 제거");
		return;
	}
}
/* /테이블 관련 */
