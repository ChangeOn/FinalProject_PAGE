$(document).ready(function() {
	
	// TODO: 회원가입 창 인풋 활성화시 투명도 제거

	/* 상단 네비게이션 스크롤 시 상단 고정 */
	var menu = $('.navbar');
	var origOffsetY = menu.offset().top;
	console.log(origOffsetY);
	
	/* 윈도우 전체 이벤트 바인딩 */
	$(window)
	
	/* 윈도우 스크롤 감지 이벤트 */
	.on('scroll', function() {
		
		/* 상단 네비게이션 최초 위치를 벗어나면 상단에 고정 */
		if ($(window).scrollTop() >= origOffsetY) {
			
			$('.navbar').addClass('navbar-wrap');
			
		} else {
			
			$('.navbar').removeClass('navbar-wrap');
		}
	})
})

/* 로그인 버튼 이벤트 */
function login() {
	
	event.preventDefault();
	
	// 이미 로그인 창이 실행 중인 경우
	if($("[id=navigation]").find("[class*=col]").length>2) {
		
		if($("[id=nav-logo]").hasClass("text-warning")) {
			$("[id=nav-logo]").html("PAGE").removeClass("text-warning");
		}
		$("[id=navigation]").find("[id=login-form]").remove();
	}
	// 로그인 창 띄우기
	else {
		// 데스크톱 환경일 경우 네비게이션 바 중앙에 배치
		if($(window).width() >= 1440) {
			
			$("[id=navigation]").find("[class~=col-10]").before(
					'<div id="login-form" class="col col-lg-8">'
					+'<form>'
						+'<div class="form-row">'
							+'<div class="col-2">'
								+'<span class="navbar-brand text-warning">LOGIN</span>'
							+'</div>'
							+'<div class="col-4">'
								+'<input type="text" class="form-control" placeholder="ID"'
								+'style="margin-top: 14px">'
							+'</div>'
							+'<div class="col-4">'
								+'<input type="text" class="form-control" placeholder="PASSWORD"'
								+'style="margin-top: 14px">'
							+'</div>'
							+'<div class="col-2">'
							+'<button type="submit" class="btn btn-warning"'
							+'style="margin-top: 14px">'
							+'GO!</button>'
							+'</div>'
						+'</div>'
					+'</form>'
				+'</div>'
			);
		}
		// 모바일 환경일 경우 네비게이션 하단에 배치
		else if($(window).width() < 1440) {
			
			$("[class=navbar-brand]").html("LOGIN").addClass("text-warning");
			
			$("[id=navigation]").find("[class~=col-10]").after(
					'<div id="login-form" class="col col-lg-8">'
					+'<form>'
						+'<div class="form-row">'
							+'<div class="col-5">'
								+'<input type="text" class="form-control" placeholder="ID"'
								+'style="margin-top: 8px">'
							+'</div>'
							+'<div class="col-5">'
								+'<input type="text" class="form-control" placeholder="PASSWORD"'
								+'style="margin-top: 8px">'
							+'</div>'
							+'<div class="col-2">'
							+'<button type="submit" class="btn btn-warning"'
							+'style="margin-top: 8px">'
							+'GO</button>'
							+'</div>'
						+'</div>'
					+'</form>'
				+'</div>'
			);
		}
	}
}

function toggles(button_object) {

	event.preventDefault();
	
	// 해당 ROW안에서 활성화된 버튼이 한 개도 없는 경우
	if($(button_object).closest("[class~=row]").find("[class~= active]").length==0) {
		
		// 해당 버튼 활성화
		$(button_object).removeClass("btn-secondary")
		.addClass("btn-primary").addClass("active");
		
		// 히든 INPUT 값 설정
		$(button_object).closest("[class~=row]").find("input")
		.val($(button_object).attr("id"));
	}	
	// 해당 ROW안에서 활성화된 버튼이 한 개 있는 경우
	else if($(button_object).closest("[class~=row]").find("[class~= active]").length==1) {
		
		if($(button_object).hasClass("active")) {
			
			// 이미 활성화 되있던 버튼 활성화 해제
			$(button_object).removeClass("active").removeClass("btn-primary")
			.addClass("btn-secondary");
			
			// 히든 INPUT 값 제거
			$(button_object).closest("[class~=row]").find("input")
			.val(null);
		}
		else {
			
			// 이미 활성화 되있던 버튼 활성화 해제
			$(button_object).closest("[class~=row]").find("[class~= active]").removeClass("active").removeClass("btn-primary")
			.addClass("btn-secondary");
			
			// 클릭한 버튼 활성화
			$(button_object).removeClass("btn-secondary")
			.addClass("btn-primary").addClass("active");
			
			// 히든 INPUT 값 설정
			$(button_object).closest("[class~=row]").find("input")
			.val($(button_object).attr("id"));
		}
	}
}





