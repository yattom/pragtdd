package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;

import jp.yattom.pragtdd.inventory.ItemRepository;

import org.junit.Test;

public class ItemResourceTest {

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

}
