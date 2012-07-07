package jp.yattom.pragtdd.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    static class Entry {
        public int value;
        public Date date;
        public Entry(Date date, int value) {
            this.value = value;
            this.date = date;
        }
    }
    private String name;
    private List<Entry> history;

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
        history.add(new Entry(date, value));
    }

    public int getStock(Date asOf) {
        int total = 0;
        for (Entry e: history) {
            if (!e.date.after(asOf)) {
                total += e.value;
            }
        }
        return total;
    }
}
