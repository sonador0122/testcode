<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta charset="UTF-8">

<title>Cart</title>

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
	
	$(".del_cart").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = 'delCart.do?&idx='+data+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".order").click(function(){
		var question = "Do you want to order it?";
		var data = [];
		var addr = "${member.address }";
		var post = "${member.postcode }";
		var name = "${member.lastName }";
		//alert(addr);
		data.push($(this).attr("vals"));
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/products/addOrders.do?&idx='+data+'&addr='+addr+'&post='+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".change_quan").click(function(){
		var question = "Do you want to change it?";
		var number = $(this).attr("vals");
		var quantity = $("#qty_"+number).val();
		var price = $("#price_"+number).attr("vals");
		if(quantity === 0){
			alert("No zero quantity");
			return;
		}
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
		    	$.ajax({
					type:"POST",
					url:"<%=request.getContextPath()%>/products/changeQuan.do",
					data:{quantity:quantity, number:number},
					success:function(result){
						if(result === "400"){
							alert("Error");
						}else{
							var total = 0;
							$("#qty_"+number).val(result);
							$("#sub_tot_"+number).text(price*result);
							$("table tbody").find("td.sub_tot").each(function(){
								var sub = $(this).text();
								total = total + parseInt(sub);
							});
							$("#total").text(total);
						}
					}
				});
		    }
		});		
	});
	
	$("#total_order").click(function(){
		var question = "Do you want to order it?";
		var data = [];
		var addr = "${member.address }";
		var post = "${member.postcode }";
		var name = "${member.lastName }";
		
		$('.order').each(function(){
			data.push($(this).attr("vals"));
		});
		//alert(data);
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/products/addOrders.do?&idx='+data+'&addr='+addr+'&post='+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});

	
	$(".number").keypress(function (e) {
	 
	     var number = $(this).attr("vals");
	     
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        $("#errmsg"+number).html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});

});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>카트</h1>

<c:choose>
	<c:when test="${hasMember == false}">
            <tr>
                <td colspan="5">
                    쇼핑 카트가 비었습니다.
                </td>
            </tr>
    </c:when>
   	<c:otherwise>
   		<table id="box-table-a" class="table table-hover" id="table">
		<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">상품</th>
				<th scope="col">가격</th>
				<th scope="col">수량</th>
				<th scope="col">합계</th>
				<th scope="col">구매</th>
				<th scope="col">취소</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${cartlist}">
				<c:set var="s" value="${s + list.quantity * list.price}"></c:set>
			 <tr>
			 	<th scope="row"><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></th>
				<td><a href="<%=request.getContextPath()%>/board/read.do?s=product&boardIdx=${list.boardIdx}">${list.title}</a></td>
				<td id="price_${list.idx}" class="prc" vals="${list.price }">${list.price }</td>
				<td><input type="text" value="${list.quantity }" id="qty_${list.idx}" class="number" vals="${list.idx}"/>
					<input type="button" class="change_quan" vals="${list.idx}" value="Change" >&nbsp;<span id="errmsg${list.idx}" class="error"></span></td>
				<td id="sub_tot_${list.idx}" class="sub_tot">${list.quantity * list.price}</td>
				<td><a href="#" class="order" id="order" vals="${list.boardIdx}">구매</a></td>
				<td><a href="#" class="del_cart" vals="${list.idx}">취소</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tr>
			<td colspan="3" align="right">Total Price : </td>
			<td id="total">${s }</td>
			<td><a href="#" id="total_order" vals="${list.boardIdx}">모두 구매</a></td>
		</tr>
	 </table>
   	</c:otherwise>
</c:choose>

</div>
</body>
</html>