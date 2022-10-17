<%@ page import="JDBC.ApiExplorer" %>
<%@ page import="JDBC.MemberService" %>
<%@ page import="JDBC.PoolTest" %>
<%@ page import="javax.swing.plaf.nimbus.State" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.HashMap" %>

<%--
  Created by IntelliJ IDEA.
  User: iui47
  Date: 2022-10-15
  Time: 오후 8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        h1 {text-align: center}
        .link {text-align: center}
    </style>
</head>
<body>

<%
    ApiExplorer apiExplorer = new ApiExplorer();
    MemberService memberService = new MemberService();
    memberService.registerAll(apiExplorer);
%>

    <h1><%=memberService.countNum()%>개의 WIFI정보를 정상적으로 저장하였습니다.</h1>
    <div class="link">
        <a href="index.jsp">홈으로 가기</a>
    </div>
</body>
</html>
