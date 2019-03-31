// paint.jsp 생성
	function Add_Paint(){		
		$.ajax({
			url : '/checksavepaint',
			type : 'POST',
			success : function(result) {
				if (result == "exist") {
					alert("그림판은 페이지에 한개이상  추가가 불가능합니다.");
					return false;
				} else if (result == "none") {					
					console.log("그림판 생성");
					$(".container-fluid").prepend(
							"<div id='paintcontainer' style='width:795px;'>"
									+"</div>");
					var url = "/paint";
					$("#paintcontainer").load(url, function(){});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log('ERRORS: ' + textStatus);
			}
		});	
		
		
	}