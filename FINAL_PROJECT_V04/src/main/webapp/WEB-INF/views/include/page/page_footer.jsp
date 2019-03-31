<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 
	메인 페이지 하단 네비게이션 바
	* 페이지 목록 표시
 -->	

<!-- 하단 네비게이션 바 -->
<div class="navbar footer navbar-light bg-light border-top">
	<div class="btn-toolbar page-tab-group" role="toolbar"
		aria-label="Toolbar with button groups">
		<c:if test="${not empty pages}">
			<c:forEach var="item" varStatus="status" items="${pages}">
				<c:choose>
					<c:when test="${status.index eq 0}">
						<button type="button" id="${item.page_no }" class="btn btn-primary page-tab main actived">${item.page_name}</button>
						<input type="hidden" id="page_no" name="page_no" value="${item.page_no }"></input>
					</c:when>
					<c:otherwise>
						<button type="button" id="${item.page_no }" class="btn btn-light page-tab">${item.page_name}</button>
						<input type="hidden" id="page_no" name="page_no" value="${item.page_no }"></input>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(pages) < 3}">
			<button type="button" class="btn btn-light" id="new-page"><img src="${path }resources/image/page/note.png"></button>
		</c:if>
	</div>
</div>
<!-- /하단 네비게이션 바 -->