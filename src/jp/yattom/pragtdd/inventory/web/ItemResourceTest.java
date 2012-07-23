package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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
        assertThat(resp, equalTo(itemsXml()));
    }

    @Test
    public void 商品を一覧できる_1件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        String resp = new ItemResource().allItems();
        assertThat(resp, equalTo(itemsXml("商品A")));
    }

    @Test
    public void 商品を一覧できる_複数件の場合() throws Exception {
        ItemRepository.getInstance().store(new Item("商品A"));
        ItemRepository.getInstance().store(new Item("商品B"));
        ItemRepository.getInstance().store(new Item("商品C"));

        String expected = itemsXml("商品A", "商品B", "商品C");

        String resp = new ItemResource().allItems();
        assertThat(resp, is(expected));
    }

    @Test
    public void 商品を新規作成できる() throws Exception {
        String name = "商品X";
        new ItemResource().createItem(toStream(itemXml(name)));

        assertThat(ItemRepository.getInstance().findByName(name).getName(),
                is(name));
    }

    @Test
    public void 商品を新規作成できる_同じ名前はエラー() throws Exception {
        String name = "商品X";
        new ItemResource().createItem(toStream(itemXml(name)));

        try {
            new ItemResource().createItem(toStream(itemXml(name)));
            fail("同じ名前では作成できない");
        } catch (InventoryException e) {
            // ok
        }
    }

    public static InputStream toStream(String xml) {
        try {
            return new ByteArrayInputStream(xml.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // utf-8なので起きないはず
            throw new RuntimeException(e);
        }
    }

    public static String itemXml(String name) {
        StringBuilder req = new StringBuilder();
        req.append("<item>");
        req.append("<name>" + name + "</name>");
        req.append("</item>");
        return req.toString();
    }

    public static String itemsXml(String... names) {
        StringBuilder expected = new StringBuilder();
        expected.append("<items>");
        for (String name : names) {
            expected.append(itemXml(name));
        }
        expected.append("</items>");
        String ex = expected.toString();
        return ex;
    }

}
