<%@ page import="JDBC.Member" %>
<%@ page import="java.util.List" %>
<%@ page import="JDBC.MemberService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="style.css" rel="stylesheet" type="text/css">
    <title>와이파이 정보 구하기</title>
    <style>
        .table-wifi {
            width: 100%;
            border-top: 1px solid black;
            border-collapse: collapse;
        }
        .table-wifi th {
            background: aquamarine;
            height: 50px;
        }
        .table-wifi th, td{
            border-bottom: 1px solid black;

        }
        .tbody2 {
            text-align: center;
            font-size: 15px;
            height: 100px;
            font-weight: bold;
        }
    </style>


</head>

<body>
    <header class="page-header">
        <h1>와이파이 정보 구하기</h1>
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

    <form >
        LAT: <input type="text" name="LAT" id="LAT" >
        LNT: <input type="text" name="LNT" id="LNT">
        <input type="button" value="내 위치 가져오기/구현못함">
        <input type="submit" value="근처 WIFI 정보 가져오기">
    </form>

    <p></p>
    <table class="table-wifi" >
        <thead>
            <tr>
                <th>거리(km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>wifi접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>

        <tbody>

            <%
                String inputLat = request.getParameter("LAT");
                String inputLnt = request.getParameter("LNT");


                if ((inputLat != null && inputLat != "") &&
                    inputLnt != null && inputLnt != "") {
                    MemberService memberService = new MemberService();
                    memberService.registerHistory(inputLat, inputLnt);

                    memberService.getDistance(Double.parseDouble(inputLat), Double.parseDouble(inputLnt));
                    List<Member> memberList = memberService.list();
                    for (Member member : memberList) {
            %>
                 <tr>
                    <td><%=member.getDistance()%></td>
                    <td><%=member.getX_SWIFI_MGR_NO()%></td>
                    <td><%=member.getX_SWIFI_WRDOFC()%></td>
                    <td><%=member.getX_SWIFI_MAIN_NM()%></td>

                     <td><%=member.getX_SWIFI_ADRES1()%></td>
                     <td><%=member.getX_SWIFI_ADRES2()%></td>
                     <td><%=member.getX_SWIFI_INSTL_FLOOR()%></td>

                     <td><%=member.getX_SWIFI_INSTL_TY()%></td>
                     <td><%=member.getX_SWIFI_INSTL_MBY()%></td>
                     <td><%=member.getX_SWIFI_SVC_SE()%></td>

                    <td><%=member.getX_SWIFI_CMCWR()%></td>
                    <td><%=member.getX_SWIFI_CNSTC_YEAR()%></td>
                    <td><%=member.getX_SWIFI_INOUT_DOOR()%></td>

                     <td><%=member.getX_SWIFI_REMARS3()%></td>
                     <td><%=member.getLAT()%></td>
                     <td><%=member.getLNT()%></td>
                     <td><%=member.getWORK_DTTM()%></td>
                 </tr>

            <%
                    }
            %>

        </tbody>

        <%    } else { %>
        <tbody class="tbody2">
            <tr>
                <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
            </tr>
        </tbody>
        <%} %>
    </table>


<br/>
</body>
</html>

