<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<header>

<nav class="navbar navbar-default">
	<div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="<%=request.getContextPath()%>/main/main.do" class="navbar-brand" style="font-size:34px;font-color:0xFFFFF;">보안SW쇼핑</a>
    </div>
	<div class="container-fluid pull-right">
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      	<ul class="nav navbar-nav">
      	<sec:authorize access="isAnonymous()">
      		<li><a href="<%=request.getContextPath()%>/member/memberAdd.do">회원가입</a></li>
			<li><a href="<%=request.getContextPath()%>/main/login.do">로그인</a></li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<li><a href="${logoutUrl}">로그아웃</a></li>
		</sec:authorize>
        	<li><a href="<%=request.getContextPath()%>/products/cart.do">카트</a></li>
        	<li><a href="<%=request.getContextPath()%>/member/memberEdit.do">마이페이지</a></li>
      	</ul>
      </div>
	</div>
</nav>
<style>
.navbar{
	margin-bottom: 0px;
}
</style>
</header>