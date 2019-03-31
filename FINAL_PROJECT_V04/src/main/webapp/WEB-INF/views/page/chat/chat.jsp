<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html style="height: 95%;">
<head>
<meta charset="UTF-8">
<title>채팅</title>

<!-- Chat mainfunction -->

<script type="text/javascript" src="/resources/js/page/chat/chat-mainfunctions.js"></script>
<script type="text/javascript">
	var wsocket;
	var randomcolor = setRandomColor();
	var userid = '${userid}';
	var username = '${username}';
	var pageno = '${pageno}';
	var profileimg = '${profileimg}';
</script>
<link type="text/css" rel="stylesheet" href="${path}/resources/css/page/chat/chat-main.css">
</head>
<body style="height: 100%;">
	<div class="card mb-3" style="height: 100%;">
		<div id="chatArea">
			<h4 class="card-headerCustom" id="moveChat" style="color: #fff; padding: 0;">
				<span class="fas fa-comment"></span> Chat
				<input type="text" id="nickname" style="display:none; width: 30%;">
				<input type="button" id="enterBtn" style="display:none;" value="입장">
				<input type="button" id="exitBtn" style="display:none;" value="나가기">
				<i id="chatDelteIcon" class="fas fa-times-circle icon-red float-right" onclick="deleteChat();"></i>			
			</h4>
			
			<div id="chatMessageArea" class="card-body"></div>
			<div class="card-footerCustom">
				<div class="input-group">
					<div class="btn-group float-left">
						<button type="button" id="plusbutton"
							class="btn btn-secondary btn-sm dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
							disabled>
						</button>
						<ul class="dropdown-menu slidedown">
							<li><a class="dropdown-item" onclick="insertFile();">
								<span class="far fa-file"></span>       파일 추가</a>
								<form id="uploadForm" enctype="multipart/form-data"
									method="POST" action="/fileupload">
									<input id="my-file-selector" value="전송" type="file" id="fileUp"
										name="fileUp" style="display: none" onchange="sendBinary();">
								</form>
							</li>
							<li><a class="dropdown-item" onclick="sendVideo();">
								<span class="fab fa-youtube"></span> 동영상 전송 <input type="url" id="videoUp" name="videoUp" style="display: none;">								
							</a></li>
						</ul>
					</div>
					<input type="text" id="message"
						class="form-control form-control-sm" style="width: 64%;" disabled>
					<span class="input-group-btn">
						<button class="btn btn-warning btn-sm text-white" id="sendBtn">전송</button>
					</span>
				</div>
			</div>
		</div>
	</div>


</body>
</html>