package model;

import org.json.JSONObject;

// An item available on the restaurant's menu.
public class MenuItem {
    private String itemName;
    private int price; // in cents

    /*
     * Creates a menu item with a given name and price
     */
    public MenuItem(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /*
     * EFFECTS: creates Json object for MenuItem
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Item Name", itemName);
        json.put("Price", price);
        return json;
    }      

    @Override
    public String toString() {
        return getItemName();
    }

    @Override
    public boolean equals(Object obj) {
        Boolean value = false;
        MenuItem other = (MenuItem) obj;
        if (itemName.equals(other.itemName)) {
            value = true;
        }
        return value;
    }

    
}
