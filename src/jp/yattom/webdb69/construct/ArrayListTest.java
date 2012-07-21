package jp.yattom.webdb69.construct;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ArrayListTest {
    static class Fixture {
        private ArrayList<String> sut;
    }
    public static class 初期状態のとき {
        private Fixture fixture = new Fixture();
        
        @Before
        public void setUp() throws Exception { 
            fixture.sut = new ArrayList<>();
        }
        
        @Test
        public void sizeは0() throws Exception {
            assertThat(fixture.sut.size(), is(0));
        }
        
        @Test
        public void 値を追加できる_1個目の値() {
            fixture.sut.add("Hello");
            assertThat(fixture.sut.get(0), is("Hello"));
        }
    }
    
    public static class Helloが含まれるとき {
        private Fixture fixture = new Fixture();
        
        @Before
        public void setUp() throws Exception { 
            fixture.sut = new ArrayList<>();
            fixture.sut.add("Hello");
        }
        
        @Test
        public void sizeは1() throws Exception {
            assertThat(fixture.sut.size(), is(1));
        }
        
        @Test
        public void 値を追加できる_2個目の値() {
            fixture.sut.add("World");
            assertThat(fixture.sut.get(0), is("Hello"));
            assertThat(fixture.sut.get(1), is("World"));
        }
    }

}
