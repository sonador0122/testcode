
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bpopup.css" type="text/css" charset="utf-8"/>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
    <script src="<%=request.getContextPath()%>/resource/js/jquery.bpopup.min.js"></script>

    <title>상품 목록</title>

    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";

        ;(function($) {
           $(function() {
                $('.popup').bind('click', function(e) {

                    var id = '.element_to_pop_up_' + $(this).attr('name');

                    e.preventDefault();
                    $(id).bPopup();
                });
            });
        })(jQuery);

    </script>
</head>
<body>
<table id="box-table-a" class="table table-hover">
    <tr>
        <th scope="col">상품 번호</th>
        <th scope="col">상품 명</th>
        <th scope="col">크기</th>
        <th scope="col">소재</th>
        <th scope="col">구성</th>
        <th scope="col">옵션</th>
        <th scope="col">제조/수입</th>
        <th scope="col">제조국</th>
        <th scope="col">가격</th>
        <th scope="col">재고</th>
        <th scope="col">등록자</th>
        <th scope="col">등록일</th>
    </tr>

    <c:choose>
        <c:when test="${hasProducts == false}">
            <tr>
                <td colspan="12">
                    상품이 없습니다.
                </td>
            </tr>
        </c:when>

        <c:otherwise>
            <c:forEach var="list" items="${productsVOList}">
                <tr id="products_${list.idx}">
                    <td>${list.idx}</td>
                    <td>
                        <a href="#" class="popup" name="${list.idx}">
                                ${list.name}
                        </a>
                        <div class='element_to_pop_up_${list.idx} b_pop_up' style='display:none;'>
                            <span class='button b-close'><span>X</span></span> 
                            <div class='content'>상품설명<br/>${list.description}</div>
                        </div>
                    </td>
                    <td>${list.size}</td>
                    <td>${list.material}</td>
                    <td>${list.component}</td>
                    <td>${list.options}</td>
                    <td>${list.manufacturer}</td>
                    <td>${list.madein}</td>
                    <td>${list.price}</td>
                    <td>${list.stock}</td>
                    <td>${list.memberEmail}</td>
                    <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>
