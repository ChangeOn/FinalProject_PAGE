
<!-- 
	ë©ì¸ íì´ì§ ì£¼ì íë¬ê·¸ì¸ ë¡ë
	* íì¤í¸ ìëí°, íì´ë¸ ìëí°
	* ìºë²ì¤, ê²ìí, ì±í, ìºë¦°ë, ì§ë
 -->

<!-- Page -->
<script src="${path}/resources/js/page/page-functions.js"></script>
<link href="${path}/resources/css/page/page.css" rel="stylesheet">
<!-- Text -->
<link type="text/css" rel="stylesheet" href="${path}/resources/css/page/text/jquery-te-1.4.0.css">
<script src="${path}/resources/js/page/text/jquery-te-1.4.0.min.js"></script>
<!-- Table -->
<script src="${path}/resources/js/page/table/jquery-table.js"></script>
<!-- Canvas & Paint -->
<script type="text/javascript" src="${path}/resources/js/page/paint/html5-canvas-drawing-app.js"></script>
<script type="text/javascript" src="${path}/resources/js/page/paint/paint-functions.js"></script>
<!-- Board -->
<script type="text/javascript" src="${path}/resources/js/page/board/board-functions.js"></script>
<!-- Chat -->
<script type="text/javascript" src="${path}/resources/js/page/chat/chat-functions.js"></script>
<!-- Calendar -->
<script type="text/javascript" src="${path}/resources/js/page/calendar/calendar-functions.js"></script>
<!-- sweetalert -->
<script type="text/javascript" src="resources/js/sweetalert2.min.js"></script>
<link rel='stylesheet' type='text/css' href='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.css'/>
<!-- moment.js -->
<script type="text/javascript" src="resources/js/moment.min.js"></script>
<!-- fullcalendar -->
<script type="text/javascript" src="resources/js/page/calendar/fullcalendar.min.js"></script>
<link rel='stylesheet' type='text/css' href='resources/css/page/calendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='resources/css/page/calendar/fullcalendar.min.css' />
<link rel='stylesheet' type='text/css' href='resources/css/page/calendar/fullcalendar.print.css' media="print"/>
<script type="text/jacascript" src="resources/js/json2.js"></script>
<!-- Map -->
<link rel="stylesheet" href="resources/css/page/map/map.css">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2fb1c4ff5a5b502155b05f2ba5928593&libraries=services"></script>
<script type="text/javascript" src="${path}/resources/js/page/map/map-functions.js"></script>
<script>
function Add_Map(){
	$(".container-fluid").prepend(
		"<div class='map_wrap card bg-light'>"
			+"<div class='card-header' style='height:2em'>"
			+"<h4><span><i class='fas fa-map-marked-alt'></i></span> Map "
			+"<i id='mapCloseIcon' class='fas fa-times fa-sm float-right' onclick='mapClose()'></i></h4>"
			+"</div>"
			+"<div class='card-body' id='map' style='position: relative; width: 100%; height: 100%; overflow: hidden;'></div>"
			+"<script src='resources/js/page/map/map-functions.js'><"+"/script>"
			// 키워드로 장소검색
			+"<div id='menu_wrap'>"
				+"<div class='option'>"
					+"<div id='optiondiv'>"
						+"<form onsubmit='searchPlaces(); return false;'>"
							+"<input type='text' id='keyword' size='15' placeholder='Keyword + Enter' />"
						+"</form>"
					+"</div>"
				+"</div>"
				// 검색결과인 장소의 마커들을 나타내는 리스트
				+"<ul id='placesList'></ul>"
			+"</div>"
		+"</div>"
	);
}
</script>