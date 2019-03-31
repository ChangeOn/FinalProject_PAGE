<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

	#boardcontainer{
  	width: fit-content !impotant;
  	height: fit-content !impotant;
  }
  .container{
  	width: 100%;
  	height: fit-content;
  }

	ul li{
		display:inline;
	}

</style>
<script type="text/javascript">
	
	function searchBtn(){
	var search = document.getElementsByName("searchType")[0];
	var searchType = search.options[search.selectedIndex].value;
	var keyword = document.getElementById("keywordInput").value;
	
	if(searchType == 'n'){
		alert("검색 조건을 선택해주세요.")
	}else if(searchType == 't'){
		if(keyword == null || keyword == ""){
			alert("키워드를 입력해주세요.")
		}else{
			refresh('${pageContext.request.contextPath}/searchTitle?keyword='+keyword);	
		}
	}else if(searchType == 'c'){
		if(keyword == null || keyword == ""){
			alert("키워드를 입력해주세요.")
		}else{
			refresh('${pageContext.request.contextPath}/searchContent?keyword='+keyword);
		}
	}else if(searchType == 'w'){
		if(keyword == null || keyword == ""){
			alert("키워드를 입력해주세요.")
		}else{
			refresh('${pageContext.request.contextPath}/searchId?keyword='+keyword);
		}
	}
	
	}
	$(document).ready(function() {
		
		$("#boardline").mousedown(function(e){
			if ($("#menu-toggle").text() != '시작하기'){
				draggable_switch('ON', $("#boardline"));
			}
		})
		
		$("#boardline").mouseup(function(e) {
			draggable_switch('OFF', $("#boardline"));
		})
		
		// 편집모드 시 닫기버튼 활성화
		$("#page-edit-mode-on-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '종료하기'){
				$("#boardDelteIcon").show();
				$("#boardcontainer").resizable("destroy");
				$("#boardcontainer").resizable({
					minWidth: 550,
					minHeight: 521,
					maxHeight: 521
				});
				resizable_switch('ON', $("#boardcontainer"));
			}					
		});	
		
		// 편집모드 끝낼 시 닫기버튼 비활성화
		$("#page-edit-mode-off-warnning #Y").click(function() {
			if ($("#menu-toggle").text() == '시작하기'){
				$("#boardDelteIcon").hide();
				resizable_switch('OFF', $("#boardcontainer"));
			}					
		});
	
	})
</script>
<style type="text/css">
	th{
		text-align: center;
	}

</style>
</head>
<body> 
<div class="container">
<nav class="navbar navbar-light bg-light justify-content-between" style="width: 100%">
	<form class="form-inline">
		<select name="searchType">
			<option value="n">:::선택:::</option>
			<option value="t">제목</option>
			<option value="c">내용</option>
			<option value="w">작성자</option>
		</select> 
		&nbsp;&nbsp;
		<div class="form-group" align="right">
			<input type="text" name='keyword' id="keywordInput" class="form-control mr-sm-2" type="search" placeholder="키워드를 입력하세요."  />
		</div>
		<button type="button" class="btn btn-outline-secondary" onClick="searchBtn()">검색</button>
		
	</form>
	<i id="boardDelteIcon" class="fas fa-times-circle icon-red float-right" onclick="deleteBoardContainer()"></i>
	</nav>
	
	<table class="table table-hover table-sm" >		
		<thead class="thead-dark">
		<tr >
			<th style="text-align: center" scope="col">번호</th>
			<th style="text-align: center"scope="col">제목</th>
			<th style="text-align: center" scope="col">작성자</th>
			<th style="text-align: center" scope="col">조회</th>
			<th style="text-align: center" scope="col">작성일</th>	
		</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${empty boardlist }">
				<tr>
					<td colspan="5">*****게시글이 없습니다.*****</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${boardlist }" var="bdto">
					<tr>
						<th align="center">${bdto.boardseq }</th>
						<td align="center">
							<a onclick="refresh('${pageContext.request.contextPath}/selectOne?boardseq=${bdto.boardseq }')">${bdto.title }</a>
							
							<span style="color:red;">(${bdto.anscnt })</span>
							
						</td>
						<td align="center">${bdto.id }</td>
						<td align="center">${bdto.viewcnt }</td>
						<td align="center"><fmt:formatDate value="${bdto.regdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
 		<tr>
 			<td colspan="5" align="right">
 				<input type="button" value="글쓰기" class="btn btn-outline-secondary btn-sm" onclick="refresh('${pageContext.request.contextPath}/insertform')" />
 			</td>
 		</tr>
 		<tr>
 			<td colspan="5">
 				<ul class="pagination justify-content-center">
 				<c:if test="${pageMaker.prev }">
 				<li class="page-item">
 					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/boardlist?page=${pageMaker.startPage-1 }')">◀</a>
 				</li>
 				</c:if>
 				<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
 				<li class="page-item">
  					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/boardlist?page=${idx }')" >${idx }</a> 
 				</li>
 				</c:forEach>
 				<c:if test="${pageMaker.next && pageMaker.endPage >0 }">
 				<li class="page-item">
 					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/boardlist?page=${pageMaker.endPage+1 }')">▶</a>
 				</li>
 				</c:if>
 			</ul>
 			</td>	
 		</tr>
 		</tbody>
	</table> 
</div>

</body>
</html>