package model;

import java.util.*;

import org.json.JSONObject;

// A categorized list of all available items on a restaurant's menu.
public class Menu {
    private ArrayList<MenuItem> appetizers;
    private ArrayList<MenuItem> mains;
    private ArrayList<MenuItem> sides;
    private ArrayList<MenuItem> desserts;
    private ArrayList<MenuItem> drinks;

    /*
     * Creates a menu with 5 different sections:
     *      - Appetizers;
     *      - Mains;
     *      - Sides;
     *      - Desserts; and
     *      - Drinks
     */
    public Menu() {
        appetizers = new ArrayList<>();
        mains = new ArrayList<>();
        sides = new ArrayList<>();
        desserts = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    /*
     * MODIFIES: list
     * EFFECTS: adds a new item to a list on the menu
     *          if item is not already in list;
     *          returns whether addition was successful or not
     */
    public boolean addItem(ArrayList<MenuItem> list, MenuItem item) {
        boolean success = false;
        Event event = new Event(item.getItemName() + " was added to the menu.");

        if (!list.contains(item)) {
            list.add(item);
            success = true;

            EventLog.getInstance().logEvent(event);
        }

        return success;
    }

    /*
     * MODIFIES: list
     * EFFECTS: removes an item from a list on the menu
     *          if item exists in list;
     *          returns whether removal was successful or not
     */
    public boolean removeItem(ArrayList<MenuItem> list, MenuItem item) {
        boolean success = false;
        Event event = new Event(item.getItemName() + " was removed from the menu.");

        if (list.contains(item)) {
            list.remove(item);
            success = true;

            EventLog.getInstance().logEvent(event);
        }

        return success;
    }

    public ArrayList<MenuItem> getAppetizers() {
        return appetizers;
    }

    public ArrayList<MenuItem> getMains() {
        return mains;
    }

    public ArrayList<MenuItem> getSides() {
        return sides;
    }

    public ArrayList<MenuItem> getDesserts() {
        return desserts;
    }

    public ArrayList<MenuItem> getDrinks() {
        return drinks;
    }

    /*
     * EFFECTS: creates Json object for Menu
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Appetizers", appetizers);
        json.put("Mains", mains);
        json.put("Sides", sides);
        json.put("Desserts", desserts);
        json.put("Drinks", drinks);
        return json;
    }  
}
