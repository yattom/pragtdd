package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.yattom.pragtdd.inventory.InventoryException;
import jp.yattom.pragtdd.inventory.Item;
import jp.yattom.pragtdd.inventory.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

        String resp = new ItemResource().allItems();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(resp
                .getBytes("utf-8")));
        Element root = doc.getDocumentElement();
        List<Item> items = new ArrayList<>();
        NodeList elements = root.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            items.add(ItemResource.readItem((Element) elements.item(i)));
        }

        assertThat(items.size(), is(3));
        assertThat(items,
                hasItems(new Item("商品A"), new Item("商品B"), new Item("商品C")));
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
