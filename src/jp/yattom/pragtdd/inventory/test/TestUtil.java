package jp.yattom.pragtdd.inventory.test;

import java.util.Calendar;
import java.util.Date;

public class TestUtil {
    static public Date buildDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    static public Date buildDate(int year, int month, int day, int hour,
            int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, min, sec);
        return cal.getTime();
    }
}
