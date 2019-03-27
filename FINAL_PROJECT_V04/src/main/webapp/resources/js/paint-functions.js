// paint.jsp 생성
	function Add_Paint(){
		if ($("#paintcontainer").length > 0){
			alert("그림판은 한개이상 실행이 불가능합니다.");
			return false;
		}
		console.log("그림판 생성");
		$(".container-fluid").prepend(
				"<div id='paintcontainer' style='width:795px;'>"
						+"</div>");
		var url = "/paint";
		$("#paintcontainer").load(url, function(){});
	}