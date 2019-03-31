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
			if ($("#menu-toggle").text() != '시작하기'){
				draggable_switch('ON', $("#chatcontainer"));
			}
		})
		// moveChat 마우스를 뗄 때
		$("#moveChat").mouseup(function(e) {
			draggable_switch('OFF', $("#chatcontainer"));
		})		
		
		// 페이지 변경 클릭 시
		$(".page-tab").click(function() {		
			//이미 활성화 되어있는 페이지를 클릭했을 경우
			if($(this).hasClass("actived")) {					
				return;
			} else {
				$('#exitBtn').click();
			}
    	});
		
		// 편집모드 시 닫기버튼 활성화
		$("#page-edit-mode-on-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '종료하기'){
				$("#chatDelteIcon").show();
				$("#chatcontainer").resizable("destroy");
				$("#chatcontainer").resizable({
					minHeight: 260,
					minWidth: 430,
					maxHeight: 800,
					resize: function( event, ui ) {
					    $("#chatMessageArea").height(ui.size.height-35-56-65);
					}
				});
				// 드래그 및 리사이즈 기능 전체 설정
				resizable_switch('ON', $("#chatcontainer"));
			}					
		});	
		
		// 편집모드 끝낼 시 닫기버튼 비활성화
		$("#page-edit-mode-off-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '시작하기'){
				$("#chatDelteIcon").hide();
				resizable_switch('OFF', $("#chatcontainer"));
			}					
		});
		
	});

	function connect() {
		wsocket = new WebSocket("ws://localhost:8787/chat-ws.do");		
		wsocket.onopen = onOpen;
		//서버로부터 메시지를 받으면 호출되는 함수 지정
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;

		$('#message').attr('disabled', false);
		$('#plusbutton').attr('disabled', false);
		
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
					+'<img src="resources/image/'+jsonmsg.profileimg+'" class="rounded-circle user_img_msg"></div>'
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
					+'<img src="resources/image/'+jsonmsg.profileimg+'" class="rounded-circle user_img_msg"></div>'
					+ '<div class="nickname"><p style="background-color:'+jsonmsg.randomcolor+';">'
					+ jsonmsg.nickname
					+ '</p></div>'
					+ '<div><span class="badge badge-pill badge-success" style="font-size: 82%;">'
					+ jsonmsg.filename + '</span>'
					+ '<a class="btn" href="/download?filename='
					+ jsonmsg.newFileName + '&name=' + jsonmsg.filename
					+ '"><i class="fa fa-download"></i>download</a></div>'
					+ '</div>');
		} else if (jsonmsg.type == 'video') {	
			appendMessage("<div class='message'>"
					+'<div class="img_cont_msg">'
					+'<img src="resources/image/'+jsonmsg.profileimg+'" class="rounded-circle user_img_msg"></div>'
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
				+ '", "message" :"' + msg + '", "randomcolor" :"' + randomcolor + '", "pageno" :"' + pageno + '", "profileimg" :"' + profileimg
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
				+ '", "url" :"' + url + '", "randomcolor" :"' + randomcolor+ '", "pageno" :"' + pageno + '", "profileimg" :"' + profileimg
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
			url : '/fileupload',
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
					obj.profileimg = profileimg;
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