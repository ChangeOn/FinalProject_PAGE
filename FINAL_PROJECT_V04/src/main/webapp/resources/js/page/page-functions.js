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

$(function() {
	
	$(document)
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
	.on("click", "#menu-toggle", function(e) {
		console.log("페이지 편집 모드 시작");

		e.preventDefault();
		$(this).blur();
		
		// 페이지 편집 모드 시작
		if($(this).hasClass('btn btn-secondary')) {
			$(this).attr('class', 'btn btn-light');
			$(this).html('종료하기');
			
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
			
			console.log("페이지 요소 드래그 기능 설정");
			
			// 드래그 및 리사이즈 기능 전체 설정
			draggable_switch('ON');
			resizable_switch('ON');
			
		//페이지 편집 모드 종료
		} else {
			console.log("페이지 편집 모드 종료 시도");
			
			// 종료 전 다른 에디터가 실행 중인 경우
			if($(document).find("[class~=editor]").length > 0) {
				console.log("종료 전 에디터가 실행 중인 경우")
				
				// 경고창 표시
				$("#using-editor-warnning").collapse('show');
			}
			// 실행중인 에디터가 없는 경우
			else{
				console.log("실행 중인 에디터가 없는 경우")
				
				// 포커스 제거 및 편집 모드 토글 버튼 색상 변경
				$("#menu-toggle").attr('class', 'btn btn-secondary');
				$("#menu-toggle").html('시작하기');
				$("#wrapper").toggleClass("toggled");
				
				// 전체 드래그 기능 해제
				draggable_switch('OFF');
				resizable_switch('OFF');
				
				console.log("페이지 요소의 드래그 기능 제거 완료");
				console.log("페이지 편집 모드 종료 완료");
				
				
				/* 
				 * 페이지 편집 모드 종료시 완성된 페이지의 모습을 
				 * 데이터베이스에 저장하기 위해 HTML을 JSON의 형태로 전송한다.
				 * */
				
				//편집된 HTML을 담고 있는 객체
				var $container = $(".container-fluid").clone();
				//JSON 오브젝트 생성
			    var json_object = new Object();
			    //JSON 오브젝트에 PageVO 모델 바인딩
			    json_object.page_name = "test_name"
			    json_object.page_content = $container[0].innerHTML;
			    
			    //AJAX를 통해 JSON 오브젝트 전달
			    $.ajax({
			    	    type : "POST",
			    	    dataType : 'json',
			    	    data : json_object,
			    	    url : "/page/save",
			    	    success : function() {
			    	       
			    	    	console.log("success");
			    	    }
			    });
			}
		}
	})
	// 페이지 탭 추가 생성
	.on("click", "#new-page", function(e) {
		
		var page_default_name = ["두번째", "세번째"];
		var now_page_default_name = page_default_name[$("[class~=page-tab-group]").find("[class~=page-tab]").length-1];
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
		    					+ page_default_name[$("[class~=page-tab-group]").find("[class~=page-tab]").length-1]
		    					+'</button>');
		    	    }
		    });
			
			//페이지 추가 생성을 막기 위해 마지막 페이지 탭 생성시 버튼 삭제
			if(now_page_tab_size == 2) {
				$("[id=new-page]").remove();
			}
		}
	})
});

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
