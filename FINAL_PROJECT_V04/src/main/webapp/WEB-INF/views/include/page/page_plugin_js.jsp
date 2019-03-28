
<!-- 
	메인 페이지 주요 플러그인 로드
	* 텍스트 에디터, 테이블 에디터
	* 캔버스, 게시판, 채팅, 캘린더, 지도
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
<!-- Map -->
<link rel="stylesheet" href="resources/css/page/map/map.css">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2fb1c4ff5a5b502155b05f2ba5928593&libraries=services"></script>
<script type="text/javascript" src="${path}/resources/js/page/map/map-functions.js"></script>
<script>
	function Add_Map(){
		$(".container-fluid").prepend(
			"<div class='map_wrap'>"
				+"<div id='map' style='position: relative; width: 60%; height: 100%; overflow: hidden;'></div>"
				+"<script src='resources/js/page/map/map-functions.js'><"+"/script>"
				// 키워드로 장소검색
				+"<div id='menu_wrap'>"
					+"<div class='option'>"
						+"<div>"
							+"<form onsubmit='searchPlaces(); return false;'>"
								+"<input type='text' id='keyword' size='15' placeholder='키워드 입력' />"
								+"<button type='submit'>Search</button>"
							+"</form>"
						+"</div>"
					+"</div>"
					// 검색결과인 장소의 마커들을 나타내는 리스트
					+"<ul id='placesList'></ul>"
				+"</div>"				
				// 화면에 지도 고정
				+"<div id='fix_wrap'>"
						+"<button id='fixB' onclick='button1_click();'>fix</button>"
				+"</div>"
			+"</div>"
		);
	}
</script>