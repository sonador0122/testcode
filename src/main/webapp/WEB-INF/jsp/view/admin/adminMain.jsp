<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">
<title>관리자</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<ul>
	<li><a href="<%=request.getContextPath()%>/member/memberAdd.do">계정 생성</a></li>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<li><a href="${logoutUrl}">로그 아웃</a></li>
	<li><a href="<%=request.getContextPath()%>/board/list.do">게시판 리스트</a></li>
    <li><a href="<%=request.getContextPath()%>/board/list.do?s=notice">공지사항</a></li>
    <li><a href="<%=request.getContextPath()%>/board/list.do?s=qna">QnA 게시판</a></li>
</ul>
</div>
</body>
</html>