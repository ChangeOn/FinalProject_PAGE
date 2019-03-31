//chat.jsp 생성
	function Add_Chat(){
		
		$.ajax({
			url : '/checksavechat',
			type : 'POST',
			success : function(result) {
				if (result == "exist") {
					alert("채팅창은 페이지에 한개이상  추가가 불가능합니다.");
					return false;
				} else if (result == "none") {
					console.log("채팅창 생성");	
					
					$(".container-fluid").prepend(
							"<div id='chatcontainer' style='width:795px;'>"
									+"</div>");
					var url = "/web/chat";
					$("#chatcontainer").load(url, function(){
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
					});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log('ERRORS: ' + textStatus);
			}
		});	
		
	}	
	
	function deleteChat() {		
		$('#exitBtn').click();
		$('.container-fluid #chatcontainer').remove();
	}