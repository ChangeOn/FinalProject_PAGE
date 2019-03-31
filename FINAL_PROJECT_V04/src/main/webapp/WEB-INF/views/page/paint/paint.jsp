<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>paint</title>

<script type="text/javascript" src="/resources/js/page/paint/paint-mainfunctions.js"></script>
<script type="text/javascript">     
    var sock = null;
    var isEditable = false;    
    var message = {};
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
</body>
</html>