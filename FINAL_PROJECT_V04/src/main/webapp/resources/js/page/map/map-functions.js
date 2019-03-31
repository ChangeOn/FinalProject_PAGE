$(function(){	
	// 지도 draggable
	
	$(".map_wrap .card-header").mousedown(function(e) {
		if ($("#menu-toggle").text() != '시작하기'){
			draggable_switch('ON', $(".map_wrap"));
		}		
	})
	$(".map_wrap .card-header").mouseup(function(e) {
		draggable_switch('OFF', $(".map_wrap"));
	})	
	
	$(".map_wrap").resizable({
		resize: function( event, ui ) {
			map.relayout();
		}
	});
	resizable_switch('ON', $(".map_wrap"));	
		
	// 편집모드 시 닫기버튼 활성화
	$("#page-edit-mode-on-warnning #Y").click(function() {
		if ($("#menu-toggle").text() == '종료하기'){
			$("#mapCloseIcon").show();
			$(".map_wrap").resizable("destroy");
			resizable_switch('ON', $(".map_wrap"));
		}					
	});	
	
	// 편집모드 끝낼 시 닫기버튼 비활성화
	$("#page-edit-mode-off-warnning #Y").click(function() {
		if ($("#menu-toggle").text() == '시작하기'){
			$("#mapCloseIcon").hide();
			resizable_switch('OFF', $(".map_wrap"));
		}					
	});	

});

function mapClose(){
	$(".map_wrap").remove();
}

/* 키워드로 검색 */
//마커를 담을 배열입니다
var markers = [];

//장소 검색 객체를 생성합니다
var ps = new daum.maps.services.Places();

var infowindow = new daum.maps.InfoWindow({
	zIndex : 1
});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = {
	center : new daum.maps.LatLng(37.498996, 127.032849), // 지도의 중심좌표 (kh)
	level : 3
//지도의 확대 레벨
};

//지도를 생성합니다
var map = new daum.maps.Map(mapContainer, mapOption);

//키워드로 장소를 검색합니다
searchPlaces();

//키워드 검색을 요청하는 함수입니다
function searchPlaces() {

	var keyword = document.getElementById('keyword').value;

	if (!keyword.replace(/^\s+|\s+$/g, '')) {
		var moveLatLon = new daum.maps.LatLng(37.498996, 127.032849);
		// 지도 확대수준 재설정과 동시에
		// 지도 중심을 부드럽게 이동시킵니다(이동할 거리가 지도 화면보다 크면 빠른 이동)
		map.setLevel(3);
		map.panTo(moveLatLon);
		// 지도 위에 표시되어 있는 마커를 모두 제거합니다
		removeMarker();
	}

	// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
	ps.keywordSearch(keyword, placesSearchCB);
}

//장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
	if (status === daum.maps.services.Status.OK) {

		// 정상적으로 검색이 완료됐으면
		// 검색 목록과 마커를 표출합니다
		displayPlaces(data);

	} else if (status === daum.maps.services.Status.ZERO_RESULT) {

		alert('검색 결과가 존재하지 않습니다.');
		return;

	} else if (status === daum.maps.services.Status.ERROR) {

		alert('검색 결과 중 오류가 발생했습니다.');
		return;

	}
}

//검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

	var listEl = document.getElementById('placesList'), menuEl = document
			.getElementById('menu_wrap'), fragment = document
			.createDocumentFragment(), bounds = new daum.maps.LatLngBounds();

	// 검색 결과 목록에 추가된 항목들을 제거합니다
	removeAllChildNods(listEl);

	// 지도에 표시되고 있는 마커를 제거합니다
	removeMarker();

	for (var i = 0; i < places.length; i++) {

		// 마커를 생성하고 지도에 표시합니다
		var placePosition = new daum.maps.LatLng(places[i].y, places[i].x), marker = addMarker(
				placePosition, i);

		// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		// LatLngBounds 객체에 좌표를 추가합니다
		bounds.extend(placePosition);

		// 마커와 검색결과 항목에 mouseover 했을때
		// 해당 장소에 인포윈도우에 장소명을 표시합니다
		// mouseout 했을 때는 인포윈도우를 닫습니다
		(function(marker, title) {
			daum.maps.event.addListener(marker, 'mouseover', function() {
				displayInfowindow(marker, title);
			});

			daum.maps.event.addListener(marker, 'mouseout', function() {
				infowindow.close();
			});
		})(marker, places[i].place_name);
	}

	// 검색결과 항목들을 결과목록 Elemnet에 추가합니다
	listEl.appendChild(fragment);
	menuEl.scrollTop = 0;

	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
	map.setBounds(bounds);
}

