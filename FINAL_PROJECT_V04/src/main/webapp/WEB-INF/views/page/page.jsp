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
