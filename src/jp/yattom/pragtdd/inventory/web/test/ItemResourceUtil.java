package jp.yattom.pragtdd.inventory.web.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class ItemResourceUtil {

    public static String itemsXml(String... names) {
        StringBuilder expected = new StringBuilder();
        expected.append("<items>");
        for (String name : names) {
            expected.append(ItemResourceUtil.itemXml(name));
        }
        expected.append("</items>");
        String ex = expected.toString();
        return ex;
    }

    public static String itemXml(String name) {
        StringBuilder req = new StringBuilder();
        req.append("<item>");
        req.append("<name>" + name + "</name>");
        req.append("</item>");
        return req.toString();
    }

    public static InputStream toStream(String xml) {
        try {
            return new ByteArrayInputStream(xml.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // utf-8なので起きないはず
            throw new RuntimeException(e);
        }
    }

}
