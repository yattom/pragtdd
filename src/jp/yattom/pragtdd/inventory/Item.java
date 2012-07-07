package jp.yattom.pragtdd.inventory;

public class Item {

    private int stock;

    public int getStock() {
        return stock;
    }

    public void addStock(int value) throws InventoryException {
        this.stock += value;
    }

}
