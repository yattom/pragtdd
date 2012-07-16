package jp.yattom.pragtdd.inventory.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import jp.yattom.pragtdd.inventory.Item;
import jp.yattom.pragtdd.inventory.ItemRepository;

@Path("/items")
public class ItemResource {
    @GET
    @Produces("application/xml")
    public String allItems() {
        StringBuilder s = new StringBuilder();
        s.append("<item>");
        for (Item item : ItemRepository.getInstance().allItems()) {
            s.append("<name>" + item.getName() + "</name>");
        }
        s.append("</item>");
        return s.toString();
    }
}