//검색결과 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
	var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',	// 마커이미지 url, 스프라이트 이미지를 씁니다
	imageSize = new daum.maps.Size(36, 37), // 마커 이미지의 크기
	imgOptions = {
		spriteSize : new daum.maps.Size(36, 691), // 스프라이트 이미지의 크기
		spriteOrigin : new daum.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		offset : new daum.maps.Point(13, 37)
	// 마커 좌표에 일치시킬 이미지 내에서의 좌표
	}, markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imgOptions), marker = new daum.maps.Marker(
		{
			position : position, // 마커의 위치
			image : markerImage
		});

	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers.push(marker); // 배열에 생성된 마커를 추가합니다

	return marker;
}

//지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}

//검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
//인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
	var content = '<div style="padding:5px;z-index:1;font-size:14px; overflow: hidden;">' + title + '</div>';

	infowindow.setContent(content);
	infowindow.open(map, marker);
}

//검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}

//주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();




//마커이미지의 주소입니다
var imageSrc = 'https://img.icons8.com/cotton/64/000000/taxi-rank-map-pin.png',
	imageSize = new daum.maps.Size(50, 55), // 마커이미지의 크기입니다
	imageOption = {
		offset : new daum.maps.Point(25, 60) //25, 60
	}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

//마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption);

//클릭한 위치를 표시할 마커입니다
var marker = new daum.maps.Marker({image : markerImage}),
	infoview = new daum.maps.InfoWindow({zindex : 1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

	
//지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
daum.maps.event.addListener(map,'click',function(mouseEvent) {
	searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		if (status === daum.maps.services.Status.OK) {
			var detailAddr = !!result[0].road_address ? result[0].road_address.address_name: ' -';
			var jibun = result[0].address.address_name.substr(6);

			var lat = mouseEvent.latLng.getLat();
			var lng = mouseEvent.latLng.getLng();

				// 커스텀 오버레이에 표시할 컨텐츠 입니다
				var content = '<div class="wrap">'
						+ '    <div class="info">'
						+ '        <div class="title">'
						+ '           	주소정보'
						+ '            <div class="close" onclick="closeOverlay()" title="닫기"></div>'
						+ '        </div>'
						+ '        <div class="body">'
						+ '            <div class="desc">'
						+ '                <div class="ellipsis">(도로명) '
						+ detailAddr
						+ '</div>'
						+ '                <div class="jibun ellipsis">(지번)'
						+ jibun
						+ '</div>'
						+ '                <div><a href="javascript:popUp('
						+ lat + ',' + lng
						+ ');" class="link">길찾기&nbsp|</a>'
						+ '                <a href="javascript:test1('
						+ lat + ',' + lng + ',\'' + detailAddr + '\',\'' + jibun
						+ '\');" class="link">저장</a></div>'
						+ '            </div>'
						+ '        </div>' + '    </div>'
						+ '</div>';

				// 마커를 클릭한 위치에 표시합니다
				marker.setPosition(mouseEvent.latLng);
				marker.setMap(map);

				// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
				infoview.setContent(content);
				infoview.open(map, marker);
		}
	});
});

//클릭한 장소의 좌표를 저장합니다
function test1(lat, lng, detailAddr, jibun){
		$.ajax({
			url: 'insert_map',
			type: 'post',
			//data: JSON.stringify(latlng),
			data: {'lat':lat, 'lng':lng, 'addr':detailAddr, 'jibun':jibun},
			dataType: 'json',
			success: function(data){			
				alert('insert 성공');
				
				var positions = [
					{latlng: new daum.maps.LatLng(lat, lng)}
				];				
				var pinImageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'; 
				
				for (var i = 0; i < positions.length; i ++) {
				    
				    // 핀 이미지의 크기 입니다
				    var pinImageSize = new daum.maps.Size(24, 35); 
				    
				    // 핀 이미지를 생성합니다    
				    var pinImage = new daum.maps.MarkerImage(pinImageSrc, pinImageSize); 
				    
				    // 핀을 생성합니다
				    var pin = new daum.maps.Marker({
				        map: map, // 핀을 표시할 지도
				        position: positions[i].latlng, // 핀을 표시할 위치
				        image : pinImage // 핀 이미지 
				    });

				daum.maps.event.addListener(pin, 'click', function() {
					var content = '<div class="wrap">'
							+ '    <div class="info">'
							+ '        <div class="title">'
							+ '           	주소정보'
							+ '            <div class="close" onclick="closeOverlay()" title="닫기"></div>'
							+ '        </div>'
							+ '        <div class="body">'
							+ '            <div class="desc">'
							+ '                <div class="ellipsis">(도로명) '
							+ detailAddr
							+ '</div>'
							+ '                <div class="jibun ellipsis">(지번)'
							+ jibun
							+ '</div>'
							+ '                <div><a href="javascript:popUp('
							+ lat + ',' + lng
							+ ');" class="link">길찾기&nbsp</a></div>'
							+ '            </div>'
							+ '        </div>' + '    </div>'
							+ '</div>';
					infoview.setContent(content);
				    infoview.open(map, pin);
				    });				    
				}			
			},
			error: function(){
				alert('에러');
			}
		});
}


