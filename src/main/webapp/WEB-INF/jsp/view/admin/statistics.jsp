
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
    <title>통계정보</title>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <script type="text/javascript">

        contextPath = "${pageContext.request.contextPath}";

        $(document).ready(function(){

        });

        function showList(separator, page, search) {

             setTimeout(function() {

                var id = "#"+separator

                $(id).load(
                        contextPath + "/admin/"+separator+".do",
                        {
                            p:page,
                            q:search
                        }
                )
            }, 500);
        }

        showList("orderList", 1);
        showList("productsList", 1);
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>통계정보</h1>

    <div id="orderList"></div>
    <hr>
    <br>
    <div id="productsList"></div>

</div>

</body>
</html>
