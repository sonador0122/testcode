<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">


<title>Your Orders</title>

<script type="text/javascript">
$(document).ready(function(){
	function confirmation(question) {
	    var defer = $.Deferred();
	    $('<div></div>')
	        .html(question)
	        .dialog({
	            autoOpen: true,
	            modal: true,
	            title: 'Confirmation',
	            buttons: {
	                "Yes": function () {
	                    defer.resolve("true");
	                    $(this).dialog("close");
	                },
	                "No": function () {
	                    defer.resolve("false");
	                    $(this).dialog("close");
	                }
	            },
	            close: function () {
	                $(this).remove();
	            }
	        });
	    return defer.promise();
	}
	
	$(".del_order").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var arr = data.split('/');
				var link = 'delOrderlist.do?&idx='+data+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});	
	});
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">

<h1>주문내역</h1>

<c:choose>
	<c:when test="${lists == NULL}">
            <tr>
                <td colspan="5">
                    주문 내역이 없습니다.
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
				<th scope="col">제품</th>
				<th scope="col">가격</th>
				<th scope="col">수량</th>
				<th scope="col">주문상태</th>
				<th scope="col">주문자</th>
				<th scope="col">수령인</th>
				<th scope="col">리스트에서 삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${lists}">
				<c:set var="s" value="${s + list.quantity * list.totalPrice}"></c:set>
			 <tr>
				<th scope="row">${list.idx }</th>
				<td><fmt:formatDate value="${list.orderDate}" pattern="yyyy-MM-dd"/></td>
				<td><a href="<%=request.getContextPath()%>/board/read.do?s=product&boardIdx=${list.boardIdx}">${list.title }</a></td>
				<td>${list.totalPrice }</td>
				<td>${list.quantity }</td>
				<td>${list.orderNow }</td>
				<td>${list.memberName}</td>
				<td>${list.receiver}</td>
				<td><a href="#" class="del_order" vals="${list.idx}">Delete</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  					<c:if test="${pagingVO.beginPage > 10}">
                       	<a href="<c:url value="orders.do?p=${pagingVO.beginPage-1}"/> ">이전</a>
                   	</c:if>
                 	<c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                       	<a href="<c:url value="orders.do?p=${pno}"/> ">[${pno}]</a>
             		</c:forEach>
               		<c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		<a href="<c:url value="orders.do?p=${pagingVO.endPage + 1}"/> ">다음</a>
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