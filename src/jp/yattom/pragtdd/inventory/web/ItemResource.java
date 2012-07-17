package jp.yattom.pragtdd.inventory.web;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.yattom.pragtdd.inventory.InventoryException;
import jp.yattom.pragtdd.inventory.Item;
import jp.yattom.pragtdd.inventory.ItemRepository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Path("/items")
public class ItemResource {
    @GET
    @Produces("application/xml")
    public String allItems() {
        StringBuilder s = new StringBuilder();
        s.append("<items>");
        for (Item item : ItemRepository.getInstance().allItems()) {
            s.append("<item>");
            s.append("<name>" + item.getName() + "</name>");
            s.append("</item>");
        }
        s.append("</items>");
        return s.toString();
    }

    @POST
    @Consumes("application/xml")
    public void createItem(InputStream is) throws ParserConfigurationException,
            SAXException, IOException, InventoryException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(is);
        Element root = doc.getDocumentElement();

        Item newItem = new Item();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE
                    && n.getNodeName().equals("name")) {
                newItem.setName(n.getTextContent());
            }
        }
        ItemRepository.getInstance().store(newItem);
    }
}
