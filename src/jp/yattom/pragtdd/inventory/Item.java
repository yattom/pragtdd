package jp.yattom.pragtdd.inventory;

public class Item {

    private int stock;

    public int getStock() {
        return stock;
    }

    public void addStock(int value) throws InventoryException {
        if(value == 0) {
            throw new InventoryException("在庫数の増加分としてゼロは許されない");
        }
        if(stock + value < 0) {
            throw new InventoryException("在庫数をマイナスにはできない");
        }
        this.stock += value;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
