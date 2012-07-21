package jp.yattom.webdb69.rule;

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

public class ContextOnlyDemo {
    @Rule
    public ContextOnly context = new ContextOnly("server");

    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
