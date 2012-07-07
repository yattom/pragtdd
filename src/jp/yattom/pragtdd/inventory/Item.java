package jp.yattom.pragtdd.inventory;

import java.util.Date;

public class Item {

    private int stock;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public int getStock() {
        return getStock(new Date());
    }

    public void addStock(int value) throws InventoryException {
        addStock(value, new Date());
    }

    public void setStock(int stock) throws InventoryException {
        if(stock < 0) {
            throw new InventoryException("在庫数をマイナスにはできない");
        }
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void addStock(int value, Date date) throws InventoryException {
        if(value == 0) {
            throw new InventoryException("在庫数の増加分としてゼロは許されない");
        }
        setStock(stock + value);
    }

    public int getStock(Date asOf) {
        return 0;
    }
}
