<!-- 
	HEAD 태그 공통 부분 구현 코드
	* TAGLIB 및 BOOTSTRAP JQUERY 로드
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>PAGE</title>
    
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <!-- 웹폰트 관련 -->
	<link rel="stylesheet" href="${path}/resources/css/font/web-fonts.css" type="text/css">
	
	<!-- Font Awesome CDN -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
		integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
		crossorigin="anonymous">
	
	<!-- Bootstrap -->
	<script src="${path}/webjars/jquery/3.3.1-2/jquery.min.js"></script>
	<link rel="stylesheet" href="${path}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
	<script src="${path}/webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
	
	<!-- JQuery-UI -->
	<script src="${path}/webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="${path}/webjars/jquery-ui/1.12.1/jquery-ui.css">

</head>