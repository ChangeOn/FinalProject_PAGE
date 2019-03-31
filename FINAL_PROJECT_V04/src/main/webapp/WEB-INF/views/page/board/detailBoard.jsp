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
  	width: fit-content;
  	height: fit-content;
  }

</style>
<!-- <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script> -->
<script type="text/javascript">

	$(function(){
		// 댓글 입력
		$("#insertAnswer").click(function(){
			var boardseq = document.getElementById("boardseq").value;
			var anscontent = document.getElementById("ansBoard").value;
			
			if(anscontent == null || anscontent == ""){
				alert("댓글내용을 입력해주세요.")
			}else{
				$.ajax({
		    		url:"insertAnswer",
		    		type:"get",
		    		data:"boardseq="+boardseq+"&anscontent="+anscontent,
		    		success:function(data){
		    			refresh('${pageContext.request.contextPath}/selectOne?boardseq='+boardseq);
		    		},
		    		error:function(){
		    			
		    		}
		    	
		    	
		    	})

				$("#insertAnswerForm").submit();
			}			
		})
	})	
		// 댓글 삭제
		function deleteAnswer(ansno){
		var ansno = ansno;
		var boardseq = $("#boardseq").val();

		
		if(confirm("정말 삭제하시겠습니까?") == true){
			refresh('${pageContext.request.contextPath}/deleteAnswer?boardseq='+boardseq+'&ansno='+ansno);
		}else{
			return;
		}
		
	}
		// 게시글 삭제
		function deleteBoard(boardseq){
		 var boardseq = boardseq;
		 if(confirm("정말 삭제하시겠습니까?") == true){
				refresh('${pageContext.request.contextPath}/deleteform?boardseq='+boardseq);
			}else{
				return;
			}
	}
	

</script>


<!-- 부트스트랩 4.0  -->
<script src="webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="webjars/bootstrap/4.3.1/css/bootstrap.min.css">

</head>
<body>
	<!-- 게시판 글 상세보기 -->
<div class="container">
	<div id="detailBoard">
		<input type="hidden" name="boardseq" id="boardseq" value="${bdto.boardseq }"/>
		<table class="table table-sm">
			<tr>
				<td><h1>${bdto.title }</h1></td>
			</tr>
			<tr>
				<th>${bdto.id }</th>
				<td align="right"><fmt:formatDate value="${bdto.regdate }" pattern="yyyy-MM-dd HH:mm:ss"/> / ${bdto.viewcnt }</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="width: 100%; height: 100%">${bdto.content }</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<c:choose>
						<c:when test="${bdto.id eq userVO.user_id }">
							<input type="button" value="수정" class="btn btn-outline-secondary btn-sm" onclick="refresh('${pageContext.request.contextPath}/updateform?boardseq=${bdto.boardseq}')">
							<input type="button" value="삭제" class="btn btn-outline-secondary btn-sm" onclick="deleteBoard(${bdto.boardseq})">
							<input type="button" value="목록으로" class="btn btn-outline-secondary btn-sm" onclick="refresh('${pageContext.request.contextPath}/boardlist')">
						</c:when>
						<c:otherwise>
							<input type="button" value="목록으로" class="btn btn-outline-secondary btn-sm" onclick="refresh('${pageContext.request.contextPath}/boardlist')">
						</c:otherwise>
					</c:choose>
					
				</td>
			</tr>
		</table>

	<!-- 댓글 쓰는 곳  -->
	<div style="width:100%;">
		<input type="hidden" name="boardseq" value="${bdto.boardseq }"/>
		<table class="table" style="width: 100%">
			<tr>
				<td colspan="2">
				<textarea id="ansBoard" name="anscontent" class="form-control" cols="80" rows="2" placeholder="댓글을 작성해주세요" ></textarea>
				</td>
				<td align="right">
				<input type="button" value="댓글작성" id="insertAnswer" class="btn btn-outline-secondary"/>	
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 댓글 목록 출력 위치 -->
	<div id="listAnswer">
		<table class="table table-sm">
		
			<col width ="80"/>
			<col width =""/>
			<col width ="200"/>
			<col width ="70"/>
			
			<c:choose>
				<c:when test="${empty listAnswer }">
					
					<tr>
						<td colspan="4">--댓글이 없습니다.---</td>
					</tr>
				</c:when>
			<c:otherwise>
				<c:forEach items="${listAnswer }" var="adto">
				<input type="hidden" id="ansno" value="${adto.ansno }"/>
					<tr>
						<td align="left">${adto.id }</td>
						<td>${adto.anscontent}</td>
						<td align="center"><fmt:formatDate value="${adto.ansregdate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					<c:choose>
						<c:when test="${adto.id eq userVO.user_id  }">
						<td><button onClick="deleteAnswer(${adto.ansno})" class="btn btn-outline-secondary btn-sm">삭제</button></td>
						</c:when>
						<c:otherwise>
							<tr></tr>
						</c:otherwise>
					</c:choose>
					</tr>
				</c:forEach>
			</c:otherwise>		
			</c:choose>
			<tr>
			<td colspan="4" >
			<ul class="pagination justify-content-center">
 				<c:if test="${pageMaker.prev }">
 				<li class="page-item">
 					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/ansPaging.do?page=${pageMaker.startPage-1 }&boardseq=${bdto.boardseq }')" >◀</a>
 				</li>
 				</c:if>
 				<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
 				<li class="page-item">
 					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/ansPaging.do?page=${idx }&boardseq=${bdto.boardseq }')">${idx }</a>
 				</li>
 				</c:forEach>
 				<c:if test="${pageMaker.next && pageMaker.endPage >0 }">
 				<li class="page-item">
 					<a class="page-link" onclick="refresh('${pageContext.request.contextPath}/ansPaging.do?page=${pageMaker.endPage+1 }&boardseq=${bdto.boardseq }')">▶</a>
 				</li>
 				</c:if>
 			</ul>
 			</td>
 			</tr>
		</table>
	</div>
</div>
</div>
</body>

</html>