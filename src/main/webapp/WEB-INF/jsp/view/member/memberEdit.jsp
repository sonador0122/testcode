
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">

<title>Edit</title>

<script type="text/javascript">

$(function(){
	$("#input_pwd").on("click",function(){		
		
		var pass1 = $("#password").val();
		var pass2 = $("#password_chk").val();
		
		if(pass1 === "" || pass2 === ""){
			$("#password").focus();
			alert("Input password");
			return false;
		}
		
		if(pass1 !== pass2){
			$("#password").focus();
			$("#password").val('');
			$("#password_chk").val('');
			alert("Two password are different");
			return false;
		}
		
		var pass = $("#password").serialize();
		
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/member/changePwd.do",
			data:pass,
			success:function(result){
				if(result === "404"){
					alert("Failed! Try again");
					$("#password").focus();
					$("#password").val('');
					$("#password_chk").val('');
				}else{
					$("#txt").text(result);
					$("#password").val('');
					$("#password_chk").val('');
					$('.collapse').collapse('hide'); 
				}
			}
		});
	
	});
	
	$("#edit_form").on("submit",function(){
		var postcode = $("#postcode").val();
		var trimcode = postcode.trim();
		
		if(trimcode.length !== 6){
			alert("Check your postcode again");
			$("#postcode").focus();
			return false;
		}
		
	});
	
	$("#postcode").keypress(function (e) {
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        $("#errmsg,#errmsg2").html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});
	
	$("#password_btn").on("click",function(){
		$("#txt").text("");
	});
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<div class="span3">

<h2>정보수정</h2>
	
	<form action="" method="post" id="edit_form" name="edit_form" enctype="multipart/form-data" class="form-horizontal">
		<div class="form-group">
		 <label for="firstName" class="col-sm-2 control-label">이름</label>
		 <div class="col-sm-6">
		  <input type="text" class="form-control" name="firstName" maxlength="50" required="required"  autocomplete="off" value="${membervo.firstName}">
		</div>
		</div>
		
		<div class="form-group">
		<label for="lastName" class="col-sm-2 control-label">성</label>
		 <div class="col-sm-6">
		<input type="text" class="form-control"  name="lastName" maxlength="50" required="required"  autocomplete="off" value="${membervo.lastName}">	
		</div>
		</div>
		<div class="form-group">
        <label for="changePassword" class="col-sm-2 control-label"></label>
        <div class="col-sm-6">
			<button type="button" class="btn btn-primary btn-xs" id="password_btn" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">암호변경</button><p id="txt"></p>
		  <div class="collapse" id="collapseExample">
  			<div class="well">
    				<fieldset>
    					<legend>암호변경</legend>
    						<input type="password" class="form-control" id="password" name="password" maxlength="20" required="required" placeholder="Password" autocomplete="off" >
    						<input type="password" class="form-control" id="password_chk" name="password2" maxlength="20" required="required" placeholder="Password again" autocomplete="off" >
    						<input type="button" class="btn btn-primary btn-sm" id="input_pwd" name="input_pwd" value="변경" >
    				</fieldset>
  			</div>
		</div>
		<br>
		</div>
		</div>
		<div class="form-group">
		 <label for="address" class="col-sm-2 control-label">주소</label>
		  <div class="col-sm-6">
		 <input type="text" class="form-control" name="address" maxlength="250" required="required" autocomplete="off" value="${membervo.address}"><br>
		</div>
		</div>
		
		<div class="form-group">
		<label for="postcode" class="col-sm-2 control-label">우편번호</label>
		 <div class="col-sm-6">
		<input type="text" class="form-control" id="postcode" name="postcode" maxlength="6" required="required" autocomplete="off" value="${membervo.postcode}">&nbsp;<span id="errmsg"></span><br>
		</div>
		</div>
		
		<div class="form-group">
      <label for="thumnail" class="col-sm-2 control-label">사진:</label>
       <div class="col-sm-6">
        <input type="file" class="form-control"  id="thumnail" name="thumnail" >
	   </div>
    </div>
	
	<div class="form-group">
    <label for="inputName" class="col-sm-2 control-label"></label>
    <div class="col-sm-6">
		<button type="submit" class="btn btn-primary" value="submit">수정완료</button>	 
		</div>
    </div>
    
	</form>
	<br>
	<a href="<%=request.getContextPath()%>/member/memberDelete.do">계정 삭제</a><br>
	
	</div>
</div>
</body>
</html>