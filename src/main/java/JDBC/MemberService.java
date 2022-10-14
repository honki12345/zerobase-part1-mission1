package JDBC;


import javax.xml.stream.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    static String tableName = "pubwifi2";
    PoolTest poolTest = PoolTest.getInstance();
    private PreparedStatement psmt;
    private Statement stmt; // 인파라미터가 없는 정적 쿼리문 실행
    private ResultSet rs; // SELECT 쿼리문 결과 저장
    private ResultSet rs2;

    // 데이터 조회 - print

    public  List<Member> list()  {
        List<Member> memberList = new ArrayList<>(); // 출력용
        Connection con = poolTest.getConnection();

        String sql = " select X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X좌표, Y좌표 " +
                " from " + tableName;
        /*
        ORDER BY distance
        limit 1, 20;
         */

        try {
            rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                String test1 = rs.getString("X_SWIFI_MGR_NO");
                String test2 = rs.getString("X_SWIFI_WRDOFC");
                String test3 = rs.getString("X_SWIFI_MAIN_NM");
                String test4 = rs.getString("X_SWIFI_ADRES1");
                String test5 = rs.getString("LNT");
                String test6 = rs.getString("LAT");

                Member member = new Member();
                member.setX_SWIFI_ADRES1(test1);
                member.setX_SWIFI_MAIN_NM(test2);
                member.setX_SWIFI_MGR_NO(test3);
                member.setX_SWIFI_WRDOFC(test4);
                member.setLAT(test4);
                member.setLNT(test4);
                memberList.add(member);
                System.out.println(test1 + ", " + test2 + ", "
                        + test3 + ", " + test4 + ", " + test5 + ", " + test6
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
        return memberList;
    }

    // 데이터입력
    public void register( String X_SWIFI_MGR_NO, String X_SWIFI_WRDOFC, String X_SWIFI_MAIN_NM, String X_SWIFI_ADRES1, String LAT, String LNT ) {
        Connection con = poolTest.getConnection();


        String sql = " INSERT INTO " + tableName + " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X좌표, Y좌표) "
         +" VALUES ( ?, ?, ?, ?, ?, ?)";

        try {
            psmt = con.prepareStatement(sql);
//            psmt.setInt(1, test0);
            psmt.setString(1, X_SWIFI_MGR_NO);
            psmt.setString(2, X_SWIFI_WRDOFC);
            psmt.setString(3, X_SWIFI_MAIN_NM);
            psmt.setString(4, X_SWIFI_ADRES1);
            psmt.setString(5, LNT); // x좌표
            psmt.setString(6, LAT); // y좌표
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
                    this.register(mem.X_SWIFI_MGR_NO, mem.X_SWIFI_WRDOFC, mem.X_SWIFI_MAIN_NM, mem.X_SWIFI_ADRES1, mem.LNT, mem.LAT);
                }
//                this.list();

            } else{
                break;
            }
            start += 999;
            end += 999;
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
                    "    (6371 * acos(cos(radians(Y좌표))" +
                    "    * cos(radians(" + inputLAT  + "))" +
                    "    * cos(radians(" + inputLNT + ") - radians(X좌표)) " +
                    "    + sin(radians(Y좌표)) * sin(radians(" + inputLAT + ")))), 4)" +



                      " ;";

            stmt.execute(getDistance);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            poolTest.releaseConnection(con);
        }
    }




//    public void getDistance(double lat1, double lnt1) {
//        Connection con = poolTest.getConnection();
//        int cnt = 0;
//
//        String sql = " select LAT, LNT " +
//                " from " + tableName;
//
//        try {
//            rs = con.createStatement().executeQuery(sql);
//            while (rs.next()) {
//                Double lat2 = Double.parseDouble(rs.getString("LAT"));
//                Double lnt2 = Double.parseDouble(rs.getString("LNT"));
//                cnt++;
//                System.out.println(cnt);
////                double distance = LocationDistance.distanceKiloMeter(lat1, lnt1, lat2, lnt2);
////                String sql2 = " UPDATE " + tableName +
////                        " SET DISTANCE =" + distance +
////                        " WHERE LAT = " + lat2;
////                rs2 = con.createStatement().executeQuery(sql2);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//        //test UPDATE pubwifi2
//        //SET DISTANCE = 0.5;
////        String sql = " UPDATE " + tableName +
////                " SET DISTANCE = LAT";
//
//
//
//    }
//    public void register(double distance, String memLAT) {
//        Connection con = poolTest.getConnection();
//
//
//        String sql = " UPDATE " + tableName + " SET DISTANCE = "
//                +  String.valueOf(distance)
//                + " WHERE LAT = " + memLAT ;
//
//        try {
//            psmt = con.prepareStatement(sql);
////            psmt.setInt(1, test0);
////            psmt.setString(1, String.valueOf(distance));
//            int result = psmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        if (con != null) {
//            poolTest.releaseConnection(con);
//        }
//    }
//
//    public void registerDistance(ApiExplorer apiExplorer, double lat2, double lnt2) {
//        int start = 1;
//        int end = 999;
//
//        // 테스트
//        while (true) {
//
//            String response = apiExplorer.httpGet(start, end);
//            if (!response.contains("MESSAGE\":\"해당하는 데이터가 없습니다.")) {
//                List<Member> items = apiExplorer.parseJson(response);
//
//                for (Member mem : items) {
//                    double lat1 = Double.parseDouble(mem.LAT);
//                    double lnt1 = Double.parseDouble(mem.LNT);
//                    double distance = LocationDistance.distanceKiloMeter(lat1, lnt1, lat2, lnt2);
//                    this.register(distance, mem.LAT);
//                }
////                this.list();
//
//            } else{
//                break;
//            }
//            start += 999;
//            end += 999;
//        }
//    }

}
