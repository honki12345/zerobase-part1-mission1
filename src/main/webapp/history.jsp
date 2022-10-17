<%@ page import="JDBC.MemberService" %>
<%@ page import="java.util.List" %>
<%@ page import="JDBC.History" %><%--
  Created by IntelliJ IDEA.
  User: iui47
  Date: 2022-10-16
  Time: 오후 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="cssHistory.css" rel="stylesheet" type="text/css">
    <title>와이파이 정보 구하기</title>
    <style>
        .table-history {
            width: 100%;
            border-top: 1px solid black;
            border-collapse: collapse;
        }
        .table-history th {
            background: aquamarine;
            height: 50px;
        }
        .table-history th, td{
            border: 1px solid black;
            text-align: center;
        }
        .id {

            font-weight: bold;
        }

    </style>
</head>
<body>
    <header class="page-header">
        <h1>위치 히스토리 목록</h1>
        <nav>
            <table class="table-nav" table rules ="cols" >
                <thead>
                <tr>
                    <th><a href="/">홈</a></th>
                    <th><a href="history.jsp">위치 히스토리 목록</a></th>
                    <th><a href="login.jsp">Open API 히스토리 정보 가져오기</a></th>
                </tr>
                </thead>
            </table>
        </nav>
    </header>
    <p></p>
    <table class="table-history">
        <thead>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        </thead>
        <tbody>
            <%
                MemberService memberService = new MemberService();
                List<History> historyList = memberService.listHistory();
                for (History history : historyList) {
            %>
            <tr>
                <td class="id"><%=history.getID()%></td>
                <td><%=history.getLNT()%></td>
                <td><%=history.getLAT()%></td>
                <td><%=history.getDate()%></td>
                <td align="center"><button onclick="location.href='historyDelete.jsp?historyId=<%=history.getID()%>'">삭제</button> </td>
            </tr>
        <%
            }
        %>

        <script>
        </script>



        </tbody>

</body>
</html>
