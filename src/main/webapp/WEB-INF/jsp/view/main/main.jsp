
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
    <title>secureswShopping</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">

<div class="jumbotron text-center">
  <h1>소프트웨어 개발 보안 가이드!</h1>
  <p>소프트웨어 개발 보안 또는 시큐어 코딩(Secure Coding)이란 안전한 소프트웨어 개발을 위해 소스 코드 등에 <br />
           존재할 수 있는 잠재적인 보안 취약점을 제거하고, 보안을 고려하여 기능을 설계 및 구현하는 등 소프트웨어<br />
            개발  과정에서  지켜야 할 일련의 보안 활동을 말한다. <br />
            인터넷 홈페이지나 소프트웨어 개발 시 보안 취약점을 악용한 해킹 등 내외부 공격으로부터 시스템을 안전하게 <br />
            방어할 수 있도록 코딩하는 것이 여기에 해당한다 -https://ko.wikipedia.org/wiki/</p>
  <p><a class="btn btn-primary btn-lg" href="<%=request.getContextPath()%>/board/list.do?s=product" role="button">Look around</a></p>
</div>
<div class="container">
    <div class="row">
		<div class="col-md-12">
                <div id="Carousel" class="carousel slide">
                 
                <ol class="carousel-indicators">
                    <li data-target="#Carousel" data-slide-to="0" class="active"></li>
                    <li data-target="#Carousel" data-slide-to="1"></li>
                    <li data-target="#Carousel" data-slide-to="2"></li>
                </ol>
                 
                <!-- Carousel items -->
                <div class="carousel-inner">
                    
                <div class="item active">
                	<div class="row">
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/27.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/27.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/27.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/27.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                <div class="item">
                	<div class="row">
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/03.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/03.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/03.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/03.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                <div class="item">
                	<div class="row">
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/42.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/42.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/42.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="<%=request.getContextPath()%>/resource/upload/42.png" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                </div><!--.carousel-inner-->
                  <a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
                  <a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
                </div><!--.Carousel-->
                 
		</div>
	</div>
</div><!--.container-->
</div>
</body>
<script>
$(document).ready(function() {
    $('#Carousel').carousel({
        interval: 5000
    })
});
</script>
<style>
body{padding-top:20px;}
.carousel {
    margin-bottom: 0;
    padding: 0 40px 30px 40px;
}
/* The controlsy */
.carousel-control {
	left: -12px;
    height: 40px;
	width: 40px;
    background: none repeat scroll 0 0 #222222;
    border: 4px solid #FFFFFF;
    border-radius: 23px 23px 23px 23px;
    margin-top: 90px;
}
.carousel-control.right {
	right: -12px;
}
/* The indicators */
.carousel-indicators {
	right: 50%;
	top: auto;
	bottom: -10px;
	margin-right: -19px;
}
/* The colour of the indicators */
.carousel-indicators li {
	background: #cecece;
}
.carousel-indicators .active {
background: #428bca;
}
</style>

</html>