//길찾기 클릭 시 호출되는 함수입니다
function popUp(lat, lng) {
	window.open('http://map.daum.net/link/to/목적지,' + lat + ',' + lng, 'navi', 'width=750, height=600, top=100, left=500');	
}

function searchDetailAddrFromCoords(coords, callback) {
	// 좌표로 법정동 상세 주소 정보를 요청합니다
	geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

//마커를 클릭했을 때 오버레이를 표시합니다
daum.maps.event.addListener(marker, 'click', function() {
	// overlay.setMap(map);
	// infowindow.setContent(content);
	infoview.open(map, marker);
});

//오버레이를 닫기 위해 호출되는 함수입니다
function closeOverlay() {
	// overlay.setMap(null);
	infoview.close();
}


//DB에 저장된 핀들을 맵이 로드되자마자 불러와 지도에 표시합니다
$(function(){
 	$.ajax({
		url: 'select_map',
		dataType: 'json',
		success: function(data){
			console.log(data);
			console.log(Object.keys(data).length);
			//var geoinfo = JSON.stringify(data);
			//alert(geoinfo[0].latitude);
			
			var positions = new Array;
			
   			for (var i=0; i<Object.keys(data).length; i++){
				
				positions.push({latlng: new daum.maps.LatLng(data[i].latitude, data[i].longitude)});
				
 				var pinImageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'; 
				    
				    // 핀 이미지의 크기 입니다
				    var pinImageSize = new daum.maps.Size(24, 35); 
				    
				    // 핀 이미지를 생성합니다    
				    var pinImage = new daum.maps.MarkerImage(pinImageSrc, pinImageSize); 
				    
 				    // 핀을 생성합니다
				    var pin = new daum.maps.Marker({
				        map: map, // 핀을 표시할 지도
				        //position: positions[j].latlng, // 핀을 표시할 위치
				        position: new daum.maps.LatLng(data[i].latitude, data[i].longitude),
				        image : pinImage // 핀 이미지 
				        
				    });
			    (function(pin, addr, jibun, latitude, longitude) {
   				    daum.maps.event.addListener(pin, 'click', function() {
   						// pin infoview에 표시할 컨텐츠 입니다
   						var content = '<div class="wrap">'
   								+ '    <div class="info">'
   								+ '        <div class="title">'
   								+ '           	주소정보'
   								+ '            <div class="close" onclick="closeOverlay()" title="닫기"></div>'
   								+ '        </div>'
   								+ '        <div class="body">'
   								+ '            <div class="desc">'
   								+ '                <div class="ellipsis">(도로명) '
   								+ addr
   								+ '</div>'
   								+ '                <div class="jibun ellipsis">(지번)'
   								+ jibun
   								+ '</div>'
   								+ '                <div><a href="javascript:popUp('
   								+ latitude + ',' + longitude
   								+ ');" class="link">길찾기&nbsp</a></div>'
   								+ '            </div>'
   								+ '        </div>' + '    </div>'
   								+ '</div>';

   						// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
   						infoview.setContent(content);
   						infoview.open(map, pin);
				    });
			    })(pin, data[i].addr, data[i].jibun, data[i].latitude, data[i].longitude)
   			}
   		},
		error: function(){
			alert('에러');
		}
	}); 
	
});


/* 지도 컨트롤 관련 */
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new daum.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new daum.maps.ZoomControl();
map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);