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
		$("#boardcontainer").load(url,function(){
			$("#boardcontainer").resizable({
				minWidth: 550,
				minHeight: 521,
				maxHeight: 521
			});
		})
			
	} 
 	
	function deleteBoard(){
		$("#boardcontainer").remove();
	}