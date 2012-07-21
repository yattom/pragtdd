package jp.yattom.webdb69.matcher;

import java.util.Date;
import static org.junit.Assert.*;
import static jp.yattom.webdb69.matcher.IsDate.*;

import org.junit.Test;

public class IsDateDemo {

    @Test
    public void test() {
        assertThat(new Date(), dateOf(2012, 1, 1));
    }

}
