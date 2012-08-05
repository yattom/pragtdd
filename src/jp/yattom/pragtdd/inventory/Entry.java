package jp.yattom.pragtdd.inventory;

import java.util.Date;

public class Entry {
    private int value;
    private Date date;

    public Entry(Date date, int value) {
        this.value = value;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}