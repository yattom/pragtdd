package jp.yattom.pragtdd.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    static private ItemRepository instance = new ItemRepository();

    private Map<String, Item> repository;

    private ItemRepository() {
        repository = new HashMap<String, Item>();
    }

    static public ItemRepository getInstance() {
        return instance;
    }

    public void store(Item item) throws InventoryException {
        if (repository.containsKey(item.getName())) {
            throw new InventoryException("指定された名前の商品はすでに存在します");
        }
        repository.put(item.getName(), item);
    }

    public Item findByName(String name) throws InventoryException {
        if (!repository.containsKey(name)) {
            throw new InventoryException("指定された名前の商品は存在しません");
        }
        return repository.get(name);
    }

    public void clear() {
        repository.clear();
    }

    public Collection<Item> allItems() {
        return repository.values();
    }

}
