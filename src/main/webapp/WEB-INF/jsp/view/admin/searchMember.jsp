
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>

<title>Search MEMBER</title>

<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").click(function(){
		if($("#q").val() == ''){
			alert("Enter Keyword");
			$("#q").focus();
			return false;
		}else{
			var act = 'search.do?q='+$("#q").val();
			$("#search").attr('action',act).submit();
		}
	});
	
});

function search_enter(form){
	var keycode = window.event.keyCode;
	if(keycode == 13) $("#search_btn").click();
}
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>회원 검색</h1>
<form id="search" method="post">
		<input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
		<input type="button" value="검색" id="search_btn"/>
</form>

<c:choose>
	<c:when test="${lists == NULL}">
            <tr>
                <td colspan="5">
                    일치하는 정보가 없습니다.
                </td>
            </tr>
   	</c:when>
   	<c:otherwise>
   		<c:if test="${pagingVO.totalPageCount > 0}">
     	   <tr>
    	        <td colspan="5">
    	                ${pagingVO.firstRow}-${pagingVO.endRow}
    	            [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
    	        </td>
  	      </tr>
   		</c:if>
   		<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">주문번호</th>
				<th scope="col">주문날짜</th>
				<th scope="col">주문상태</th>
				<th scope="col">User email</th>
				<th scope="col">User name</th>
				<th scope="col">상품 ID</th>
				<th scope="col">상품</th>
				<th scope="col">옵션</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lists}">
			 <tr>
				<th scope="row">${list.idx }</th>
				<td><fmt:formatDate value="${list.orderDate}" pattern="yyyy-MM-dd"/></td>
				<td>${list.orderNow }</td>
				<td>${list.memberEmail }</td>
				<td>${list.memberName }</td>
				<td>${list.productsIdx }</td>
				<td>${list.productsName }</td>
				<td>${list.productsOptions }</td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  					<c:if test="${pagingVO.beginPage > 5}">
                       	<a href="<c:url value="search.do?q=${keyword }&p=${pagingVO.beginPage-1}"/> ">이전</a>
                   	</c:if>
                 	<c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                       	<a href="<c:url value="search.do?q=${keyword }&p=${pno}"/> ">[${pno}]</a>
             		</c:forEach>
               		<c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		<a href="<c:url value="search.do?q=${keyword }&p=${pagingVO.endPage + 1}"/> ">다음</a>
                  	</c:if>
 				 </td>
			</tr>
		</tfoot>
	 </table>
   	</c:otherwise>
</c:choose>

</div>
</body>
</html>