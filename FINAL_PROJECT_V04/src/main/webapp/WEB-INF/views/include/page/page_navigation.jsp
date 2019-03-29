<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 
	메인 페이지 상단 네비게이션 구현 코드
	* 좌측 사이드바 토글 버튼
	* 상황별 경고 토글 경고창
	* 우측 유저 정보 프로필 및 드롭메뉴
 -->

<!-- 상단 네비게이션 바 -->
<nav id="top-navbar" 
	class="navbar sticky-top navbar-expand-lg navbar-light bg-light border-bottom">

	<!-- 페이지 편집 모드 토글 버튼 -->
	<button class="btn btn-secondary edit-mode-switch" id="menu-toggle">시작하기</button>

	<!-- /페이지 편집 모드 토글 버튼 -->

	<!-- 페이지 편집 상세 알림 -->
	<div id="page-details" class="collapse alert alert-dark" role="alert">
		페이지를 
		<a href="#" class="alert-link">추가 </a> 하거나 
		<a href="#" class="alert-link">삭제 </a> 할 수 있습니다.
	</div>

	<div id="partner-details" class="collapse alert alert-dark" role="alert">
		파트너를 
		<a href="#" class="alert-link">초대 </a> 하거나 
		<a href="#" class="alert-link">관리 </a> 할 수 있습니다.
	</div>
	<!-- /페이지 편집 상세 메뉴 -->

	<!-- 상황별 경고 표시 -->
	<div id='using-editor-warnning' class='collapse alert alert-danger' role='alert'>
		에디터 실행 중에 편집 모드를 종료할 경우 진행중이던 작업 내용은 모두 제거됩니다. 
		<a href='#' onclick='Using_Editor_Warnning(this.id)' id='Y' class='alert-link'>예 </a> / 
		<a href='#' onclick='Using_Warnning(this.id)' id='N' class='alert-link'> 아니오 </a>
	</div>
	<div id='same-editor-warnning' class='collapse alert alert-danger' role='alert'>
		이미 실행 중인 동일 에디터의 작업 내용은 모두 제거됩니다. 
		<a href='#' onclick='Same_Editor_Warnning("Y", this.id)' class='alert-link'>예 </a> / 
		<a href='#' onclick='Same_Editor_Warnning("N", this.id)' class='alert-link'> 아니오 </a>
	</div>
	<div id='create-editor-warnning' class='collapse alert alert-danger' role='alert'>
	동일 에디터가 이미 실행 중입니다.
	</div>
	<!-- /상황별 경고 표시 -->
	
	<!-- 텍스트 관련 경고 -->
	<!-- /텍스트 관련 경고 -->

	<!-- 테이블 관련 경고 -->
	<div id='table-destroy-warnning' class='collapse alert alert-danger' role='alert'>
		편집을 마치고 테이블을 유지하시겠습니까? 
		<a href='#' onclick='Table_Destroy_Warnning(this.id)' id='Y' class='alert-link'>유지 </a> / 
		<a href='#' onclick='Table_Destroy_Warnning(this.id)' id='N' class='alert-link'> 삭제 </a>
	</div>
	<div id='table-row-warnning' class='collapse alert alert-danger' role='alert'>
		테이블에는 최소 한 개 이상의 행이 유지되어야 합니다. 
		<a href='#' onclick='Table_Row_Warnning(this.id)' id='Y' class='alert-link'>계속 </a> / 
		<a href='#' onclick='Table_Row_Warnning(this.id)' id='N' class='alert-link'> 삭제 </a>
	</div>
	<!-- /테이블 관련 경고 -->
	<!-- /상황별 경고 표시 -->

	<!-- /유저 프로필 정보 및 드롭다운 메뉴 -->
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul id="user-profile" class="navbar-nav ml-auto mt-4 mt-lg-0">
			<c:if test="${not empty login}">
				<li class="nav-item">
					<button class="btn btn-light" href="#"><img id="page-home" src="${path }resources/image/page/home.png"></button>
				</li>
				<li class="nav-item">
					<button id="user-profile-img" class="btn btn-light" href="#"> 
						<img src="${pageContext.request.contextPath}/resources/image/${login.user_img}" class="user-image">
					</button>
				</li>
				<li class="nav-item">
				<span class="navbar-text text-dark" href="#">${login.user_name} 님</span>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 메뉴 </a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="#">Action</a> 
						<a class="dropdown-item" href="#">Another action</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="${path}/user/logout">로그아웃</a>
					</div>
				</li>
			</c:if>
		</ul>
	</div>
	<!-- /유저 프로필 정보 및 드롭다운 메뉴 -->
</nav>
<!-- /상단 네비게이션 바 -->