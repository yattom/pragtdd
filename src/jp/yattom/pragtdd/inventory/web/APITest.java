package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.yattom.pragtdd.inventory.web.test.ItemResourceUtil;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class APITest {

    @Test
    public void 一連の正常系が動作する() throws Exception {
        String name = "APIテスト用商品";
        assertThat(商品が存在しない(name), is(true));

        商品を作成する(name);
        assertThat(商品が存在する(name), is(true));
        assertThat(商品の在庫(name), is(0));

        商品の在庫を追加する(name, 6);
        assertThat(商品の在庫(name), is(6));

        商品の在庫を減らす(name, 4);
        assertThat(商品の在庫(name), is(2));
    }

    private void 商品の在庫を減らす(String name, int amount) {
        // TODO Auto-generated method stub

    }

    private void 商品の在庫を追加する(String name, int amount) {
        // TODO Auto-generated method stub

    }

    private int 商品の在庫(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    private boolean 商品が存在する(String name) throws Exception {
        URL url = new URL("http://localhost:8080/pragtdd-spike/items/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = (InputStream) conn.getContent();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(is);
        NodeList items = doc.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element element = (Element) items.item(i);
            NodeList nameElement = element.getElementsByTagName("name");
            String nameOfItem = ((Element) nameElement.item(0))
                    .getTextContent();
            if (nameOfItem.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void 商品を作成する(String name) throws Exception {
        URL url = new URL("http://localhost:8080/pragtdd-spike/items/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-type", "application/xml");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                conn.getOutputStream(), "utf-8"));
        out.print(ItemResourceUtil.itemXml(name));
        out.flush();
        conn.getInputStream();
        assertThat(conn.getResponseCode(), is(HttpURLConnection.HTTP_NO_CONTENT));
    }

    private boolean 商品が存在しない(String name) throws Exception {
        return !商品が存在する(name);
    }

}
