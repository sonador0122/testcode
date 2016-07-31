
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>

<head>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <title>상품 등록</title>
</head>
<body>

<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<div class="span3">
<h2 >상품등록</h2>
    <form action="<c:url value="/products/write.do" />" method="post" class="form-horizontal">
    <div class="page-header">
	  <h1>등록 정보</h1>
    </div>
    <div class="form-group">
    <label for="productName" class="col-sm-2 control-label">상품명:</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="name" name="name" maxlength="100" required="required"  autocomplete="off" required autofocus />
      </div>
    </div>
     <div class="form-group">
    <label for="productSize" class="col-sm-2 control-label">크기:</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="size" name="size" maxlength="20" required="required" autocomplete="off" required />
      </div>
    </div>
     <div class="form-group">
    <label for="productMaterial" class="col-sm-2 control-label">소재:</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="material" name="material" maxlength="50" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productComponent" class="col-sm-2 control-label">구성:</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="component" name="component" maxlength="20" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productOptions" class="col-sm-2 control-label">옵션:</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="options" name="options" maxlength="20" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productManufacturer" class="col-sm-2 control-label">제조/수입 :</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="manufacturer" name="manufacturer" maxlength="50" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productMadein" class="col-sm-2 control-label">제조국 :</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="madein" name="madein" maxlength="50" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productPrice" class="col-sm-2 control-label">가격 :</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="price" name="price" maxlength="20" required="required" autocomplete="off" required />
      </div>
    </div>
    <div class="form-group">
    <label for="productStock" class="col-sm-2 control-label">재고 :</label>
    <div class="col-sm-6">
        <input type="text" class="form-control" id="stock" name="stock" maxlength="20" required="required" autocomplete="off" required /><br>
      </div>
    </div>
      <div class="form-group">
    <label for="productDescription" class="col-sm-2 control-label">상품설명 :</label>
    <div class="col-sm-6">
        <textarea name="description" cols="80" rows="5"></textarea><br>
      </div>
    </div>
       
     <br/>
  
    <div class="form-group">
      <label for="inputName" class="col-sm-2 control-label"></label>
      <div class="col-sm-6">
        <input type="button" onclick="location.href='list.do?s=${param.s}'" value="취소"/>
        <input type="submit" value="전송">
    </div>
    </div>
    </form>

</div>
</div>
</body>
</html>