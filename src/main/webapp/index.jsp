<%@ page contentType="text/html; charset=utf-8" %>
<%
String content = request.getParameter("to");
if(content==null) content="main/main.do";
%>
<jsp:include page="<%=content%>" flush="true" />