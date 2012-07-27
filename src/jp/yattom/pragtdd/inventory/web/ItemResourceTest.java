package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


import jp.yattom.pragtdd.inventory.InventoryException;
import jp.yattom.pragtdd.inventory.Item;
import jp.yattom.pragtdd.inventory.ItemRepository;
import jp.yattom.pragtdd.inventory.web.test.ItemResourceUtil;

import org.junit.Before;
import org.junit.Test;

public class ItemResourceTest {
    @Before
    public void ItemRepository初期化() {
        ItemRepository.getInstance().clear();
    }

    @Test
    public void 商品を一覧できる_空の場合() throws Exception {
        String resp = new ItemResource().allItems();
        assertThat(resp, equalTo(ItemResourceUtil.itemsXml()));
    }

    @Test
    public void 商品を一覧できる_1件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        String resp = new ItemResource().allItems();
        assertThat(resp, equalTo(ItemResourceUtil.itemsXml("商品A")));
    }

    @Test
    public void 商品を一覧できる_複数件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        ItemRepository.getInstance().store(new Item("商品B"));
        ItemRepository.getInstance().store(new Item("商品C"));

        String expected = ItemResourceUtil.itemsXml("商品A", "商品B", "商品C");

        String resp = new ItemResource().allItems();
        assertThat(resp, is(expected));
    }

    @Test
    public void 商品を新規作成できる() throws Exception {
        String name = "商品X";
        new ItemResource().createItem(ItemResourceUtil.toStream(ItemResourceUtil.itemXml(name)));

        assertThat(ItemRepository.getInstance().findByName(name).getName(),
                is(name));
    }

    @Test
    public void 商品を新規作成できる_同じ名前はエラー() throws Exception {
        String name = "商品X";
        new ItemResource().createItem(ItemResourceUtil.toStream(ItemResourceUtil.itemXml(name)));

        try {
            new ItemResource().createItem(ItemResourceUtil.toStream(ItemResourceUtil.itemXml(name)));
            fail("同じ名前では作成できない");
        } catch (InventoryException e) {
            // ok
        }
    }

}
