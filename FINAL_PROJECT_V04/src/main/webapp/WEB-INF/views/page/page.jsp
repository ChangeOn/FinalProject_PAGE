<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Simple Sidebar - Start Bootstrap Template</title>

<!-- 웹폰트 관련 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/web-fonts.css" type="text/css">

<!-- Font Awesome CDN -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">

<!-- Bootstrap 관련 -->
<script src="${pageContext.request.contextPath}/webjars/jquery/3.3.1-2/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>

<!-- JQuery-UI 관련 -->
<script src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.css">

<!-- JQTE 플러그인 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-te-1.4.0.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-te-1.4.0.min.js"></script>

<!-- 메인 페이지 CSS -->
<link href="${pageContext.request.contextPath}/resources/css/main-page.css" rel="stylesheet">

<!-- 페이지 수정 기능 관련 -->
<script src="${pageContext.request.contextPath}/resources/js/main-edit-functions.js"></script>

<!-- JQuery 테이블 플러그인 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-table.js"></script>

<!-- Canvas 관련 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html5-canvas-drawing-app.js"></script>

<!-- Board 관련 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/board-functions.js"></script>

<!-- Chat 관련 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/chat-functions.js"></script>

<!-- Paint 관련 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/paint-functions.js"></script>

<!-- Calendar 관련 -->

<!-- sweetalert -->
<script type="text/javascript" src="resources/js/sweetalert2.min.js"></script>
<link rel='stylesheet' type='text/css' href='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css'/>

<!-- moment.js -->
<script type="text/javascript" src="resources/js/moment.min.js"></script>

<!-- fullcalendar -->
<script type="text/javascript" src="resources/js/fullcalendar.min.js"></script>
<link rel='stylesheet' type='text/css' href='resources/css/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='resources/css/fullcalendar.min.css' />
<link rel='stylesheet' type='text/css' href='resources/css/fullcalendar.print.css' media="print"/>
<script type="text/jacascript" src="resources/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/calendar-functions.js"></script>



<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/calendar-functions.js"></script>

<!-- Map 관련 -->
<link rel="stylesheet" href="resources/css/map.css">
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2fb1c4ff5a5b502155b05f2ba5928593&libraries=services"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/map-functions.js"></script>
<script>
function Add_Map(){
	$(".container-fluid").prepend(
		"<div class='map_wrap card bg-light'>"
			+"<div class='card-header' style='height:2em'>"
			+"</div>"
			+"<div class='card-body' id='map' style='position: relative; width: 100%; height: 100%; overflow: hidden;'></div>"
			+"<script src='resources/js/map-functions.js'><"+"/script>"
			// 키워드로 장소검색
			+"<div id='menu_wrap'>"
				+"<div class='option'>"
					+"<div id='optiondiv'>"
						+"<form onsubmit='searchPlaces(); return false;'>"
							+"<input type='text' id='keyword' size='15' placeholder='키워드 입력+Enter' />"
							//+"<button class='btn btn-light btn-sm' type='submit'>search</button>"
						+"</form>"
					+"</div>"
				+"</div>"
				// 검색결과인 장소의 마커들을 나타내는 리스트
				+"<ul id='placesList'></ul>"
			+"</div>"
		+"</div>"
	);
}
</script>
</head>

