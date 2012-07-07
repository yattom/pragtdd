package jp.yattom.pragtdd.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import org.junit.Before;
import org.junit.Test;

public class ItemRepositoryTest {
    private ItemRepository repo;

    @Before
    public void ItemManagerの準備() throws Exception {
        repo = ItemRepository.getInstance();
        repo.clear();
    }

    @Test
    public void Itemを保存して取り出せる() throws Exception {
        Item item = new Item("商品A");
        repo.store(item);
        assertThat(repo.findByName("商品A"), is(item));
    }

    @Test(expected = InventoryException.class)
    public void Itemを保存して取り出せる_存在しなければ例外() throws Exception {
        repo.findByName("存在しない商品");
    }

    @Test(expected = InventoryException.class)
    public void Itemを保存して取り出せる_同じ名前で二度保存できない() throws Exception {
        Item item1 = new Item("商品A");
        Item item2 = new Item("商品A");
        repo.store(item1);
        repo.store(item2);
    }

    @Test
    public void Itemの一覧を取得できる_空の場合() throws Exception {
        assertThat(repo.allItems().size(), is(0));
    }

    @Test
    public void Itemの一覧を取得できる_1件の場合() throws Exception {
        Item item = new Item("商品A");
        repo.store(item);
        assertThat(repo.allItems().size(), is(1));
        assertThat(repo.allItems(), hasItems(item));
    }

    @Test
    public void Itemの一覧を取得できる_複数件の場合() throws Exception {
        Item itemA = new Item("商品A");
        Item itemB = new Item("商品B");
        Item itemC = new Item("商品C");
        repo.store(itemA);
        repo.store(itemB);
        repo.store(itemC);
        assertThat(repo.allItems().size(), is(3));
        assertThat(repo.allItems(), hasItems(itemA, itemB, itemC));
    }
}
