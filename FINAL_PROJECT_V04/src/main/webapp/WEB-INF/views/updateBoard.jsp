<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script> -->
<script type="text/javascript" src="./resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>

<script type="text/javascript">
$(function(){
    //전역변수 선언
    var obj = [];              
    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: obj,
        elPlaceHolder: "editor",
        sSkinURI: "./resources/editor/SmartEditor2Skin.html",
        htParams : {
            // 툴바 사용 여부
            bUseToolbar : true,            
            // 입력창 크기 조절바 사용 여부
            bUseVerticalResizer : true,    
            // 모드 탭(Editor | HTML | TEXT) 사용 여부
            bUseModeChanger : true,
        }
    });
    //전송버튼
    $("#updateBoard").click(function(){
        //id가 editor인 textarea에 에디터에서 대입
        obj.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);

        var boardseq = document.getElementById("boardseq").value;
        var title = document.getElementById("title").value;
        var content = document.getElementById("editor").value;

        
        $.ajax({
    		url:"updateBoard.do",
    		type:"get",
    		data:"boardseq="+boardseq+"&title="+title+"&content="+content,
    		success:function(data){
    			alert("완료");
    			refresh('${pageContext.request.contextPath}/selectOne.do?boardseq='+boardseq);
    		},
    		error:function(){
    			
    		}

    	
    	})

    });
});
</script>

<!-- 부트스트랩 4.0  -->
<script src="webjars/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="webjars/bootstrap/4.3.1/css/bootstrap.min.css">


</head>
<body>
	<div class="container" style="width: 100%">
			<input type="hidden" id="boardseq" value="${bdto.boardseq }">
		<table class="table">
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" id="title" style="width: 80%" value="${bdto.title }"></td>
			</tr>
			<tr>
				<td colspan="6"><textarea name="editor" id="editor" rows="10" cols="50" style="width: 100%;">${bdto.content }</textarea></td>
			</tr>
			<tr>
				<td colspan="6" align="right">
					<input type="button" id="updateBoard" class="btn btn-outline-secondary btn-sm" value="수정하기"/>
					<input type="button" id="listBoard" class="btn btn-outline-secondary btn-sm" onclick="refresh('${pageContext.request.contextPath}/boardlist.do')" value="목록으로"/>
				</td>
			</tr>
		</table>
	</div>

</body>

</html>