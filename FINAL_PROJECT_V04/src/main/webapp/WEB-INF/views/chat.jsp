<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 95%;">
<head>
<meta charset="UTF-8">
<title>채팅</title>

<script type="text/javascript">
	var wsocket;
	var randomcolor = setRandomColor();
	var userid = '${userid}';
	var username = '${username}';
	var pageno = '${pageno}';	

	function connect() {
		wsocket = new WebSocket("ws://localhost:8787/FINAL_PROJECT_V04/chat-ws.do");		
		wsocket.onopen = onOpen;
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;

		$('#message').attr('disabled', false);
		$('#plusbutton').attr('disabled', false);
		
		userid = prompt('ID 입력하세요');
		username = prompt('username 입력하세요');
		pageno = prompt('pageno 입력하세요');
		
		$('#nickname').val(username);
		$('#enterBtn').click();
	}

	function disconnect() {
		var msg = '{"type":"inout", "message":"[' + $('#nickname').val()
				+ '님 퇴장!]"'+ ', "pageno" :"' + pageno+'"}';
		wsocket.send(msg);
	}

	function onOpen(evt) {
		var msg = '{"type":"inout", "message":"[' + $('#nickname').val()
				+ '님 입장!]"'+ ', "pageno" :"' + pageno+'"}';
		wsocket.send(msg);
	}

	function onMessage(evt) {
		var jsonmsg = JSON.parse(evt.data);
		if (jsonmsg.type == 'msg') {
			appendMessage('<div class="message">'
					+'<div class="img_cont_msg">'
					+'<img src="http://placehold.it/50/55C1E7/fff&amp;text=U" class="rounded-circle user_img_msg"></div>'
					+ '<div class="nickname"><p style="background-color:'+jsonmsg.randomcolor+';">'
					+ jsonmsg.nickname + '</p></div>' + '<div class="msg"><p>'
					+ jsonmsg.message + '</p></div>' + '</div>');			

		} else if (jsonmsg.type == 'inout') {
			appendMessage('<div class="message">'
					+'<div class="msg"><p style="font-size:11px;">'
					+ jsonmsg.message + '</p></div>'
					+ '</div>');
			/* 닉네임 확인 == 자기 닉네임과 같은지 체크 */
			if (jsonmsg.message.substring(1, jsonmsg.message.lastIndexOf('님 퇴장!')) == username
					&& jsonmsg.message.lastIndexOf('님 퇴장!') != -1) {
				wsocket.close();
			}
		} else if (jsonmsg.type == 'filedata') {			
			appendMessage('<div class="message">'
					+'<div class="img_cont_msg">'
					+'<img src="http://placehold.it/50/55C1E7/fff&amp;text=U" class="rounded-circle user_img_msg"></div>'
					+ '<div class="nickname"><p style="background-color:'+jsonmsg.randomcolor+';">'
					+ jsonmsg.nickname
					+ '</p></div>'
					+ '<div><span class="badge badge-pill badge-success" style="font-size: 82%;">'
					+ jsonmsg.filename + '</span>'
					+ '<a class="btn" href="/FINAL_PROJECT_V04/download.do?filename='
					+ jsonmsg.newFileName + '&name=' + jsonmsg.filename
					+ '"><i class="fa fa-download"></i>download</a></div>'
					+ '</div>');
		} else if (jsonmsg.type == 'video') {	
			appendMessage("<div class='message'>"
					+'<div class="img_cont_msg">'
					+'<img src="http://placehold.it/50/55C1E7/fff&amp;text=U" class="rounded-circle user_img_msg"></div>'
					+ "<div class='nickname'><p style='background-color:"+jsonmsg.randomcolor+";'>"
					+ jsonmsg.nickname
					+ "</p></div>"
					+ "<iframe width='360' height='202' src='"+jsonmsg.url+"' frameborder='0' allowfullscreen></iframe>"
					+ "</div>");
		}
	}

	function onClose(evt) {
		//퇴장 한 이후 부과적인 작업이 있을 경우 명시
		$('#nickname').val("");
		$('#message').attr('disabled', true);
		$('#plusbutton').attr('disabled', true);
	}

	function send() {
		var nickname = $('#nickname').val();
		var msg = $('#message').val();
		if (msg == null || !msg.replace(/^\s+|\s+$/g, '')) {
			alert('메시지를 입력해주세요');
			return false;
		}
		wsocket.send('{"type" :"msg", "nickname" :"' + nickname
				+ '", "message" :"' + msg + '", "randomcolor" :"' + randomcolor + '", "pageno" :"' + pageno
				+ '"}');
		$('#message').val('');
	}

	function sendVideo() {
		var nickname = $('#nickname').val();

		var inputUrl = prompt('유튜브 주소를 입력하세요',
				'ex)https://www.youtube.com/embed/o_cdhpfEmLM');
		if (inputUrl.includes("youtu.be")) {
			inputUrl = "https://www.youtube.com/embed/"
					+ inputUrl.substr(-11, 11);
		}
		$('#videoUp').val(inputUrl);

		var url = $('#videoUp').val();
		if (url == null || !url.replace(/^\s+|\s+$/g, '')
				|| !url.includes("youtube.com")) {
			alert('링크 주소를 다시 확인해주세요!');
			return false;
		}

		wsocket.send('{"type" :"video", "nickname" :"' + nickname
				+ '", "url" :"' + url + '", "randomcolor" :"' + randomcolor+ '", "pageno" :"' + pageno
				+ '"}');
		$('#videoUp').val('');
	}

	// 서버로 업로드 파일 전송
	function sendBinary() {
		var file = $("#uploadForm")[0];

		var newdata = new FormData(file);
		var filetitle;
		var filestream;

		$.ajax({
			url : '/FINAL_PROJECT_V04/fileupload.do',
			type : 'POST',
			data : newdata,
			cache : false,
			processData : false,
			contentType : false,
			success : function(data, textStatus, jqXHR) {
				console.log(data);	
				var jsonStr;

				//JSON
				if (!(data.title == "" && data.fileName == "")) {
					//var arr = [];
					var obj = {};
					var jsonStr;
					var nickname = $('#nickname').val();

					//JSON형태로 웹소켓 서버에 메세지 보내기
					obj.type = "filedata";
					obj.filetitle = data.title;
					obj.nickname = nickname;
					obj.randomcolor = randomcolor;
					obj.filename = data.fileName;
					obj.newFileName = data.newFileName;
					obj.fileno = String(data.fileno);
					obj.pageno = pageno;
					jsonStr = JSON.stringify(obj);

					wsocket.send(jsonStr);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log('ERRORS: ' + textStatus);
			}
		});
	}

	function appendMessage(msg) {
		$('#chatMessageArea').append(msg);
		//var chatAreaHeight = $('#chatArea').height();
		//var maxScroll = $('#chatMessageArea').height();
		$('#chatMessageArea').scrollTop($('#chatMessageArea').prop('scrollHeight'));
	}

	$(document).ready(function() {
		
		connect();

		$('#message').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				send();
			}
			event.stopPropagation();
		});
		$('#sendBtn').click(function() {
			send();
		});
		$('#enterBtn').click(function() {
			if ($('#nickname').val() == '') {
				alert('이름을 입력하세요!');
				$('#nickname').focus();
				return;
			}
			connect();
		});
		$('#exitBtn').click(function() {
			if ($('#nickname').val() == '') {
				alert('이름을 입력하세요!');
				$('#nickname').focus();
				return;
			}
			disconnect();
		});

		$('#fileUp').change(function() {
			sendBinary();
			alert('업로드');
		});
		
		// moveChat 마우스를 눌렀을 때
		$("#moveChat").mousedown(function(e) {
			$("#chatcontainer").draggable({
				disabled : false
			});
		})
		// moveChat 마우스를 뗄 때
		$("#moveChat").mouseup(function(e) {
			$("#chatcontainer").draggable({
				disabled : true
			});
		})
	});

	function setRandomColor() {
		values = [];
		for (var i = 0; i < 256; i++)
			if (i > 80)
				values.push(i);
		var r = values[(Math.random() * values.length) | 0], g = values[(Math
				.random() * values.length) | 0], b = values[(Math.random() * values.length) | 0];
		
		return "rgb(" + r + "," + g + "," + b + ")";		
	}

	function insertFile() {
		$('#my-file-selector').click();
	}

