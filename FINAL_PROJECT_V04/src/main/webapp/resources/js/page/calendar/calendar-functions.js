function Add_Calendar(){
	
	console.log("캘린더 생성");
	
	if($("[class~=editor][class~=table]").length==0){
		
		if ($("#calcontainer").length > 0){
			alert("캘린더는 한개이상 실행이 불가능합니다.");
			return false;
		}
		
		$(".container-fluid").prepend(
				
				"<div class='calcontainer draggable border border-dark rounded' id='calcontainer' style='width:700px; position:relative'>"+
				"<div class='calcontrol' style='position:relative; height:30px;'><img id='conimg' src='resources/image/delete-button.png' style='width:20px; height:20px; float:right; '></div>"+
					"<div class='caltable'>"+
						
						"<div class='calendar' id='calendar'></div>"+
					"</div>"+
				"</div>"	

		)
		// Calendar 실행
		$(".calcontainer").resizable({
			minHeight: 506,
			minWidth: 500,
			resize: function( event, ui ) {
				$('#calendar').fullCalendar('option', 'height', ui.size.height-32);
			}
		});
		// 드래그 및 리사이즈 기능 전체 설정
		resizable_switch('ON', $("#calcontainer"));
		draggable_switch('ON', $("#calcontainer"));		
		
		// 편집모드 시 닫기버튼 활성화
		$("#page-edit-mode-on-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '종료하기'){
				$("#conimg").show();
				$(".calcontainer").resizable("destroy");
				$(".calcontainer").resizable({
					minHeight: 506,
					minWidth: 500,
					resize: function( event, ui ) {
						$('#calendar').fullCalendar('option', 'height', ui.size.height-32);
					}
				});
				resizable_switch('ON', $("#calcontainer"));
				draggable_switch('ON', $("#calcontainer"));	
			}					
		});	
		
		// 편집모드 끝낼 시 닫기버튼 비활성화
		$("#page-edit-mode-off-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '시작하기'){
				$("#conimg").hide();
				resizable_switch('OFF', $("#calcontainer"));
				draggable_switch('OFF', $("#calcontainer"));
			}					
		});
		
		$("#conimg").click(function(){
			$(".calcontainer").remove();
		})	
	
	$(document).ready(function() {		
		
			var seq=0;
			var myStartDate='';
			var myEndDate='';
			// 현재의 날짜
			var today=new Date();
			var date=today.getDate();
			var month=today.getMonth();
			var year=today.getFullYear();
		
			var id="";
			
			$.ajax({
				
				type:"post",
				url:"calId",
				dataType:"text",
				success:function(data){
					console.log("id가져오기 성공");
					id=data;
					console.log(id);
				},
				error:function(request,status,error){
					console.log("id가져오기 실패")
					swal("id 가져오기 실패!"+"\n"+"code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
			
			
			$(".fc-list-item-time").hide();
			
			$('#calendar').fullCalendar({
	    		  
				     
				    header: {
				      left: 'prev,next today',
				      center: 'title',
				      right: 'month,agendaWeek,listMonth'
				    },
				    
				    defaultDate: today,
				    selectable : true,
				    selectHelper : true,
				    // 월/주/일 버튼 view
				    views : {
				    	month : {
				    		titleFormat : "YYYY MMMM"
				    	},
				    	week:{
				    		titleFormat : "DD MMMM YYYY"
				    	
				    	},
				    	
				    	list:{
				    		buttonTest : "listMonth",
				    		titleFormat : "YYYY MMMM",
				    		listMonthFormat : "YYYY-MM-DD"
				    	}
				    	
				    },
				    timeFormat:{
				    	"" : "HH:mm", // 월간
				    	agenda : "HH:mm{-HH:mm}" // 주간, 일간
				    },
				    allDayText : '시간',
				    axisFormat : 'tt hh',
				    
				    // 날짜 클릭으로 event 추가
				  
				    select : function(startDate,endDate){
				    
				    //alert('selected ' + startDate.format() + ' to '+ endDate.format());
				    
				    	
				   
				    swal({
				    	title: 'Create an Event',
				        html: '<div class="form-group">' +
				        			'<input class="form-control" placeholder="Event Title" id="title" name="title">' +
				        		'</div>'+
				        		'<div class="form-group">'+
				        			'<input class="form-control" placeholder="Event Content" id="content" name="content">'+
				        		'</div>',

				        showCancelButton: true,
				        confirmButtonClass: 'btn btn-success',
				        confirmButtonText : '일정 추가',
				        cancelButtonClass: 'btn btn-danger',
				        cancelButtonText : '취소',
				        buttonsStyling: false
				     }).then(function(result) {

				                var eventData;
				                event_title = $('#title').val();
				                event_content=$('#content').val();
				                event_starttime=$("#stime").val();
				                event_endtime=$("#etime").val();
				                
				                console.log(typeof event_starttime);
				                
				      
				                
				                
				                //startDate.isValid() & endDate.isValid()
				             	
				                if(event_title && event_content){
				             		
				             		eventData={
				             				id : (++seq),
					             			title : event_title,
					             			content :  event_content,
					             			//start : startDate+,
					             			//end : endDate,
					             			start: moment(startDate).format('YYYY-MM-DD'),
					             			end:moment(endDate).format('YYYY-MM-DD'),
					             			allDay : false	
				             		};
				             		$('#calendar').fullCalendar('renderEvent',eventData,true);
				       
				             	}else if(event_title==="" || event_content===""){
				             		swal("You need to write something!");
				             		return false;
				             	}else{
				             		swal.close();
				             	}
				             	
				             	// DB에 Insert
				             	$.ajax({
				         			
				         			type : "post",
				         			url:"insertCal",
				         			data:{
				         				"seq":seq,
				         				"id":id,
				         				"startdate":moment(startDate).format('YYYY-MM-DD'),
				         				"enddate":moment(endDate).format('YYYY-MM-DD'),
				         				"title":event_title,
				         				"content":event_content
				      
				         			},
				         			success:function(data){
				         				console.log("event 추가성공")
				         				swal("추가성공!"+event._id);
				         			},
				         			errer:function(request,status,error){
				         				console.log("event 추가실패")
				    					swal("추가 실패!"+"\n"+"code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				         			}
				         			
				         		}); 
				             	$('#calendar').fullCalendar('unselect');
				             	
				    	});
				    },
				    // id 속성
				    eventAfterRender:function(event,element,view){
				    	$(element).attr("id",event.id);
				    },
				    
				    navLinks: true, // can click day/week names to navigate views
				    editable: true,
				    /*
				    dayClick : function (date,jsEvent,view){
				    	
				    	swal({
				    	  	text: date.format(),
				    		});

				    	
				    	$(this).css('background-color','skyblue');
				    },*/
				    
				    eventLimit: false, // allow "more" link when too many events
				    
				    //start,end,callback
				    events : function(start,end,timezone,callback){
				    	
				    	$.ajax({
				    		type :"POST",
				    		url : "calDBEvent",
				    		dataType:'json',
				    		success:function(data) { 
				    			var events =[];
				    			$(data).each(function(){
				    				events.push({
				    					id:decodeURIComponent($(this).attr('seq')),
				    					title:decodeURIComponent($(this).attr('title')),
				    					content:decodeURIComponent($(this).attr('content')),
					    				start:moment($(this).attr('startdate')).format('YYYY-MM-DD'),
					    				end:moment($(this).attr('enddate')).format('YYYY-MM-DD')
				    				});
				    			});
				    			callback(events);
				    		}
				    	});
	
				    },
				    // Render 생길때 
				    eventRender : function(event,element,view){
				    	element.append("<span class='removebtn'>X</span>");
				    	$(".fc-list-item-time").hide();
				    	//if(view.name=="listMonth"){
				    	//	element.find('.fc-list-item-title').html(event.title);
				    	//}
				    	
				    },
				    // 삭제!
				    eventClick:function(event,jsEvent,view){
				    	
				    	
				    	swal({
				    		showCancelButton: true,
				    		title:'정말 삭제하시겠습니까?',
				    		confirmButtonClass : 'btn btn-success',
				    		confirmButtonText : 'Delete',
				    		cancelButtonClass :'btn btn-danger',
				    		cancelButtonText : 'Cancel'
				    	}).then(function(result){
				    		if(result.value){
				    			$("#calendar").fullCalendar('removeEvents',event._id);
				    			
				    			// 삭제 정보 보내기
				    			$.ajax({
				    				type : "post",
				    				url : "deleteCal",
				    				data : {
				    					"seq":event._id
				    				},
				    				success(data){
				    					console.log("event 삭제성공")
				         				swal(event._id+"글 삭제성공!");
				    				},
				    				error(request,status,error){
				    					console.log("event 삭제실패")
				    					swal("삭제 실패!"+"\n"+"code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				    				}
				    				
				    			})
				    			
				    			
				    			
				    		}else{
				    			swal.close();
				    		}
	
				    	});
				  
				    }
				    			
				  });
						
		});
		
	}
	
}
















