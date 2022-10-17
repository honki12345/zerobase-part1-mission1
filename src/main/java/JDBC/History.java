package JDBC;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class History {
    public int ID;
    public String LNT; // x좌표
    public String LAT; // y좌표
    public Timestamp date;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLNT() {
        return LNT;
    }

    public void setLNT(String LNT) {
        this.LNT = LNT;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

}