package del;

import javax.xml.transform.Result;
import java.sql.*;

public class JDBConnect {

    public Connection con; // 데이터베이스와 연결을 담당합니다
    public Statement stmt; // 인파라미터가 없는 정적 쿼리문 실행
    public PreparedStatement psmt; // 인파리미터가 있는 동적 쿼리문
    public ResultSet rs; // SELECT 쿼리문 결과 저장


    static String id = "testuser3";
    static String pwd = "onebase";
    static String databaseName = "testwifi";



    // 기본 생성자
    public void connect() {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost/" + databaseName;
            this.con = DriverManager.getConnection(url, id, pwd);

//            System.out.println("DB 연결 성공");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int close() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (psmt != null) psmt.close();
            if (con != null) con.close();
//            System.out.println("JDBC  자원 해제");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
// statement도 구현해야할까? 테스트중
//    public ResultSet statement(String sql) {
//        try {
//            return this.con.createStatement().executeQuery(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



}
