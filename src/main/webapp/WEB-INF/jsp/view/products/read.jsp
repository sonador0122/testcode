
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>

<head>
<!-- CSS Files -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.css">

<!-- Javascript -->
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/bootstrap.min.js"></script>

    <title>상품 읽기</title>

</head>
<body>

<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">

    <table>
        <tr>
            <td>상품 번호</td>
            <td>${productsVO.idx}</td>
        </tr>
        <tr>
            <td>상품 명</td>
            <td>${productsVO.name}</td>
        </tr>
        <tr>
            <td>크기</td>
            <td>${productsVO.size}</td>
        </tr>
        <tr>
            <td>소재</td>
            <td>${productsVO.material}</td>
        </tr>
        <tr>
            <td>구성</td>
            <td>${productsVO.component}</td>
        </tr>
        <tr>
            <td>옵션</td>
            <td>${productsVO.options}</td>
        </tr>
        <tr>
            <td>제조/수입</td>
            <td>${productsVO.manufacturer}</td>
        </tr>
        <tr>
            <td>제조국</td>
            <td>${productsVO.madein}</td>
        </tr>

            <td>가격</td>
            <td>${productsVO.price}</td>
        </tr>
        <tr>
            <td>재고</td>
            <td>${productsVO.stock}</td>
        </tr>
        <tr>
            <td>등록자</td>
            <td>${productsVO.memberEmail}</td>
        </tr>
        <tr>
            <td>등록일</td>
            <td><fmt:formatDate value="${productsVO.createdDate}" pattern="yyyy-MM-dd"/></td>
        </tr>

        <tr>
            <td>상품 설명</td>
            <td>
                <pre><c:out value="${productsVO.description}"/></pre>
            </td>
        </tr>
        <tr>

            <td colspan="2">
                <input type="button" onclick="location.href='list.do?p=${param.p}'" value="목록보기"/>
                <input type="button" onclick="location.href='update.do?p=${param.p}&productsIdx=${productsVO.idx}'" value="수정하기"/>
                <form action="<c:url value="/products/delete.do" />" method="post" style="display: inline;">
                    <input type="hidden" name="p" value="${param.p}"/>
                    <input type="hidden" name="productsIdx" value="${productsVO.idx}"/>
                    <input type="submit" value="삭제하기" >
                </form>
            </td>
        </tr>
    </table>
    <a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>

</div>
</body>
</html>