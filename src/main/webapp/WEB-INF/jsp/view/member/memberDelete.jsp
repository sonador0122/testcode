
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">

<title>계정 삭제</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<div class="span3">
<h2>계정 삭제 이유는?</h2>

<form action="" method="post" >
	<div class="radio">
 	 <label >
   		 <input type="radio" name="check" id="optionsRadios1" value="noUse" checked>
    		나는 더 이상 계정을 사용하지 않음.
  	 </label>
	</div>
	<div class="radio">
	 <label>
    	<input type="radio" name="check" id="optionsRadios2" value="shit">
    		이 사이트를 좋아하지 않음.
  	 </label>
	</div>
	<button type="submit" class="btn btn-primary" value="submit">계정삭제</button>
</form>
</div>
</div>
</body>
</html>