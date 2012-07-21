package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import jp.yattom.pragtdd.inventory.InventoryException;
import jp.yattom.pragtdd.inventory.Item;
import jp.yattom.pragtdd.inventory.ItemRepository;

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
        assertThat(resp, equalTo("<items></items>"));
    }

    @Test
    public void 商品を一覧できる_1件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        String resp = new ItemResource().allItems();
        assertThat(resp,
                equalTo("<items><item><name>商品A</name></item></items>"));
    }

    @Test
    public void 商品を一覧できる_複数件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        ItemRepository.getInstance().store(new Item("商品B"));
        ItemRepository.getInstance().store(new Item("商品C"));

        StringBuilder expected = new StringBuilder();
        expected.append("<items>");
        expected.append("<item><name>商品A</name></item>");
        expected.append("<item><name>商品B</name></item>");
        expected.append("<item><name>商品C</name></item>");
        expected.append("</items>");

        String resp = new ItemResource().allItems();
        assertThat(resp, is(expected.toString()));
    }

    @Test
    public void 商品を新規作成できる() throws Exception {
        StringBuilder req = new StringBuilder();
        req.append("<item>");
        req.append("<name>商品X</name>");
        req.append("</item>");
        ByteArrayInputStream is = new ByteArrayInputStream(req.toString()
                .getBytes("utf-8"));
        new ItemResource().createItem(is);

        assertThat(ItemRepository.getInstance().findByName("商品X").getName(),
                is("商品X"));
    }

    @Test
    public void 商品を新規作成できる_同じ名前はエラー() throws Exception {
        StringBuilder req = new StringBuilder();
        req.append("<item>");
        req.append("<name>商品X</name>");
        req.append("</item>");
        ByteArrayInputStream is = new ByteArrayInputStream(req.toString()
                .getBytes("utf-8"));
        new ItemResource().createItem(is);

        try {
            is.reset();
            new ItemResource().createItem(is);
            fail("同じ名前では作成できない");
        } catch (InventoryException e) {
            // ok
        }
    }
}
