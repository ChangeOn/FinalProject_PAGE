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
		});
	}	
	
	function deleteChat() {		
		$('#exitBtn').click();
		$('#chatcontainer').remove();
	}