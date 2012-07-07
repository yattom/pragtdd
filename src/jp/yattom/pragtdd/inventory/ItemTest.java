package jp.yattom.pragtdd.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    private Item item;
    @Before
    public void itemを準備() {
        item = new Item();
    }

    @Test
    public void 在庫を取得できる_最初はゼロ() throws Exception {
        assertThat(item.getStock(), is(0));
    }

    @Test
    public void 在庫を取得できる_増やして取得() throws Exception {
        item.addStock(10);
        assertThat(item.getStock(), is(10));
    }

    @Test
    public void 在庫を増やす_連続() throws Exception {
        item.addStock(10);
        item.addStock(5);
        assertThat(item.getStock(), is(15));
    }

    @Test(expected = InventoryException.class)
    public void 在庫を増やす_ゼロは許さない() throws Exception {
        item.addStock(0);
    }
}
