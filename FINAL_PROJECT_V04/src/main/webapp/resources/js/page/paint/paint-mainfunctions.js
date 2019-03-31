$(document).ready(function() {
         
        sock = new WebSocket("ws://localhost:8787/paint-ws.do");
         
        var ctx = null;
         
        var isDown = false;
        var isLoad = false;
        var prevX = 0;
        var prevY = 0;
               
        // 마우스를 눌렀을 때
        $("#paint").mousedown(function(e) {
             
            if ( !isEditable ) {
                return;
            }
             
            var selectWidth = $("#widthSize option:selected").val();
    		
            var c = document.getElementById("paint");
            ctx = c.getContext("2d");
             
            ctx.strokeStyle = $("#color").val();
            ctx.beginPath();
            ctx.lineWidth = selectWidth;
            
            isDown = true;
            prevX = e.pageX;
            prevY = e.pageY;
            
            if ( isDown ) {
            	var canvasLeft = $("#paint").offset().left;
            	var canvasTop  = $("#paint").offset().top; // 캔버스의 위치
            	point.prevX = prevX-canvasLeft;
                point.prevY = prevY-canvasTop;
                point.nowX = e.pageX-canvasLeft;
                point.nowY = e.pageY-canvasTop;
	            point.lineWidth = selectWidth;
	            point.color = $("#color").val();
	             
	            sock.send( JSON.stringify(point) );
            }
        });
         
        var point = {};
         
        // 마우스를 움직일 때
        $("#paint").mousemove(function(e) {
             
            if ( !isEditable ) {
                return;
            }
            
            if ( isDown ) {
            	var canvasLeft = $("#paint").offset().left;
            	var canvasTop  = $("#paint").offset().top;// 캔버스의 위치                 
            	var selectWidth = $("#widthSize option:selected").val();
            	
                point.prevX = prevX-canvasLeft;
                point.prevY = prevY-canvasTop;
                point.nowX = e.pageX-canvasLeft;
                point.nowY = e.pageY-canvasTop;
                point.lineWidth = selectWidth;
                point.color = $("#color").val();                
              	
                sock.send( JSON.stringify(point) );
                
                ctx.moveTo(prevX-canvasLeft, prevY-canvasTop);
                ctx.lineTo(e.pageX-canvasLeft,e.pageY-canvasTop);
                ctx.lineJoin = ctx.lineCap = 'round';
                ctx.stroke();
                 
                prevX = e.pageX;
                prevY = e.pageY;
            }
        });
         
        // 마우스를 뗄 때
        $("#paint").mouseup(function(e) {
             
            if ( !isEditable ) {
                return;
            }
             
            if ( isDown ) {
            	var canvasLeft = $("#paint").offset().left;
            	var canvasTop  = $("#paint").offset().top; // 캔버스의 위치
            	var selectWidth = $("#widthSize option:selected").val();
            	
            	point.prevX = prevX-canvasLeft;
                point.prevY = prevY-canvasTop;
                point.nowX = e.pageX-canvasLeft;
                point.nowY = e.pageY-canvasTop;
	            point.lineWidth = selectWidth;
	            point.color = $("#color").val();
	             
	            sock.send( JSON.stringify(point) );
	            
	            isDown = false;
	            ctx.closePath();
            }  
        });
         
        $("#fill").click(function(){
             
            if ( !isEditable ) {
                return;
            }
             
            var c = document.getElementById("paint");
            ctx = c.getContext("2d");
             
            // 캔버스 채우기 적용
            ctx.beginPath();
            ctx.rect(0, 0, 750, 750);
            ctx.fillStyle = $("#color").val();
            ctx.fill();
            ctx.closePath();
             
            var fill = {};
            fill.mode = "fill";
            fill.color = $("#color").val();
            
            // 채우기 연속 클릭 차단
            $('#fill').attr('disabled', true);
            $('#fill').attr('value', "10초 비활성화");
            
            setTimeout(function() {
            	$('#fill').attr('disabled', false);
            	$('#fill').attr('value', "채우기");
            }, 10000)
            
            sock.send( JSON.stringify(fill) );             
        });
         
        sock.onmessage = function(evt) {
        	//console.log(evt.data);           
            
            if ( evt.data == "!@#OK" || evt.data == "!@#NO" || evt.data == "!@#REQUESTCANVAS") {
                 
                if ( evt.data == "!@#OK" ) {
                    isEditable = true;
                                    
                }
                else {
                    isEditable = false;
                }
                
                if ( evt.data == "!@#REQUESTCANVAS" ) {
                	var canvas = document.getElementById("paint");
                	var dataurl = canvas.toDataURL('image/png', 1.0);

                	$.ajax({
            			url : '/saveimage',
            			type : 'POST',
            			data : { dataurl: dataurl },
            			success : function(data) {
            				//console.log(data);	
            				var jsonStr;

            				//JSON
            				if (data.type == "copycanvas") {
            					var obj = {};
            					var jsonStr;

            					//JSON형태로 웹소켓 서버에 메세지 보내기
            					obj.type = "copycanvas";
            					obj.data = data.data;
            					jsonStr = JSON.stringify(obj);

            					sock.send(jsonStr);
            				}
            			},
            			error : function(jqXHR, textStatus, errorThrown) {
            				console.log('ERRORS: ' + textStatus);
            			}
            		});
                }                 
                return;
            }
            var drawData = JSON.parse(evt.data); 
            
            if ( !isLoad && drawData.type == 'copycanvas'){
            	var copyCanvas = document.getElementById('paint');     
            	var copyContext = copyCanvas.getContext('2d');            	
            	
            	$.ajax({
        			url : '/loadimage',
        			type : 'POST',
        			data : { dataurl: drawData.data },
        			success : function(data) {        				        				
        				var image = new Image();
                    	image.src = data;
                    	  
                    	image.onload = function(){
                    	   copyContext.drawImage(image,0,0);
                    	}  
                    	isLoad = true;
                    	console.log("기존 캔버스 불러오기 완료");
        			},
        			error : function(jqXHR, textStatus, errorThrown) {
        				console.log('ERRORS: ' + textStatus);
        			}
        		});
            } else {
             
            var c = document.getElementById("paint");
            var otherCtx = c.getContext("2d");
             
            //console.log(drawData.mode);
            if ( drawData.mode != undefined && drawData.mode == "fill" ) {
                 
                otherCtx.beginPath();
                otherCtx.rect(0, 0, 750, 750);
                otherCtx.fillStyle = drawData.color;
                otherCtx.fill();
                otherCtx.closePath();
                 
                return;
            }
             
            otherCtx.strokeStyle = drawData.color;
            otherCtx.lineWidth = drawData.lineWidth;
            
            var canvasTop  = $("#paint").offset().top;// 캔버스의 위치 
            var canvasLeft = $("#paint").offset().left;
            
            otherCtx.beginPath();
            otherCtx.moveTo(drawData.prevX, drawData.prevY);
            otherCtx.lineJoin = otherCtx.lineCap = 'round';
            otherCtx.lineTo(drawData.nowX, drawData.nowY);            
            otherCtx.stroke();
            otherCtx.closePath();
            }
        }; 
        
    	// movePaint 마우스를 눌렀을 때
		$("#movePaint").mousedown(function(e) {
			if ($("#menu-toggle").text() != '시작하기'){
				draggable_switch('ON', $("#paintcontainer"));			
			}
		})
		// movePaint 마우스를 뗄 때
		$("#movePaint").mouseup(function(e) {
			draggable_switch('OFF', $("#paintcontainer"));
		})
		
		
		// 편집모드 시 닫기버튼 활성화
		$("#page-edit-mode-on-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '종료하기'){
				$("#paintexit").show();
				$("#paintminimize").show();
			}					
		});	
		
		// 편집모드 끝낼 시 닫기버튼 비활성화
		$("#page-edit-mode-off-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '시작하기'){
				$("#paintexit").hide();
				$("#paintminimize").hide();
			}					
		});		
		
		// paintexit 클릭, 페인트 div 삭제
		$("#paintexit").click(function() {
			if ($("#menu-toggle").text() != '시작하기'){
				$("#paintcontainer").remove();			
			}			
		})
		
		// paintminimize 클릭, 그림판 최소화
		$("#paintminimize").click(function() {
			if ($("#menu-toggle").text() != '시작하기'){
				$("#paintcontainer div .card-body").toggle();
				$("#paintcontainer div .card-footer").toggle();			
			}			
		})
    });