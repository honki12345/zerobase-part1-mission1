package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class PoolTest {
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Connection> free;
    private ArrayList<Connection> used;

    private String databaseName = "testwifi";
    private String url = "jdbc:mariadb://localhost/" + databaseName;
    private String user = "testuser3";
    private String password = "onebase";

    private int initailCon = 5;
    private int maxCons = 10;
    private int numCons = 0;
    private static PoolTest cp;

    public PoolTest()  {
        if (initailCon < 0)
            initailCon = 5;
        if (maxCons < 0)
            maxCons = 10;

        free = new ArrayList<Connection>(initailCon);
        used = new ArrayList<Connection>(initailCon);

        while (numCons < initailCon) {
            try {
                addConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // ConnectionPool 객체
    public static PoolTest getInstance() {
        if (cp == null) {
            synchronized (PoolTest.class) {
                cp = new PoolTest();
            }
        }
        return cp;
    }

    private void addConnection() throws SQLException {
        free.add(getNewConnection());
    }

    private Connection getNewConnection() throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ++numCons;
        return con;
    }

    public synchronized Connection getConnection() {
        if (free.isEmpty()) {
            while (numCons < maxCons) {
                try {
                    addConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Connection _con = free.get(free.size() - 1);
        free.remove(_con);
        used.add(_con);
        return _con;
    }

    public synchronized void releaseConnection(Connection _con) {
        boolean flag = false;
        if (used.contains(_con)) {
            used.remove(_con);
            numCons--;
            flag = true;
        } else {
            try {
                throw new SQLException();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (flag) {
                free.add(_con);
                numCons++;
            } else {
                _con.close();
            }
        } catch (SQLException e) {
            try {
                _con.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void closeAll() {
        for (int i = 0; i < used.size(); i++) {
            Connection _con = (Connection) used.get(i);
            used.remove(i--);
            try {
                _con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < free.size(); i++) {
            Connection _con = (Connection) free.get(i);
            free.remove(i--);
            try {
                _con.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public int getMaxCons() {
        return maxCons;
    }
    public int getNumCons() {
        return numCons;
    }

}
