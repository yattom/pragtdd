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
        setStock(stock + value);
    }

    private void validateNewStockValue(int value) throws InventoryException {
        if(value < 0) {
            throw new InventoryException("在庫数をマイナスにはできない");
        }
    }

    public void setStock(int stock) throws InventoryException {
        validateNewStockValue(stock);
        this.stock = stock;
    }
}
