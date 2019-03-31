// boardlist.jsp 생성
	function Add_Board(){
		console.log("게시판 생성");
		$(".container-fluid").prepend(
				"<div id='boardline' class='draggable table border border-dark rounded'>"
					+"<div id='boardcontainer' class='container border border-black rounded'>"
					+"</div>"
				+"</div>");
		var url = "/boardlist";
		$("#boardcontainer").load(url, function(){
			$("#boardcontainer").resizable({
				minWidth: 550,
				minHeight: 521,
				maxHeight: 521
			});
		});
	}
	
	function refresh(url){
		$("#boardcontainer").resizable("destroy");
		$("#boardcontainer").load(url,function(){
			$("#boardcontainer").resizable({
				minWidth: 550,
				minHeight: 521,
				maxHeight: 521
			});
			// 토글 버튼 상태에따라  크기변경 여부 활성화/비활성화
			if ($("#menu-toggle").text() == '시작하기'){
				$("#boardDelteIcon").hide();
				resizable_switch('OFF', $("#boardcontainer"));
			} else if ($("#menu-toggle").text() == '종료하기'){
				$("#boardDelteIcon").show();
				resizable_switch('ON', $("#boardcontainer"));				
			}			
		})
	} 
 	
	function deleteBoardContainer(){
		$("#boardcontainer").remove();
	}