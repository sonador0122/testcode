<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">


<title>Giftlist</title>

<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").click(function(){
		if($("#q").val() == ''){
			alert("Enter Keyword");
			$("#q").focus();
			return false;
		}else{
			var act = 'giftlist.do?q='+$("#q").val();
			$("#search").attr('action',act).submit();
		}
	});
	
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
	
	$(".del_gift").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var arr = data.split('/');
				var link = 'delGiftlist.do?&email='+arr[0]+'&idx='+arr[1]+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".cart").click(function(){
		var question = "Do you want to do it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var arr = data.split('/');
				$.ajax({
					type:"POST",
					url:"<%=request.getContextPath()%>/products/addCartAjax.do",
					data:{ number : arr[1], choice : arr[0] },
					success:function(result){
						if(result === "400"){
							alert("Error");
						}else if(result === "200"){
							alert("Copied in Cart");
						}else if(result === "202"){
							alert("Moved in Cart");
						}else{
							alert("Already listed");
						}
					}
				});
		    }
		});		
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
	<c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
            </td>
        </tr>
    </c:if>
    
    <c:choose>
        <c:when test="${hasMember == false}">
            <tr>
                <td colspan="5">
                    선물이 없습니다.
                </td>
            </tr>
        </c:when>
        <c:otherwise>
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">상품</th>
				<th scope="col">가격</th>
				<th scope="col">장바구니</th>
				<th scope="col">카트 복사</th>
				<th scope="col">삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${giftlist}">
			 <tr>
                <th scope="row"><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></th>
				<td><a href="<%=request.getContextPath()%>/board/read.do?boardIdx=${list.boardIdx}">${list.title }</a></td>
				<td>${list.price }</td>
				<td><a href="#" class="cart" vals="go/${list.boardIdx}">Go</a></td>
				<td><a href="#" class="cart" vals="copy/${list.boardIdx}">Copy</a></td>			
				<td><a href="#" class="del_gift" vals="${list.memberEmail }/${list.boardIdx}">Delete</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  				 <c:if test="${pagingVO.beginPage > 10}">
                    	 <a href="<c:url value="giftlist.do?q=${keyword }&p=${pagingVO.beginPage-1}"/> ">이전</a>
                 </c:if>
                 <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                     	 <a href="<c:url value="giftlist.do?q=${keyword }&p=${pno}"/> ">[${pno}]</a>
             	 </c:forEach>
                 <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		 <a href="<c:url value="giftlist.do?q=${keyword }&p=${pagingVO.endPage + 1}"/> ">다음</a>
                 </c:if>
 				</td>
			</tr>
		</tfoot>
	</table>
		</c:otherwise>
	</c:choose>
	
	<form id="search" method="post">
		<input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
		<input type="button" value="검색" id="search_btn"/>
	</form>
	
	<a href="<%=request.getContextPath()%>/member/giftlist.do">Back</a>

</div>
</body>
</html>