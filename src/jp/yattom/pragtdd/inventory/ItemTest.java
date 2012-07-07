package jp.yattom.pragtdd.inventory;

import static jp.yattom.pragtdd.inventory.test.TestUtil.buildDate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    private Item item;

    @Before
    public void itemを準備() {
        item = new Item("商品");
    }

    @Test
    public void 在庫を取得できる_最初はゼロ() throws Exception {
        assertThat(item.getStock(), is(0));
    }

    @Test
    public void 在庫を増やせる_正数の最小() throws Exception {
        item.addStock(1);
        assertThat(item.getStock(), is(1));
    }

    @Test
    public void 在庫を増やせる_正数の最大() throws Exception {
        item.addStock(Integer.MAX_VALUE);
        assertThat(item.getStock(), is(Integer.MAX_VALUE));
    }

    @Test
    public void 在庫を増やせる_連続() throws Exception {
        item.addStock(10);
        item.addStock(5);
        assertThat(item.getStock(), is(15));
    }

    @Test(expected = InventoryException.class)
    public void 在庫を増やせる_増分ゼロは許さない() throws Exception {
        item.addStock(0);
    }

    @Test
    public void 在庫を減らせる_負数の最大値() throws Exception {
        item.addStock(30);
        item.addStock(-1);
        assertThat(item.getStock(), is(29));
    }

    @Test
    public void 在庫を減らせる_負数の最小値() throws Exception {
        // MIN_VALUE == -(MAX_VALUE + 1)なので、どうしても在庫がマイナスになってしまう。
        // MIN_VALUE+1にして、結果ゼロになるようにした。
        item.addStock(Integer.MAX_VALUE);
        item.addStock(Integer.MIN_VALUE + 1);
        assertThat(item.getStock(), is(0));
    }

    @Test
    public void 在庫を減らせる_ちょうどゼロになる() throws Exception {
        item.addStock(10);
        item.addStock(-10);
        assertThat(item.getStock(), is(0));
    }

    @Test(expected = InventoryException.class)
    public void 在庫を減らせる_マイナスになるのは不可_ちょうどマイナス1() throws Exception {
        item.addStock(5);
        item.addStock(-6);
    }

    @Test(expected = InventoryException.class)
    public void 在庫を減らせる_マイナスになるのは不可_最小の負数() throws Exception {
        item.addStock(Integer.MIN_VALUE);
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

    @Test(expected = InventoryException.class)
    public void 在庫を再設定できる_マイナスにはできない() throws Exception {
        item.setStock(-1);
    }

    @Test
    public void 指定した時点の在庫数を取得できる() throws Exception {
        item.addStock(10, buildDate(2012, 1, 10));
        assertThat(item.getStock(buildDate(2012, 1, 9)), is(0));
        assertThat(item.getStock(buildDate(2012, 1, 11)), is(10));
    }
}
