<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Simple Sidebar - Start Bootstrap Template</title>

<!-- 웹폰트 관련 -->
<link rel="stylesheet" href="resources/css/web-fonts.css"
	type="text/css">
	
<!-- Font Awesome CDN -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" 
integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">

<!-- Bootstrap 관련 -->
<script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
<script src="webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="webjars/bootstrap/4.3.1/css/bootstrap.min.css">

<!-- JQuery-UI 관련 -->
<script src="webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="webjars/jquery-ui/1.12.1/jquery-ui.css">

<!-- JQTE 플러그인 -->
<link type="text/css" rel="stylesheet"	href="resources/css/jquery-te-1.4.0.css">
<script src="resources/js/jquery-te-1.4.0.min.js"></script>

<!-- Custom styles for this template -->
<link href="resources/css/simple-sidebar.css" rel="stylesheet">

<!-- 페이지 수정 기능 관련 -->
<script src="resources/js/page-edit-functions.js"></script>

<!-- JQuery 테이블 플러그인 -->
<script src="resources/js/jquery-table.js"></script>

<!-- <script src="resources/js/chat-functions.js"></script> -->
<script type="text/javascript">

	//chat.jsp 생성
	function Add_Chat(){
		if ($("#chatcontainer").length > 0){
			alert("채팅창은 한개이상 실행이 불가능합니다.");
			return false;
		}		
		console.log("채팅창 생성");	
		
		$(".container-fluid").prepend(
				"<div id='chatcontainer' style='width:795px;'>"
						+"</div>");
		var url = "${pageContext.request.contextPath}/web/chat.do";
		$("#chatcontainer").load(url, function(){
			$("#chatcontainer").resizable({
				minHeight: 260,
				minWidth: 430,
				maxHeight: 800,
				resize: function( event, ui ) {
				    $("#chatMessageArea").height(ui.size.height-35-56-65);
				}
			});
		});
	}
	
	// paint.jsp 생성
	function Add_Paint(){
		if ($("#paintcontainer").length > 0){
			alert("그림판은 한개이상 실행이 불가능합니다.");
			return false;
		}
		console.log("그림판 생성");
		$(".container-fluid").prepend(
				"<div id='paintcontainer' style='width:795px;'>"
						+"</div>");
		var url = "${pageContext.request.contextPath}/paint.do";
		$("#paintcontainer").load(url, function(){});
	}
	
	function deleteChat() {		
		$('#exitBtn').click();
		$('#chatcontainer').remove();
	}	

</script>
<style type="text/css">
	#paint {
		overflow: hidden;
	}

