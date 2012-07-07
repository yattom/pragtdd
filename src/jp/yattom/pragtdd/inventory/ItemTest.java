package jp.yattom.pragtdd.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ItemTest {
    @Test
    public void 在庫を取得できる_最初はゼロ() throws Exception {
        Item item = new Item();
        assertThat(item.getStock(), is(0));
    }
    
    @Test
    public void 在庫を取得できる_増やして取得() throws Exception {
        Item item = new Item();
        item.addStock(10);
        assertThat(item.getStock(), is(10));
    }
    
    @Test
    public void 在庫を増やす_連続() throws Exception {
        Item item = new Item();
        item.addStock(10);
        item.addStock(5);
        assertThat(item.getStock(), is(15));
    }
    
    @Test
    public void 在庫を増やす_ゼロは許さない() throws Exception {
        Item item = new Item();
        try {
            item.addStock(0);
            fail("増分としてゼロは不可");
        } catch(InventoryException e) {
            //ok
        }
    }
}
