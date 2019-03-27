<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>paint</title>

<script type="text/javascript">
     
    var sock = null;
    var isEditable = false;   
    
    var message = {};
     
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
			$("#paintcontainer").draggable({
				disabled : false
			});
		})
		// movePaint 마우스를 뗄 때
		$("#movePaint").mouseup(function(e) {
			$("#paintcontainer").draggable({
				disabled : true
			});
		})
		
		// paintexit 클릭, 페인트 div 삭제
		$("#paintexit").click(function() {
			$("#paintcontainer").remove();
		})
		
		// paintminimize 클릭, 그림판 최소화
		$("#paintminimize").click(function() {
			$("#paintcontainer div .card-body").toggle();
			$("#paintcontainer div .card-footer").toggle();
		})
    });
    
    
    
</script>

<style type="text/css">
input[type=color].form-control {
	height: calc(2.25rem + 2px) !important;
}

.card {
	background: #C1C0C3;
}

#paint {
	background: white;
}
</style>

</head>
<body style="height: 100%;">

	<div class="card text-white" style="width: 795px; height: 100%;">
		<h4 class="card-header" id="movePaint" style="padding: 0;">
			<span class="fas fa-palette"></span> Paint <i id="paintexit"
				class="fas fa-times float-right" style="margin: 0 10px 0 10px;"></i>
			<i id="paintminimize" class="fas fa-minus float-right"></i>
		</h4>
		<div class="card-body" style="padding: 3;">
			<canvas id="paint" width="750" height="750"
				style="border: 1px solid #333; border-radius: 5px;"></canvas>
		</div>
		<div class="card-footer">
			<div class="input-group">
				<label class="col-sm-3 col-form-label text-right">글쓰기 색상:</label> <input
					type="color" class="col-sm-2 form-control" id="color"
					value="#000000" style="width: 100px;" /> <input type="button"
					class="btn btn-light" id="fill" value="채우기" /> <label
					class="col-sm-4 col-form-label float-right text-right">크기:</label>
				<div class="widthSize float-right">
					<select id="widthSize" class="form-control">
						<option value="1">1px</option>
						<option value="3" selected="selected">3px</option>
						<option value="5">5px</option>
						<option value="7">7px</option>
						<option value="10">10px</option>
						<option value="15">15px</option>
						<option value="20">20px</option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<%-- <div style="width:600px; float:left;">
        <c:import url="/chat" />
    </div> --%>
</body>
</html>