</style>
</head>
<body>

	<div class="d-flex" id="wrapper">

		<!-- Sidebar -->
		<div class="bg-light border-right border-secondary" id="sidebar-wrapper">
			<div class="sidebar-heading bg-secondary text-white rounded-top">PAGE EDIT MENU</div>
			<div id="sidebar-scroll" class="list-group list-group-flush border-top-0">

				<!-- 페이지 설정 관련 메뉴 -->
				<a class="list-group-item list-group-item-action bg-light"
				data-toggle="collapse" href="#page-management" aria-expanded="false">PAGE MANAGEMENTS</a>
				<div id="page-management" class="collapse list-group list-group-flush">
					<a onclick="" class="list-group-item list-group-item-action bg-white"
					data-toggle="collapse" href="#page-details" aria-expanded="false">Page</a>
					<a onclick="" class="list-group-item list-group-item-action bg-white"
					data-toggle="collapse" href="#partner-details" aria-expanded="false">Partner</a> 
				</div>
				
				<!-- 페이지 요소 관련 메뉴 -->
				<a class="list-group-item list-group-item-action bg-light"
				data-toggle="collapse" href="#primary-components" aria-expanded="false" aria-controls="collapseExample">PRIMARY COMPONENTS</a>
				<div id="primary-components" class="collapse list-group list-group-flush">
					<a href="#" onclick="Add_PlainText();" class="list-group-item list-group-item-action bg-white">Text Box</a>
					<a href="#" onclick="Add_Table();" class="list-group-item list-group-item-action bg-white">Table</a>
					<a href="#" onclick="Add_Chat();" class="list-group-item list-group-item-action bg-white">Chat</a>
					<a href="#" onclick="Add_Paint();" class="list-group-item list-group-item-action bg-white">Paint</a>
				</div>

				<a href="#" class="list-group-item list-group-item-action bg-light">Board</a>

			</div>

		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">

			<nav id="top-navbar" class="navbar sticky-top navbar-expand-lg navbar-light bg-light border-bottom">
			
				<!-- 페이지 편집 모드 토글 버튼 -->
				<button class="btn btn-light" id="menu-toggle">Toggle Menu</button>
				
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<!-- /페이지 편집 모드 토글 버튼 -->
				
				<!-- 페이지 편집 상세 메뉴 -->
				<div id="page-details" class="collapse alert alert-dark" role="alert">
					페이지를 
		  			<a href="#" class="alert-link">추가 </a>
		  			하거나 
		  			<a href="#" class="alert-link">삭제 </a>
	  				할 수 있습니다.
				</div>
					
				<div id="partner-details" class="collapse alert alert-dark" role="alert">
					파트너를 
		  			<a href="#" class="alert-link">초대 </a>
		  			하거나  
		  			<a href="#" class="alert-link">관리 </a>
	  				할 수 있습니다.
				</div>
				<!-- /페이지 편집 상세 메뉴 -->
				
				<!-- 상황별 경고 표시 -->
				
				<div id='using-editor-warnning' class='collapse alert alert-danger' role='alert'>
					에디터 실행 중에 편집 모드를 종료할 경우
					진행중이던 작업 내용은 모두 제거됩니다.
					<a href='#' onclick='Using_Editor_Warnning(this.id)' id='Y' class='alert-link'>
					 예
					</a>
					 /
					<a href='#' onclick='Using_Warnning(this.id)' id='N' class='alert-link'>
					 아니오
					</a>
				</div>
				<div id='same-editor-warnning' class='collapse alert alert-danger' role='alert'>
					이미 실행 중인 동일 에디터의 작업 내용은 모두 제거됩니다.
					<a href='#' onclick='Same_Editor_Warnning("Y", this.id)' class='alert-link'>
					 예
					</a>
					 /
					<a href='#' onclick='Same_Editor_Warnning("N", this.id)' class='alert-link'>
					 아니오
					</a>
				</div>
				<div id='create-editor-warnning' class='collapse alert alert-danger' role='alert'>
					동일 에디터가 이미 실행 중입니다.
				</div>
				
				<!-- 텍스트 관련 경고 -->
				<!-- /텍스트 관련 경고 -->
				
				<!-- 테이블 관련 경고 -->
				<div id='table-destroy-warnning' class='collapse alert alert-danger' role='alert'>
					편집을 마치고 테이블을 유지하시겠습니까?
					<a href='#' onclick='Table_Destroy_Warnning(this.id)' id='Y' class='alert-link'>
					 유지
					</a>
					 /
					<a href='#' onclick='Table_Destroy_Warnning(this.id)' id='N' class='alert-link'>
					 삭제
					 
					</a>
				</div>
				<div id='table-row-warnning' class='collapse alert alert-danger' role='alert'>
					테이블에는 최소 한 개 이상의 행이 유지되어야 합니다.
					<a href='#' onclick='Table_Row_Warnning(this.id)' id='Y' class='alert-link'>
					 계속
					</a>
					 /
					<a href='#' onclick='Table_Row_Warnning(this.id)' id='N' class='alert-link'>
					 삭제
					 
					</a>
				</div>
				<!-- /테이블 관련 경고 -->
				<!-- /상황별 경고 표시 -->
				

				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">
						<li class="nav-item active"><a class="nav-link" href="#">Home
								<span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
						<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> Dropdown </a>
							<div class="dropdown-menu dropdown-menu-right"
								aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="#">Action</a> <a
									class="dropdown-item" href="#">Another action</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">Something else here</a>
							</div></li>
					</ul>
				</div>
			</nav>

			<div class="container-fluid">
			
				<!-- <div class="draggable text" style="position: relative; left: 500px; top: 5px;">
					<div class="plain text OFF" data-origin="div">
						<div id="jqte-div">
							<span style="font-size: 197px;">PAGE</span>
						</div>
					</div>  
				</div> -->
				<div class="bottom-fixed-by-level-1" style="position: relative; top: 2000px;"><br></div>
		</div>
		<!-- /#page-content-wrapper -->
		
		<div class="navbar footer navbar-light bg-light border-top">
			<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
				<button type="button" class="btn btn-light">first</button>
				<button type="button" class="btn btn-light">Secondary</button>
			</div>
		</div>

	</div>
	<!-- /#wrapper -->

</body>

</html>
