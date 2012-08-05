package jp.yattom.pragtdd.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    private String name;
    private List<Entry> history;

    public Item(String name) {
        this();
        this.name = name;
    }

    public Item() {
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
        addEntry(new Entry(date, stock - getStock()));
    }

    public String getName() {
        return name;
    }

    public void addStock(int value, Date date) throws InventoryException {
        if (value == 0) {
            throw new InventoryException("在庫数の増加分としてゼロは許されない");
        }
        addEntry(new Entry(date, value));
    }

    private void addEntry(Entry entry) throws InventoryException {
        history.add(entry);
    }

    public int getStock(Date asOf) {
        int total = 0;
        for (Entry e : history) {
            if (!e.getDate().after(asOf)) {
                total += e.getValue();
            }
        }
        return total;
    }

    public List<Entry> getHistory() {
        return history;
    }

    // TODO nameを変更できないようにする
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Item)) {
            return false;
        }
        Item otherItem = (Item) other;
        return otherItem.getName().equals(getName());
    }
}
