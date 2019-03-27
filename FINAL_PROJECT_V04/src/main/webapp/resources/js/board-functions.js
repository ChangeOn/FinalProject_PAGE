// boardlist.jsp 생성
	function Add_Board(){
		console.log("게시판 생성");
		$(".container-fluid").prepend(
				"<div id='boardcontainer' class='container border border-black rounded' style='width:fit-content;'>"
					+"</div>");
		var url = "${pageContext.request.contextPath}/boardlist.do";
		$("#boardcontainer").load(url, function(){
			$("#boardcontainer").resizable({
				minHeight: 577,
				minWidth: 532,
			});
		});
	}
	
 	function refresh(url){
		$("#boardcontainer").load(url, function(){
			$("#boardcontainer").resizable({
				minHeight: 577,
				minWidth: 532,
			});
		});	
	} 
 	
	function deleteBoard(){
		$("#boardcontainer").remove();
	}