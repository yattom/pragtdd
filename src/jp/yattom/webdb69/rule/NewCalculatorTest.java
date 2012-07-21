package jp.yattom.webdb69.rule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import jp.yattom.webdb69.tutorial.Calculator;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class NewCalculatorTest {
    @DataPoints
    public static Fixture[] DATA = { new Fixture(3, 2, 1.5f),
            new Fixture(10, 2, 5f), new Fixture(3, 0, null),
            new Fixture(10, 0, null), };
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Calculator calc;

    @Before
    public void setUp() {
        calc = new Calculator();
    }

    @Theory
    public void 除算結果が取得できること(Fixture f) {
        Assume.assumeTrue(f.y != 0);
        assertThat(calc.divide(f.x, f.y), is(f.expected));
    }

    @Theory
    public void 除数が0なら例外になること(Fixture f) {
        Assume.assumeTrue(f.y == 0);
        exception.expect(IllegalArgumentException.class);
        calc.divide(f.x, f.y);
    }

    static class Fixture {
        int x;
        int y;
        Float expected;

        public Fixture(int x, int y, Float expected) {
            this.x = x;
            this.y = y;
            this.expected = expected;
        }

    }
}
