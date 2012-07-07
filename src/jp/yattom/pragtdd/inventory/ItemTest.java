package jp.yattom.pragtdd.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ItemTest {

    @Test
    public void 在庫を取得できる_最初はゼロ() {
        Item item = new Item();
        assertThat(item.getStock(), is(0));
    }
}
