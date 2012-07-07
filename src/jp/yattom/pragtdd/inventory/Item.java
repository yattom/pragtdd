package jp.yattom.pragtdd.inventory;

public class Item {

    private int stock;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void addStock(int value) throws InventoryException {
        if(value == 0) {
            throw new InventoryException("在庫数の増加分としてゼロは許されない");
        }
        setStock(stock + value);
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
}
