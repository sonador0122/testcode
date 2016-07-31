<%@ page contentType="text/html; charset=utf-8" %>
<%
String content = request.getParameter("to");
if(content != null) {
response.sendRedirect(content);
} else {
response.sendRedirect("main/main.do");
}
%>
