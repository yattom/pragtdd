package jp.yattom.webdb69.construct;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PermutationParameterizedTest {
    @DataPoint
    public static int INT_PARAM_1 = 3;
    @DataPoint
    public static int INT_PARAM_2 = 5;
    @DataPoint
    public static String STR_PARAM_1 = "Hello";
    @DataPoint
    public static String STR_PARAM_2 = "World";

    public PermutationParameterizedTest() {
        System.out.println("initilized");
    }

    @Theory
    public void test(int x, int y, String s) {
        System.out.println("test(" + x + ", " + y + ", \"" + s + "\")");
    }
}
