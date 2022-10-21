package JDBC;


import javax.xml.stream.Location;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    static String tableName = "pubwifi2";
    static String tableName2 = "history";
    PoolTest poolTest = PoolTest.getInstance();
    private PreparedStatement psmt;
    private Statement stmt; // 인파라미터가 없는 정적 쿼리문 실행
    private ResultSet rs; // SELECT 쿼리문 결과 저장
    private ResultSet rs2;

    // 데이터 조회 - print

    public List<Member> list() {
        List<Member> memberList = new ArrayList<>(); // 출력용
        Connection con = poolTest.getConnection();
//distance  , X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1
        String sql = " select distance, " +
                " X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, " +
                " X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY,X_SWIFI_SVC_SE, " +
                " X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                " X_SWIFI_REMARS3, LAT, LNT, " +
                        " WORK_DTTM " +
                " from " + tableName
                + " ORDER BY distance "
                + " limit 1, 20;";

        try {
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                String test0 = rs.getString("distance");
                String test1 = rs.getString("X_SWIFI_MGR_NO");
                String test2 = rs.getString("X_SWIFI_WRDOFC");
                String test3 = rs.getString("X_SWIFI_MAIN_NM");

                String test4 = rs.getString("X_SWIFI_ADRES1");
                String test5 = rs.getString("X_SWIFI_ADRES2");
                String test6 = rs.getString("X_SWIFI_INSTL_FLOOR");

                String test7 = rs.getString("X_SWIFI_INSTL_TY");
                String test8 = rs.getString("X_SWIFI_INSTL_MBY");
                String test9 = rs.getString("X_SWIFI_SVC_SE");

                String test10 = rs.getString("X_SWIFI_CMCWR");
                String test11 = rs.getString("X_SWIFI_CNSTC_YEAR");
                String test12 = rs.getString("X_SWIFI_INOUT_DOOR");

                String test13 = rs.getString("X_SWIFI_REMARS3");
                String test14 = rs.getString("LAT");
                String test15 = rs.getString("LNT");
                String test16 = rs.getString("WORK_DTTM");

                Member member = new Member();
                member.setDistance(test0);
                member.setX_SWIFI_MGR_NO(test1);
                member.setX_SWIFI_WRDOFC(test2);
                member.setX_SWIFI_MAIN_NM(test3);

                member.setX_SWIFI_ADRES1(test4);
                member.setX_SWIFI_ADRES2(test5);
                member.setX_SWIFI_INSTL_FLOOR(test6);

                member.setX_SWIFI_INSTL_TY(test7);
                member.setX_SWIFI_INSTL_MBY(test8);
                member.setX_SWIFI_SVC_SE(test9);

                member.setX_SWIFI_CMCWR(test10);
                member.setX_SWIFI_CNSTC_YEAR(test11);
                member.setX_SWIFI_INOUT_DOOR(test12);

                member.setX_SWIFI_REMARS3(test13);
                member.setLAT(test14);
                member.setLNT(test15);
                member.setWORK_DTTM(test16);
                memberList.add(member);
//                System.out.println(test1 + ", " + test2 + ", "
//                        + test3 + ", " + test4 + ", " + test5 + ", " + test6
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
        return memberList;
    }


    public List<History> listHistory() {
        List<History> historyList = new ArrayList<>(); // 출력용
        Connection con = poolTest.getConnection();
        String sql = " select ID," +
                " X좌표, Y좌표, 조회일자 " +
                " from " + "history ";

        try {
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                int test0 = rs.getInt("ID");
                String test1 = rs.getString("X좌표");
                String test2 = rs.getString("Y좌표");
                Timestamp test3 = rs.getTimestamp("조회일자");

                History history = new History();
                history.setID(test0);
                history.setLNT(test1);
                history.setLAT(test2);
                history.setDate(test3);
                historyList.add(history);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
        return historyList;
    }

    // 데이터입력
    public void register(String X_SWIFI_MGR_NO, String X_SWIFI_WRDOFC, String X_SWIFI_MAIN_NM,
                         String X_SWIFI_ADRES1, String X_SWIFI_ADRES2, String X_SWIFI_INSTL_FLOOR,
                         String X_SWIFI_INSTL_TY, String X_SWIFI_INSTL_MBY, String X_SWIFI_SVC_SE,
                         String X_SWIFI_CMCWR, String X_SWIFI_CNSTC_YEAR,  String X_SWIFI_INOUT_DOOR,
                         String X_SWIFI_REMARS3, String LAT, String LNT,
                         String WORK_DTTM) {
        Connection con = poolTest.getConnection();

        String sql = " INSERT INTO " + tableName + " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, " +
                " X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY,X_SWIFI_SVC_SE, " +
                " X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                " X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
                + " VALUES ( ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?," +
                " ?)";

        try {
            psmt = con.prepareStatement(sql);
//            psmt.setInt(1, test0);
            psmt.setString(1, X_SWIFI_MGR_NO);
            psmt.setString(2, X_SWIFI_WRDOFC);
            psmt.setString(3, X_SWIFI_MAIN_NM);

            psmt.setString(4, X_SWIFI_ADRES1);
            psmt.setString(5, X_SWIFI_ADRES2); // x좌표
            psmt.setString(6, X_SWIFI_INSTL_FLOOR); // y좌표

            psmt.setString(7, X_SWIFI_INSTL_TY);
            psmt.setString(8, X_SWIFI_INSTL_MBY);
            psmt.setString(9, X_SWIFI_SVC_SE);

            psmt.setString(10, X_SWIFI_CMCWR);
            psmt.setString(11, X_SWIFI_CNSTC_YEAR); // x좌표
            psmt.setString(12, X_SWIFI_INOUT_DOOR); // y좌표

            psmt.setString(13, X_SWIFI_REMARS3);
            psmt.setString(14, LAT); // x좌표
            psmt.setString(15, LNT); // y좌표
            psmt.setString(16, WORK_DTTM); // y좌표
            int result = psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }

    public void registerHistory(String inputLat, String inputLnt) {
        Connection con = poolTest.getConnection();
        String tableName = "history";

        String sql = " INSERT INTO " + " history " +
                "(X좌표, Y좌표, 조회일자)" +
                " VALUES (?, ?, now())";

        try {
            psmt = con.prepareStatement(sql);
//            psmt.setInt(1, test0);
            psmt.setString(1, inputLnt);
            psmt.setString(2, inputLat);

            int result = psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }

    // 모든 데이터 입력
    public void registerAll(ApiExplorer apiExplorer) {
        this.truncate(); // 리셋
        int start = 1;
        int end = 999;

        // 테스트
        while (true) {

            String response = apiExplorer.httpGet(start, end);
            if (!response.contains("MESSAGE\":\"해당하는 데이터가 없습니다.")) {
                List<Member> items = apiExplorer.parseJson(response);

                for (Member mem : items) {
                    this.register(mem.X_SWIFI_MGR_NO, mem.X_SWIFI_WRDOFC, mem.X_SWIFI_MAIN_NM,
                            mem.X_SWIFI_ADRES1, mem.X_SWIFI_ADRES2, mem.X_SWIFI_INSTL_FLOOR,
                            mem.X_SWIFI_INSTL_TY, mem.X_SWIFI_INSTL_MBY, mem.X_SWIFI_SVC_SE,
                            mem.X_SWIFI_CMCWR, mem.X_SWIFI_CNSTC_YEAR, mem.X_SWIFI_INOUT_DOOR,
                            mem.X_SWIFI_REMARS3, mem.LAT, mem.LNT,
                            mem.WORK_DTTM);
                }
//                this.list();

            } else {
                break;
            }
            start += 999;
            end += 999;
        }
    }

    public String countNum() {
        Connection con = poolTest.getConnection();

        String result = "";
//        int res2 = 0;
        String sql = " select count(*) " +
                " from " + tableName;

        try {
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();
            rs.next();
            result = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void delete(String id) {
        Connection con = poolTest.getConnection();
        try {
            stmt = con.createStatement();
            String truncate = "DELETE FROM " + "history"
                    + " WHERE ID = " + id;
            stmt.execute(truncate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }

    //  TRUNCATE
    public void truncate() {
        Connection con = poolTest.getConnection();
        try {
            stmt = con.createStatement();
            String truncate = "TRUNCATE " + tableName;
            stmt.execute(truncate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }

    public void getDistance(double inputLAT, double inputLNT) { // x좌표 - LNT/  y좌표 -LAT
        Connection con = poolTest.getConnection();
        try {
            stmt = con.createStatement();
            String getDistance = "UPDATE pubwifi2 " +
                    " SET DISTANCE = " + " ROUND(" +
                    "    (6371 * acos(cos(radians(LAT))" +
                    "    * cos(radians(" + inputLAT + "))" +
                    "    * cos(radians(" + inputLNT + ") - radians(LNT)) " +
                    "    + sin(radians(LAT)) * sin(radians(" + inputLAT + ")))), 4)" +

                    " ;";

            stmt.execute(getDistance);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }

    public void test() {
        System.out.println("test");
    }

}

