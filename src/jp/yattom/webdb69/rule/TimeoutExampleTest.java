package jp.yattom.webdb69.rule;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TimeoutExampleTest {
    @Rule
    public Timeout timeout = new Timeout(100);
    
    @Test
    public void test() throws Exception { 
        Thread.sleep(1000);
        fail("no timeout");
    }
}
