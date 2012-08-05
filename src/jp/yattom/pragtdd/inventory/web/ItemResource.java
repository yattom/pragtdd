package jp.yattom.pragtdd.inventory.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("{name}")
    @Produces("application/xml")
    public String item(@PathParam("name") String name) throws InventoryException {
        Item item = ItemRepository.getInstance().findByName(name);
        
        StringBuilder s = new StringBuilder();
        s.append("<item>");
        s.append("<name>" + item.getName() + "</name>");
        s.append("<amount>" + item.getStock() + "</amount>");
        s.append("</item>");
        return s.toString();
    }

    @POST
    @Consumes("application/xml")
    public void createItem(InputStream is) throws ParserConfigurationException,
            SAXException, IOException, InventoryException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Item newItem = readItem(itemElement);
        ItemRepository.getInstance().store(newItem);
    }

    @POST
    @Path("{name}/entries")
    @Consumes("application/xml")
    public void createEntry(@PathParam("name") String name, InputStream is) throws ParserConfigurationException,
            SAXException, IOException, InventoryException, ParseException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = builder.parse(is);
        Element entryElement = doc.getDocumentElement();

        Date date = null;
        int value = 0;
        NodeList nodes = entryElement.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE
                    && n.getNodeName().equals("date")) {
                date = SimpleDateFormat.getInstance().parse(n.getTextContent());
            }
            if (n.getNodeType() == Node.ELEMENT_NODE
                    && n.getNodeName().equals("amount")) {
                value = Integer.parseInt(n.getTextContent());
            }
        }
        Item item = ItemRepository.getInstance().findByName(name);
        item.addStock(value, date);
    }

    static Item readItem(Element itemElement) {
        Item newItem = new Item();
        NodeList nodes = itemElement.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE
                    && n.getNodeName().equals("name")) {
                newItem.setName(n.getTextContent());
            }
        }
        return newItem;
    }
}
