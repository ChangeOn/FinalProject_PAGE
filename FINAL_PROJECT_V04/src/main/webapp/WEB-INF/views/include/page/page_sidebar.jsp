<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<!-- 
	메인 페이지 좌측 사이드바 구현
	* 페이지 설정 관련 메뉴
	* 페이지 요소 관련 메뉴
 -->	
	
<!-- 좌측 사이드 바 -->
<div class="bg-light border-right border-secondary" id="sidebar-wrapper">
	<div class="sidebar-heading bg-secondary text-white rounded-top">
	페이지 편집 메뉴
	</div>
	<div id="sidebar-scroll" class="list-group list-group-flush border-top-0">

		<!-- 페이지 설정 관련 메뉴 -->
		<a class="list-group-item list-group-item-action bg-light"
			data-toggle="collapse" href="#page-management" aria-expanded="false">
			PAGE MANAGEMENTS
		</a>
		<div id="page-management" class="collapse list-group list-group-flush">
			<a onclick="" class="list-group-item list-group-item-action bg-white"
				data-toggle="collapse" href="#page-details" aria-expanded="false">Page</a>
			<a onclick="" class="list-group-item list-group-item-action bg-white"
				data-toggle="collapse" href="#partner-details" aria-expanded="false">Partner</a>
		</div>

		<!-- 페이지 요소 관련 메뉴 -->
		<a class="list-group-item list-group-item-action bg-light"
			data-toggle="collapse" href="#primary-components"
			aria-expanded="false" aria-controls="collapseExample">
			PRIMARY COMPONENTS</a>
		<div id="primary-components" class="collapse list-group list-group-flush">
			<a href="#" onclick="Add_PlainText();"
				class="list-group-item list-group-item-action bg-white">Text Box</a>
			<a href="#" onclick="Add_Table();"
				class="list-group-item list-group-item-action bg-white">Table</a>
		</div>
		<a href="#" onclick="Add_Board();" 
			class="list-group-item list-group-item-action bg-light">Board</a> 
		<a href="#" onclick="Add_Chat();" 
			class="list-group-item list-group-item-action bg-light">Chat</a> 
		<a href="#" onclick="Add_Paint();" 
			class="list-group-item list-group-item-action bg-light">Paint</a> 
		<a href="#" onclick="Add_Map();" 
			class="list-group-item list-group-item-action bg-light">Map</a> 
		<a href="#" onclick="Add_Calendar();" 
			class="list-group-item list-group-item-action bg-light">Calendar</a>
	</div>
</div>
<!-- /#sidebar-wrapper -->