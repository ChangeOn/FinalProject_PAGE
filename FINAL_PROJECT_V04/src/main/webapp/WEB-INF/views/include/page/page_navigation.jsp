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
	<div id="page-edit-mode-on-warnning" class="collapse alert alert-dark" role="alert">
		페이지 편집 모드를 시작하시겠습니까?  
		<a href='#' onclick='Page_Edit_Mode_ON_Warnning(this.id)' id='Y' class='alert-link'>예 </a> / 
		<a href='#' onclick='Page_Edit_Mode_ON_Warnning(this.id)' id='N' class='alert-link'> 아니오 </a>
	</div>
	<div id="page-edit-mode-off-warnning" class="collapse alert alert-dark" role="alert">
		페이지 편집 모드를 종료하고, 저장하시겠습니까?
		<a href='#' onclick='Page_Edit_Mode_OFF_Warnning(this.id)' id='Y' class='alert-link'>예 </a> / 
		<a href='#' onclick='Page_Edit_Mode_OFF_Warnning(this.id)' id='N' class='alert-link'> 아니오 </a>
	</div>
	<!-- /페이지 편집 모드 토글 버튼 -->

	<!-- 페이지 편집 상세 알림 -->
	<div id="page-details" class="collapse alert alert-warning" role="alert">
		
		미구현된 기능으로, 추후 업데이트 예정입니다.
		<!-- 
		미구현 된 기능 추후 업데이트 예정
		
		페이지를 
		<a href="#" class="alert-link">추가 </a> 하거나 
		<a href="#" class="alert-link">삭제 </a> 할 수 있습니다.
		 -->
	</div>

	<div id="partner-details" class="collapse alert alert-warning" role="alert">
	
		미구현된 기능으로, 추후 업데이트 예정입니다.
	
		<!-- 
		미구현 된 기능 추후 업데이트 예정
		
		파트너를 
		<a href="#" class="alert-link">초대 </a> 하거나 
		<a href="#" class="alert-link">관리 </a> 할 수 있습니다.
		-->
	</div>
	<!-- /페이지 편집 상세 메뉴 -->

	<!-- 상황별 경고 표시 -->
	<div id='using-editor-warnning' class='collapse alert alert-danger' role='alert'>
		에디터 실행 중에 편집 모드를 종료할 경우 진행중이던 작업 내용은 모두 제거됩니다. 
		<a href='#' onclick='Using_Editor_Warnning(this.id)' id='Y' class='alert-link'>예 </a> / 
		<a href='#' onclick='Using_Editor_Warnning(this.id)' id='N' class='alert-link'> 아니오 </a>
	</div>
	<div id='same-editor-warnning' class='collapse alert alert-danger' role='alert'>
		이미 실행 중인 동일 에디터의 작업 내용은 모두 제거됩니다. 
		<a href='#' onclick='Same_Editor_Warnning("Y")' class='alert-link'>예 </a> / 
		<a href='#' onclick='Same_Editor_Warnning("N")' class='alert-link'> 아니오 </a>
	</div>
	<div id='create-editor-warnning' class='collapse alert alert-danger' role='alert'>
		동일 에디터가 이미 실행 중입니다.
	</div>
	<div id='other-tab-warnning' class='collapse alert alert-danger' role='alert'>
		다른 페이지 탭을 불러오기 전, 페이지 편집을 먼저 마쳐주십시오.
	</div>
	<!-- /상황별 경고 표시 -->
	
	<!-- 상황별 알림 표시 -->
	<div id='update-tab-warnning' class='collapse alert alert-primary' role='alert'>
		페이지 탭 정보가 업데이트 되었습니다.
	</div>
	<!-- /상황별 알림 표시 -->
	
	<!-- 북마크 관련 알림 -->
	<div id='add-bookmark-warnning' class='collapse alert alert-secondary' role='alert'>
		현제 페이지 시점으로 북마크를 생성하시겠습니까?
		<a href='#' onclick='Add_Bookmark_Warnning("Y")' class='alert-link'>예 </a> / 
		<a href='#' onclick='Add_Bookmark_Warnning("N")' class='alert-link'> 아니오 </a>
	</div>
	<div id='input-bookmark-details-warnning' class='collapse alert alert-secondary' role='alert'>
		<form class="form" action="bookmark/add" method="post">
			<input type="hidden" id="page_no" name="page_no"></input>
			<input type="hidden" id="bookmark_scrolltop" name="bookmark_scrolltop" value=0></input>
			<div style="display: inline-block;">
				<span>추가하실 북마크의 이름을 설정합니다. </span>
			</div>
			<div style="display: inline-block;">
				<input type="text" name="bookmark_name"></input>
			</div>
			<div style="display: inline-block; font-size: 0.9rem;
			margin-left: 5px;">
				<button type="submit" class="btn btn-warning">생성</button>
			</div>
		</form>
	</div>
	<!-- /북마크 관련 알림 -->
	
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
					<button id="user-profile-img" class="btn btn-light" href="#"> 
						<img src="${pageContext.request.contextPath}/resources/image/${login.user_img}" class="user-image">
					</button>
				</li>
				<li class="nav-item">
				<span class="navbar-text text-dark" href="#">${login.user_name} 님</span>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="bookmark-Dropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">북마크</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="bookmark-Dropdown">
							<c:forEach var="item" varStatus="status" items="${bookmarks}">
								<a class="dropdown-item" id="${item.bookmark_no }" href="#" onclick="Show_Bookmark_Page(this.id)">${item.bookmark_name }</a>
							</c:forEach>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#" onclick="Show_Add_Bookmark_Warnning()">북마크 추가</a>
					</div>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="profile-Dropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 메뉴 </a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="profile-Dropdown">
						<a class="dropdown-item" href="#">마이페이지</a> 
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