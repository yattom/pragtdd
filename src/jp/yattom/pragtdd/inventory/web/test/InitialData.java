package jp.yattom.pragtdd.inventory.web.test;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import jp.yattom.pragtdd.inventory.ItemRepository;

@Path("/init-data")
public class InitialData {
    @PUT
    public void load() {
        ItemRepository.getInstance().clear();
    }
}
