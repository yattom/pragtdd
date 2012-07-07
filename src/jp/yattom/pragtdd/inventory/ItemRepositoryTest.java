package jp.yattom.pragtdd.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    @Test(expected=InventoryException.class)
    public void Itemを保存して取り出せる_存在しなければ例外() throws Exception {
        repo.findByName("存在しない商品");
    }

    @Test(expected=InventoryException.class)
    public void Itemを保存して取り出せる_同じ名前で二度保存できない() throws Exception {
        Item item1 = new Item("商品A");
        Item item2 = new Item("商品A");
        repo.store(item1);
        repo.store(item2);
    }
}
