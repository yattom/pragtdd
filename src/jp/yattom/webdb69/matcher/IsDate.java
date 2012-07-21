package jp.yattom.webdb69.matcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IsDate extends BaseMatcher<Date> {

    private int y;
    private int m;
    private int d;
    private Object actual;

    public IsDate(int y, int m, int d) {
        this.y = y;
        this.m = m;
        this.d = d;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if (!(actual instanceof Date)) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) actual);
        if (y == cal.get(Calendar.YEAR) && m == cal.get(Calendar.MONTH) + 1
                && d == cal.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(y + "/" + m + "/" + d);
        if (actual == null) {
            return;
        }
        desc.appendText(" but actual is ");
        desc.appendValue(new SimpleDateFormat("yyyy/MM/dd")
                .format((Date) actual));
    }

    public static Matcher<Date> dateOf(int y, int m, int d) {
        return new IsDate(y, m, d);
    }
}
