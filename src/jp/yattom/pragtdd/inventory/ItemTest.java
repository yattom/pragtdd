package jp.yattom.pragtdd.inventory;

import static jp.yattom.pragtdd.inventory.test.TestUtil.buildDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    private static final Date Date0109 = buildDate(2012, 1, 9);
    private static final Date Date0110 = buildDate(2012, 1, 10);
    private static final Date Date0111 = buildDate(2012, 1, 11);
    private static final Date Date0115 = buildDate(2012, 1, 15);
    private static final Date Date0116 = buildDate(2012, 1, 16);
    private static final Date Date0120 = buildDate(2012, 1, 20);
    private static final Date Date0121 = buildDate(2012, 1, 21);
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

    @Test
    public void 在庫を減らせる_ちょうどマイナス1() throws Exception {
        item.addStock(5);
        item.addStock(-6);
    }

    @Test
    public void 在庫を減らせる_最小の負数() throws Exception {
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

    @Test
    public void 在庫を再設定できる_マイナスにできる() throws Exception {
        item.setStock(-1);
    }

    @Test
    public void 指定した時点の在庫数を取得できる_1件だけ() throws Exception {
        item.addStock(10, Date0110);
        assertThat(item.getStock(Date0109), is(0));
        assertThat(item.getStock(Date0111), is(10));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_0件() throws Exception {
        assertThat(item.getStock(Date0110), is(0));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_複数件() throws Exception {
        item.addStock(10, Date0110);
        item.addStock(20, Date0115);
        item.addStock(-5, Date0120);
        assertThat(item.getStock(Date0109), is(0));
        assertThat(item.getStock(Date0111), is(10));
        assertThat(item.getStock(Date0116), is(30));
        assertThat(item.getStock(Date0121), is(25));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_順序には関係ないこと() throws Exception {
        item.addStock(20, Date0115);
        item.addStock(-5, Date0120);
        item.addStock(10, Date0110);
        assertThat(item.getStock(Date0109), is(0));
        assertThat(item.getStock(Date0111), is(10));
        assertThat(item.getStock(Date0116), is(30));
        assertThat(item.getStock(Date0121), is(25));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_在庫増の瞬間の時刻() throws Exception {
        item.addStock(20, buildDate(2012, 1, 1, 10, 0, 0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 9, 59, 59)), is(0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 10, 0, 0)), is(20));
        assertThat(item.getStock(buildDate(2012, 1, 1, 10, 0, 1)), is(20));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_在庫増が同時に起きる場合() throws Exception {
        item.addStock(20, buildDate(2012, 1, 1, 10, 0, 0));
        item.addStock(10, buildDate(2012, 1, 1, 10, 0, 0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 9, 59, 59)), is(0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 10, 0, 0)), is(30));
    }

    @Test
    public void 指定した時点の在庫数を取得できる_在庫増減が同時に起きる場合() throws Exception {
        item.addStock(-10, buildDate(2012, 1, 1, 10, 0, 0));
        item.addStock(20, buildDate(2012, 1, 1, 10, 0, 0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 9, 59, 59)), is(0));
        assertThat(item.getStock(buildDate(2012, 1, 1, 10, 0, 0)), is(10));
    }

    @Test
    public void 履歴が取得できる() throws Exception {
        item.addStock(10, Date0110);
        assertThat(item.getHistory().get(0).getDate(), equalTo(Date0110));
        assertThat(item.getHistory().get(0).getValue(), equalTo(10));

        item.addStock(-3, Date0115);
        assertThat(item.getHistory().get(1).getDate(), equalTo(Date0115));
        assertThat(item.getHistory().get(1).getValue(), equalTo(-3));
    }

    @Test
    public void 履歴が取得できる_最初は空() throws Exception {
        assertThat(item.getHistory().size(), is(0));
    }
    
    @Test
    public void 同値_nameが一致すればequal() throws Exception {
        Item item1 = new Item("同じ名前");
        Item item2 = new Item("同じ名前");
        assertThat(item1.equals(item2), is(true));
    }
    
    @Test
    public void 同値_nameが違えば同値でない() throws Exception {
        Item item1 = new Item("同じ名前");
        Item item2 = new Item("違う名前");
        assertThat(item1.equals(item2), is(false));
    }

    @Test
    public void 同値_別classなら同値でない() throws Exception {
        Item item1 = new Item("同じ名前");
        Object obj2 = new Object();
        assertThat(item1.equals(obj2), is(false));
    }
}