</script>
<style type="text/css">
#chatArea {
	padding: 12px 12px 0 12px;
	width: 100%;
	height: 100%;
	max-height: 800px;
	border-radius : 4px;
	border-bottom: 5px solid #222f3d;
	background: #C1C0C3;
	border-radius: 4px
}

#chatMessageArea {
	min-height: 150px;
	height: 80%;
	max-height: 700px;
	border-radius: 3px;
	background: #eee;
	overflow: auto;
}

#chatMessageArea .message {
	display: flex;
	margin: 10px 0 0 10px;
	min-height: 30px;
	height: auto;
	text-align: left;
	overflow: auto;
}

#chatMessageArea .message .nickname p {
	display: inline-block;
	margin: 0 10px 0 0;
	width: auto;
	padding: 8px 8px 8px 8px;
	/* background: #f0ad4e; */
	word-wrap: break-word;
	font-family: Monospace;
	border-radius: 10px;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

#chatMessageArea .message .msg p {
	display: inline-block;
	margin: 0;
	width: auto;
	padding: 8px 10px 8px 10px;
	background: #fff;
	word-wrap: break-word;
	font-family: Monospace;
	border-radius: 3px;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

#chatMessageArea .message .msg:before {
	position: relative;
	float: left;
	content: '';
	margin: 7px 0 0 -8px;
	width: 0;
	height: 0;
	border-style: solid;
	border-width: 8px 8px 8px 0;
	border-color: transparent #fff transparent transparent;
}

.card-headerCustom {
    padding: .75rem 1.25rem;
    margin-bottom: 0;
    border-bottom: 1px solid rgba(0,0,0,.125);
}

.card-footerCustom {
    padding: .75rem 1.25rem;
    border-top: 1px solid rgba(0,0,0,.125);
}

.user_img_msg {
    height: 35px;
    width: 35px;
}


</style>
</head>

<body style="height: 100%;">

	<div class="card mb-3" style="height: 100%;">
		<div id="chatArea">
			<h4 class="card-headerCustom" id="moveChat" style="color: #fff; padding: 0;">
				<span class="fas fa-comment"></span> Chat
				<input type="text" id="nickname" style="display:none; width: 30%;">
				<input type="button" id="enterBtn" style="display:none;" value="입장">
				<input type="button" id="exitBtn" style="display:none;" value="나가기">
				<i class="fas fa-times-circle icon-red float-right" onclick="deleteChat();"></i>			
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
									method="POST" action="/fileupload.do">
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