<body>

	<div class="d-flex" id="wrapper">

		<!-- Sidebar -->
		<div class="bg-light border-right border-secondary" id="sidebar-wrapper">
			<div class="sidebar-heading bg-secondary text-white rounded-top">페이지 편집 메뉴</div>


			<div id="sidebar-scroll" class="list-group list-group-flush border-top-0">

				<!-- 페이지 설정 관련 메뉴 -->
				<a class="list-group-item list-group-item-action bg-light"
					data-toggle="collapse" href="#page-management"
					aria-expanded="false">PAGE MANAGEMENTS</a>
				<div id="page-management"
					class="collapse list-group list-group-flush">
					<a onclick=""
						class="list-group-item list-group-item-action bg-white"
						data-toggle="collapse" href="#page-details" aria-expanded="false">Page</a>
					<a onclick=""
						class="list-group-item list-group-item-action bg-white"
						data-toggle="collapse" href="#partner-details"
						aria-expanded="false">Partner</a>
				</div>

				<!-- 페이지 요소 관련 메뉴 -->
				<a class="list-group-item list-group-item-action bg-light"
					data-toggle="collapse" href="#primary-components"
					aria-expanded="false" aria-controls="collapseExample">PRIMARY
					COMPONENTS</a>
				<div id="primary-components"
					class="collapse list-group list-group-flush">
					<a href="#" onclick="Add_PlainText();"
						class="list-group-item list-group-item-action bg-white">Text
						Box</a> <a href="#" onclick="Add_Table();"
						class="list-group-item list-group-item-action bg-white">Table</a>
				</div>
				<a href="#" onclick="Add_Board();"
					class="list-group-item list-group-item-action bg-light">Board</a>
				<a href="#" onclick="Add_Chat();"
					class="list-group-item list-group-item-action bg-light">Chat</a> <a
					href="#" onclick="Add_Paint();"
					class="list-group-item list-group-item-action bg-light">Paint</a>
				<a href="#" onclick="Add_Map();"
					class="list-group-item list-group-item-action bg-light">Map</a> <a
					href="#" onclick="Add_Calendar();"
					class="list-group-item list-group-item-action bg-light">Calendar</a>

			</div>

		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">

			<nav id="top-navbar"
				class="navbar sticky-top navbar-expand-lg navbar-light bg-light border-bottom">

				<!-- 페이지 편집 모드 토글 버튼 -->
				<button class="btn btn-light" id="menu-toggle">시작하기</button>

				<!-- /페이지 편집 모드 토글 버튼 -->

				<!-- 페이지 편집 상세 메뉴 -->
				<div id="page-details" class="collapse alert alert-dark"
					role="alert">
					페이지를 <a href="#" class="alert-link">추가 </a> 하거나 <a href="#"
						class="alert-link">삭제 </a> 할 수 있습니다.
				</div>

				<div id="partner-details" class="collapse alert alert-dark"
					role="alert">
					파트너를 <a href="#" class="alert-link">초대 </a> 하거나 <a href="#"
						class="alert-link">관리 </a> 할 수 있습니다.
				</div>
				<!-- /페이지 편집 상세 메뉴 -->

				<!-- 상황별 경고 표시 -->

				<div id='using-editor-warnning' class='collapse alert alert-danger'
					role='alert'>
					에디터 실행 중에 편집 모드를 종료할 경우 진행중이던 작업 내용은 모두 제거됩니다. <a href='#'
						onclick='Using_Editor_Warnning(this.id)' id='Y' class='alert-link'>
						예 </a> / <a href='#' onclick='Using_Warnning(this.id)' id='N'
						class='alert-link'> 아니오 </a>
				</div>
				<div id='same-editor-warnning' class='collapse alert alert-danger'
					role='alert'>
					이미 실행 중인 동일 에디터의 작업 내용은 모두 제거됩니다. <a href='#'
						onclick='Same_Editor_Warnning("Y", this.id)' class='alert-link'>
						예 </a> / <a href='#' onclick='Same_Editor_Warnning("N", this.id)'
						class='alert-link'> 아니오 </a>
				</div>
				<div id='create-editor-warnning' class='collapse alert alert-danger'
					role='alert'>동일 에디터가 이미 실행 중입니다.</div>

				<!-- 텍스트 관련 경고 -->
				<!-- /텍스트 관련 경고 -->

				<!-- 테이블 관련 경고 -->
				<div id='table-destroy-warnning' class='collapse alert alert-danger'
					role='alert'>
					편집을 마치고 테이블을 유지하시겠습니까? <a href='#'
						onclick='Table_Destroy_Warnning(this.id)' id='Y'
						class='alert-link'> 유지 </a> / <a href='#'
						onclick='Table_Destroy_Warnning(this.id)' id='N'
						class='alert-link'> 삭제 </a>
				</div>
				<div id='table-row-warnning' class='collapse alert alert-danger'
					role='alert'>
					테이블에는 최소 한 개 이상의 행이 유지되어야 합니다. <a href='#'
						onclick='Table_Row_Warnning(this.id)' id='Y' class='alert-link'>
						계속 </a> / <a href='#' onclick='Table_Row_Warnning(this.id)' id='N'
						class='alert-link'> 삭제 </a>
				</div>
				<!-- /테이블 관련 경고 -->
				<!-- /상황별 경고 표시 -->


				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul id="user-profile" class="navbar-nav ml-auto mt-4 mt-lg-0">
						 <c:if test="${not empty login}">
							<li class="nav-item active">
								<a class="nav-link" href="#">Home
									<span class="sr-only">(current)</span>
								</a></li>
							<li class="nav-item border border-light rounded">
								<a id="user-profile-img" href="#">
		                            <img src="${pageContext.request.contextPath}/resources/image/${login.user_img}" class="user-image">
	                        	</a>
	                        </li>
	                        <li class="nav-item">
								<span class="navbar-text text-dark" href="#">${login.user_name} 님</span>
	                        </li>
							<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
								role="button" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false"> 메뉴 </a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdown">
									<a class="dropdown-item" href="#">Action</a> <a
										class="dropdown-item" href="#">Another action</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="${path}/user/logout">로그아웃</a>
								</div></li>
						</c:if>
					</ul>
				</div>
			</nav>

			<div class="container-fluid">

				<!-- 
				캔버스 관련 (추후 작업 예정)
				<div id="canvasDiv"></div> 
				-->

				<div class="draggable text"
					style="position: relative; left: 500px; top: 5px;">
					<div class="plain text OFF" data-origin="div">
						<div id="jqte-div">
							<span style="font-size: 197px;">PAGE</span>
						</div>
					</div>
				</div>
				<div class="bottom-fixed-by-level-1"
					style="position: relative; top: 2000px;">
					<br>
				</div>
			</div>
			<!-- /#page-content-wrapper -->

			<div class="navbar footer navbar-light bg-light border-top">
				<div class="btn-toolbar" role="toolbar"
					aria-label="Toolbar with button groups">
					<button type="button" class="btn btn-light">first</button>
					<button type="button" class="btn btn-light">Secondary</button>
				</div>
			</div>

		</div>
	</div>
	<!-- /#wrapper -->

</body>

</html>
