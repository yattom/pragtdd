package jp.yattom.webdb69.construct;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PrameterizedTest {
    @DataPoint
    public static int INT_PARAM_1 = 3;
    @DataPoint
    public static int INT_PARAM_2 = 5;

    public PrameterizedTest() {
        System.out.println("initilized");
    }

    @Theory
    public void test(int param) {
        System.out.println("test(" + param + ")");
    }
}
