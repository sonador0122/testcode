<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  	
<!-- CSS Files -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bpopup.css" type="text/css" charset="utf-8"/>
    
<!-- Javascript -->
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/board/boardComment.js"></script>

<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<html>
<head>
    <title>글 읽기</title>

    
    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";
        boardIdx = "${boardVO.idx}";
		
        var j = jQuery.noConflict(); 
        var isWriting = false;		
        var isReplying = false;		
        var isUpdating = false;		
        var cs1 = 'review';
        var cs2 = 'qna';

        var loading_comment =
                function() {
                    j("#read_comment").html("<table><tr><td><br/><img src='" + contextPath + "/resource/img/board/loader-comm.gif'/><br/><br /></td></tr></table>");
                };

    
        function showProducts() {
            if ( j("#products_list").length == 0 ) { return; }

            j("#products_list").load(
                    contextPath + "/products/listByBoard.do",
                    {
                        boardIdx:boardIdx
                    }
            )
        }
   	      
        j(document).ready(function(){
            
        	function confirmation(question) {
        	    var defer = $.Deferred();
        	    var div = j('<div></div>')
        	        div.html(question)
        	        div.dialog({
        	        	autoOpen: true,
        	          	modal: true,
        	          	title: 'Confirmation',
        	          	buttons: {
        	          	       "Yes": function () {
        	          	           defer.resolve("true");
        	          	         div.dialog("close");
        	          	       },
        	          	       "No": function () {
        	          	             defer.resolve("false");
        	          	           div.dialog("close");
        	          	       	}
        	          	 	},
        	          	   	close: function () {
        	          	   		div.remove();
        	          	   }
        	         });
        	   return defer.promise();
        	}
        	
            j("a#addGift").on("click", function() {
            	var question = "Do you want to add this in giftlist?";
        		confirmation(question).then(function (answer) {
        		    var ansbool = (String(answer) == "true");
        		    if(ansbool){
        		    	var data = j("a#addGift").attr("vals");
        				var arr = data.split('/');
        				$.ajax({
        					type:"POST",
        					url:"<%=request.getContextPath()%>/member/addGiftlist.do",
        					data:{ email : arr[0], check : ansbool, no : arr[1] },
        					success:function(result){
        						if(result === "400"){
        							alert("Already existed");
        						}else if(result === "200"){
        							alert("Added in giftlist");
        						}
        					}
        				});
        		    }
        		});			
        	});
			
            showComment(1, cs1);
            showComment(1, cs2);
            showProducts();
            
            j("#submit_form").on("submit",function(){
        		var quantity = $("#quantity").val().trim();
        		if(quantity === ""){
        			alert("Check your quantity again");
        			j("#quantity").focus();
        			return false;
        		}
        	
        	});
        	
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="page-header">
	  <h1>글읽기</h1>
</div>
<div class="main">
 <table class="table table-bordered">
    <tr>
        <td>제목</td>
        <td>${boardVO.title}</td>
    </tr>
    <tr>
        <td>작성자</td>
        <td>${boardVO.memberEmail}</td>
    </tr>
    <tr>
        <td>작성일</td>
        <td><fmt:formatDate value="${boardVO.postingDate}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>내용</td>
        <td>
            <c:if test="${param.s == 'product'}">
                <div id="products_list"></div>
            </c:if>
            ${boardVO.content}
        </td>
    </tr>

    <c:if test="${param.s == 'product'}">
        <tr>
            <td>전체 가격</td>
            <td>${boardVO.totalPrice}</td>
        </tr> 
	<tr>
		<td colspan="2">
		<br>
        <a href="#" class="btn btn-default" role="button" id="addGift" vals="${boardVO.memberEmail }/${boardVO.idx}">선물함</a><br><br>
    	<form action="<%=request.getContextPath()%>/products/addCart.do" method="get" class="form-inline" id="submit_form" name="submit_form">
    		<div class="form-group">
    			<label class="sr-only" for="quantity">Quantity</label>
    			<div class="input-group">
      				<div class="input-group-addon">*</div>
      					<input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity" autocomplete="off">
    			</div>
  			</div>
  			<input type="hidden" name="idx" value="${boardVO.idx}"/>
      		<input type="hidden" name="p" value="${param.p}"/>
      		<input type="hidden" name="s" value="${param.s}"/>
  			<button type="submit" class="btn btn-primary">카트에 담기</button>
    	</form>
    	</td>
	</tr>
	</c:if>
    
    <tr>
        <td colspan="2">
            <input type="button" onclick="location.href='list.do?s=${param.s}&p=${param.p}'" value="목록보기"/>
            <c:if test="${param.s != 'product'}">
                <input type="button" onclick="location.href='reply.do?s=${param.s}&p=${param.p}&parentBoardIdx=${boardVO.idx}'" value="답변하기"/>
            </c:if>
            <input type="button" onclick="location.href='update.do?s=${param.s}&p=${param.p}&boardIdx=${boardVO.idx}'" value="수정하기"/>
            <form action="<c:url value="/board/delete.do" />" method="post" style="display: inline;">
                <input type="hidden" name="p" value="${param.p}"/>
                <input type="hidden" name="s" value="${param.s}"/>
                <input type="hidden" name="boardIdx" value="${boardVO.idx}"/>
                <input type="submit" value="삭제하기" >
            </form>
        </td>
        
    </tr>

</table>

<!-- 댓글 구현부 -->
<div id="review"></div>
<div id="qna"></div>
</div>
</body>
</html>
