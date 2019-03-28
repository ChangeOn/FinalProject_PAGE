function Add_Calendar(){
	
	console.log("캘린더 생성");
	
	if($("[class~=editor][class~=table]").length==0){
		
		$(".container-fluid").prepend(
			"<div class='caltable draggable border border-dark rounded' id='caltable'>"	+
				"<div class='calendar' id='calendar'>"+
				"</div>"+
			"</div>"	
		)
	// Calendar 실행
		
		resizable_switch('ON', $(".calendar"));
		
		
	$('button:contains("list")').click(function(){
		
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
		
			var id="kh";

			
			$('#calendar').fullCalendar({
	    		  
				     
				    header: {
				      left: 'prev,next today',
				      center: 'title',
				      right: 'month,agendaWeek,agendaDay,listMonth'
				    },
				    
				    defaultDate: today,
				    selectable : true,
				    selectHelper : true,
				    // 월/주/일 버튼 view
				    views : {
				    	month : {
				    		titleFormat : "MMMM YYYY"
				    	},
				    	week:{
				    		titleFormat : " MMMM D YYYY"
				    	},
				    	day:{
				    		titleFormat : "D MMM YYYY"
				    	},
				    	list:{
				    		buttonTest : "listMonth",
				    		titleFormat : "MMMM YYYY",
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
				        		'</div>'+
				        		'<div class="form-group col-xs-3" style="display:inline-block">'+
				        			'<input class="form-control" placeholder="Time (00 : 00)" id="time" name="time" >'+
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
				                event_time=$("#time").val();
				                
				                swal(typeof event_time);
				                
				      
				                
				                
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
				         			url:"insertCal.do",
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
				    		type :"GET",
				    		url : "calDBEvent.do",
				    		data:{
				    			"id":id
				    		},
				    		dataType:'json',
				    		success:function(data) { 
				    			var events =[];
				    			$(data).each(function(){
				    				events.push({
				    					id:$(this).attr('seq'),
				    					title:$(this).attr('title'),
				    					content:$(this).attr('content'),
					    				start:$(this).attr('startdate'),
					    				end:$(this).attr('enddate')
				    				});
				    			});
				    			callback(events);
				    		}
				    	});
	
				    },
				    // Render 생길때 
				    eventRender : function(event,element,view){
				    	element.append("<span class='removebtn'>X</span>");
				    	
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
				    				url : "deleteCal.do",
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
















