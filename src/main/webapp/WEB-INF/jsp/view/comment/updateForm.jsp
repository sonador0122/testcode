
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="${cs}_${commentIdx}_ajaxform_update" id="${cs}_${commentIdx}_ajaxform_update" action="<c:url value="/comment/update.do" />" method="post">
    <input type="hidden" name="commentIdx" value="${commentVO.idx}"/>
    내용 : <br/>
    <textarea name="content" id="${cs}_${commentIdx}_content_update" cols="40" rows="5" >${commentVO.content}</textarea>
    <br/>
</form>

<button id="${cs}_${commentIdx}_ajaxform_cancle_update" onclick="showComment(1, '${cs}')" >취소</button>
<button id="${cs}_${commentIdx}_ajaxform_submit_update" onclick="ajaxForm_update('${cs}', '${commentIdx}')" >전송</button>

