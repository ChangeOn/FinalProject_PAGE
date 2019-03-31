<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<!-- HEAD 태그 공통 부분  -->
<%@ include file="../include/commons/head.jsp"%>
<!-- /HEAD 태그 공통 부분  -->

<body>

	<!-- 전체 페이지 레이아웃 -->
	<div class="d-flex toggled" id="wrapper">
	
		<!-- 좌측 사이드 바 -->
		<%@ include file="../include/page/page_sidebar.jsp"%>
		<!-- /좌측 사이드 바 -->

		<!-- 페이지 컨텐츠 레이아웃 -->
		<div id="page-content-wrapper">
			
			<!-- 상단 네비게이션 바 -->
			<%@ include file="../include/page/page_navigation.jsp"%>
			<!-- /상단 네비게이션 바 -->	
			
			<!-- 컨텐츠 레이아웃 -->
			<div class="container-fluid">
				<!-- 유저별 페이지 정보 상이 -->
			</div>
			<!-- /컨텐츠 레이아웃 -->

			<!-- 하단 네비게이션 바 -->
			<%@ include file="../include/page/page_footer.jsp"%>
			<!-- /하단 네비게이션 바 -->
		</div>
		<!-- /페이지 컨텐츠 레이아웃 -->
	</div>
	<!-- /전체 페이지 레이아웃 -->

<%@ include file="../include/page/page_plugin_js.jsp"%>
</body>

</html>
