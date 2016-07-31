
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">

<title>add</title>

<script type="text/javascript">

function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
} 

$(function(){
	var check = 0;

	$("#check_email").on("click",function(){
		var email = $("#email").val();

		if(email === ""){
			$("#email").focus();
			alert("Input Email");
			return false;
		}
		
		if(!validateEmail(email)){
			$("#email").focus();
			$("#email").val('');
			alert("Please write down available form of email");
			return false;
		}
		
		var email2 = $("#email").serialize();
		
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/member/checkEmail.do",
			data:email2,
			success:function(result){
				if(result === "404"){
					alert("Failed! Try again");
					$("#email").focus();
					$("#email").val('');
				}else if(result === "400"){
					alert("This email is used");
					$("#email").focus();
					$("#email").val('');
				}else{
					check = 1;
					$("#txt").text(result);
				}
			}
		});
	});
	
	$("#submit_form").on("submit",function(){
		var postcode = $("#postcode").val();
		var trimcode = postcode.trim();
		
		if(trimcode.length !== 6){
			alert("Check your postcode again");
			$("#postcode").focus();
			return false;
		}
		
		if(check !== 1){
			alert("Check your email again");
			$("#email").focus();
			return false;
		}
	});
	
	$("#postcode").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg,#errmsg2").html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<div class="span3">
<h2 >회원가입</h2>
	<form action="" method="post" enctype="multipart/form-data" id="submit_form" name="submit_form" class="form-horizontal">
	<div class="page-header">
	  <h1>가입 정보</h1>
    </div>
    
	<div class="form-group">
    <label for="firstName" class="col-sm-2 control-label">이름:</label>
     <div class="col-sm-6">
        <input type="text" class="form-control" id="firstName" name="firstName" maxlength="50" required="required" placeholder="First name" autocomplete="off" required autofocus /><br>
      </div>
    </div>
   
   <div class="form-group">
    <label for="lastName" class="col-sm-2 control-label">성:</label>
     <div class="col-sm-6">
         <input type="text" class="form-control"  id="lastName" name="lastName" maxlength="50" required="required" placeholder="Last name" autocomplete="off" required /><br>
		</div>
    </div>
    
    <div class="form-group">
    <label for="email" class="col-sm-2 control-label">메일:</label>
     <div class="col-sm-6">
           <input type="text" class="form-control"  id="email" name="email" maxlength="250" required="required" placeholder="Email " autocomplete="off" />
		   <input type="button" class="btn btn-primary btn-sm" id="check_email" name="check_email" value="중복체크와 인증키보내기" /><p id="txt"></p>
	  </div>
    </div>
    
    <div class="form-group">
     <label for="password" class="col-sm-2 control-label">암호:</label>
       <div class="col-sm-6">
         <input type="password" class="form-control" id="password" name="password" maxlength="20" required="required" placeholder="Password" autocomplete="off"><br>
	   </div>
    </div>
    
    <div class="form-group">
     <label for="address" class="col-sm-2 control-label">주소:</label>
      <div class="col-sm-6">
       <input type="text" class="form-control"  id="address" name="address" maxlength="250" required="required" placeholder="Address" autocomplete="off"><br>
	   </div>
    </div>
    
     <div class="form-group">
      <label for="postcode" class="col-sm-2 control-label">우편번호:</label>
       <div class="col-sm-6">
        <input type="text" class="form-control"  id="postcode" name="postcode" maxlength="6" required="required" placeholder="Postcode" autocomplete="off">&nbsp;<span id="errmsg"></span><br><br>
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
      <button type="submit" class="btn btn-primary">회원가입</button>
    </div>
    </div>
	   	
	</form>
	
</div>
</div>
</body>
</html>