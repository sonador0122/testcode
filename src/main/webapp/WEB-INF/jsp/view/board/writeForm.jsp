<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/daumeditor/css/editor.css" type="text/css" charset="utf-8"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bpopup.css" type="text/css" charset="utf-8"/>
<script src="<%=request.getContextPath()%>/resource/daumeditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/jquery.bpopup.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/board/editor.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/board/popup.js"></script>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>

    <title>글쓰기</title>
    
    <style type="text/css">
    textarea {
        resize: none;
    }
    </style>

    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<!-- start editor -->
<div>
<div class="page-header">
	  <h1>글쓰기</h1>
</div>
<c:if test="${act == 'write'}">
    <form id="frm" name="frm" action="<c:url value="/board/write.do" />" enctype="multipart/form-data" method="post" accept-charset="utf-8">
</c:if>
<c:if test="${act == 'reply'}">
    <form id="frm" name="frm" action="<c:url value="/board/reply.do" />" method="post" accept-charset="utf-8">
    <input type="hidden" name="parentBoardIdx" value="${param.parentBoardIdx}" />
</c:if>

    <input type="hidden" name="s" value="${param.s}"/>
    
    제목 : <input type="text" name="title" size="100"/><br/>
	
    <c:if test="${param.s == 'product'}">
        <table id="selected_products" border="1">
            <tbody>
                <tr>
                    <td>상품 번호</td>
                    <td>상품 명</td>
                    <td>크기</td>
                    <td>소재</td>
                    <td>구성</td>
                    <td>옵션</td>
                    <td>제조/수입</td>
                    <td>제조국</td>
                    <td>가격</td>
                    <td>재고</td>
                    <td>등록자</td>
                    <td>등록일</td>
                    <td>삭제</td>
                </tr>

                <tr>
                    <td id="empty_products" colspan="13">
                        상품이 없습니다.
                    </td>
                </tr>
            </tbody>
        </table>

        <button id="popup">상품 추가</button><br/>

        전체 가격 : <input type="text" name="total_price" id="total_price" size="30" value="0"/><br/>

        <div id='element_to_pop_up' style='display:none;'>
            <span class='button b-close'><span>X</span></span> 
            <div class='content'></div>
        </div>
    </c:if>

    <!-- call editor frame -->
    <div id="editor_frame"></div>
    <textarea name="daumeditor" id="daumeditor" rows="10" cols="100" style="width:766px; height:412px;display: none;"></textarea>
    <c:if test="${param.s == 'product'}">
    Thumbnail
    <input type="file" name="thumnail" id="thumnail"/><br>
    </c:if>
    <input type="hidden" name="hid" value="${param.s}" id="hid"/>
    <input type="button" onclick="location.href='list.do?s=${param.s}'" value="취소"/>
    <input type="button" id="save_button" value="등록"/>
</form>
</div>
<!-- end editor -->
</div>
</body>