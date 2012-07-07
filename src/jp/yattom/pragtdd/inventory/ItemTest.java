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

    @Test
    public void 在庫を取得できる_減らして取得() throws Exception {
        item.addStock(30);
        item.addStock(-10);
        assertThat(item.getStock(), is(20));
    }

    @Test
    public void 在庫を取得できる_ちょうどゼロになる() throws Exception {
        item.addStock(10);
        item.addStock(-10);
        assertThat(item.getStock(), is(0));
    }

    @Test(expected=InventoryException.class)
    public void 在庫を取得できる_マイナスになるのは不可() throws Exception {
        item.addStock(5);
        item.addStock(-10);
    }

    @Test
    public void 在庫を再設定できる() throws Exception {
        item.setStock(5);
        assertThat(item.getStock(), is(5));
    }

    @Test
    public void 在庫を再設定できる_ゼロにできる() throws Exception {
        item.addStock(5);
        item.setStock(0);
        assertThat(item.getStock(), is(0));
    }

    @Test
    public void 在庫を再設定できる_変化しない() throws Exception {
        item.addStock(5);
        item.setStock(5);
        assertThat(item.getStock(), is(5));
    }

    @Test(expected=InventoryException.class)
    public void 在庫を再設定できる_マイナスにはできない() throws Exception {
        item.setStock(-5);
    }
}
