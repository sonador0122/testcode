<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table style="border: 2px solid; margin: 30px 10px; width: 50%;">
    <tr>
        <td>
            <form name="${cs}_${parentCommentIdx}_ajaxform_reply" id="${cs}_${parentCommentIdx}_ajaxform_reply" action="<c:url value="/comment/reply.do" />" method="post">
                <input type="hidden" name="boardIdx" value="${boardIdx}">
                <input type="hidden" name="parentCommentIdx" value="${parentCommentIdx}"/>
                <input type="hidden" name="cs" value="${cs}"/>
                내용: <br/>
                <textarea name="content" id="${cs}_${parentCommentIdx}_content_reply" cols="40" rows="5"></textarea>
                <br/>
            </form>
            <button id="${cs}_${parentCommentIdx}_ajaxform_cancle_reply" onclick="toggleReply('${cs}', '${parentCommentIdx}', '${boardIdx}')" >취소</button>
            <button id="${cs}_${parentCommentIdx}_ajaxform_submit_reply" onclick="ajaxForm_reply('${cs}', '${parentCommentIdx}')" >전송</button>
        </td>
    </tr>
</table>

