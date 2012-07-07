package jp.yattom.pragtdd.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {

    private int stock;
    private String name;
    private List<Object[]> history;

    public Item(String name) {
        this.name = name;
        history = new ArrayList<>();
    }

    public int getStock() {
        return getStock(new Date());
    }

    public void addStock(int value) throws InventoryException {
        addStock(value, new Date());
    }

    public void setStock(int stock) throws InventoryException {
        setStock(stock, new Date());
    }
    
    public void setStock(int stock, Date date) throws InventoryException {
        addStockInternal(stock - getStock(), date);
    }

    public String getName() {
        return name;
    }

    public void addStock(int value, Date date) throws InventoryException {
        if (value == 0) {
            throw new InventoryException("在庫数の増加分としてゼロは許されない");
        }
        addStockInternal(value, date);
    }

    private void addStockInternal(int value, Date date) throws InventoryException {
        if (getStock() + value < 0) {
            throw new InventoryException("在庫数をマイナスにはできない");
        }
        history.add(new Object[] { date, value });
    }

    public int getStock(Date asOf) {
        int total = 0;
        for (Object[] h : history) {
            if (!((Date) h[0]).after(asOf)) {
                total += (int) h[1];
            }
        }
        return total;
    }
}
