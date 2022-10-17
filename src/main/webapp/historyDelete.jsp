<%@ page import="JDBC.MemberService" %><%--
  Created by IntelliJ IDEA.
  User: iui47
  Date: 2022-10-17
  Time: 오후 3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberService memberService = new MemberService();
    String id = request.getParameter("historyId");
    memberService.delete(id);
%>
<script>
    location.href = 'history.jsp';
</script>
