package persistence;

import model.Menu;
import model.MenuItem;
import model.OrderSummary;
import model.Tabs;

import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


// Reads a JSON data stored in a file to reload a given restaurant database.
// Code below is modelled after `JsonReader` class in `persistance` package of JsonSerializationDemo.
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * EFFECTS: reads menu from file and returns it;
     *          throws IOException if an error occurs reading data from file
     */
    public Menu readMenu() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    /*
     * EFFECTS: reads tabs from file and returns it;
     *          throws IOException if an error occurs reading data from file
     */
    public Tabs readTabs() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTabs(jsonObject);
    }

    /*
     * EFFECTS: reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
    
    /*
     * EFFECTS: parses menu from JSON object and returns it
     */
    private Menu parseMenu(JSONObject jsonObject) {
        Menu menu = new Menu();

        JSONObject menuObject = jsonObject.getJSONObject("MENU");

        addMenuItems(menu, menuObject,1);
        addMenuItems(menu, menuObject,2);
        addMenuItems(menu, menuObject,3);
        addMenuItems(menu, menuObject,4);
        addMenuItems(menu, menuObject,5);
        return menu;
    }

    /*
     * MODIFIES: menu
     * EFFECTS: parses menu items from JSON object and adds them to menu
     */
    private void addMenuItems(Menu menu, JSONObject menuObject, int section) {
        String label = "";
        
        if (section == 1) {
            label = "Appetizers";
        } else if (section == 2) {
            label = "Mains";
        } else if (section == 3) {
            label = "Sides";
        } else if (section == 4) {
            label = "Desserts";
        } else {
            label = "Drinks";
        }

        JSONArray jsonArray = menuObject.getJSONArray(label);
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addMenuItem(menu, nextItem, section);
        }
    }

    /*
     * MODIFIES: menu
     * EFFECTS: parses menu item from JSON object
     *          and adds it to respective menu section
     */
    private void addMenuItem(Menu menu, JSONObject menuObject, int section) {
        ArrayList<MenuItem> fetchList;

        if (section == 1) {
            fetchList = menu.getAppetizers();
        } else if (section == 2) {
            fetchList = menu.getMains();
        } else if (section == 3) {
            fetchList = menu.getSides();
        } else if (section == 4) {
            fetchList = menu.getDesserts();
        } else {
            fetchList = menu.getDrinks();
        }

        String name = menuObject.getString("itemName");
        int price = menuObject.getInt("price");
        MenuItem menuItem = new MenuItem(name, price);
        menu.addItem(fetchList, menuItem);
    }

    /*
     * EFFECTS: parses system from JSON object and returns it
     */
    private Tabs parseTabs(JSONObject jsonObject) {
        Tabs system = new Tabs();

        JSONObject tabsObject = jsonObject.getJSONObject("P.O.S. SYSTEM");

        addTabs(system, tabsObject, 1);
        addTabs(system, tabsObject,2);
        return system;
    }

    /*
     * MODIFIES: system
     * EFFECTS: parses tabs from JSON object and adds them to system
     */
    private void addTabs(Tabs system, JSONObject tabsObject, int section) {
        String label = "";

        if (section == 1) {
            label = "Paid Tabs";
        } else {
            label = "Unpaid Tabs";
        }

        JSONArray jsonArray = tabsObject.getJSONArray(label);
        for (Object json : jsonArray) {
            JSONObject nextTab = (JSONObject) json;
            addTab(system, nextTab, section);
        }
    }

    /*
     * MODIFIES: system
     * EFFECTS: parses tab from JSON object
     *          and adds it to respective list of tabs
     */
    private void addTab(Tabs system, JSONObject tabsObject, int section) {
        ArrayList<OrderSummary> fetchList;

        if (section == 1) {
            fetchList = system.getPaidTabs();
        } else {
            fetchList = system.getUnpaidTabs();
        }

        String              name = tabsObject.getString("name");
        boolean             tabStatus = tabsObject.getBoolean("tabStatus");
        boolean             taxStatus = tabsObject.getBoolean("taxStatus");
        JSONArray           orders = tabsObject.getJSONArray("orders");
        int                 partySize = tabsObject.getInt("partySize");
        int                 tip = tabsObject.getInt("tip");
        int                 totalBill = tabsObject.getInt("totalBill");
        OrderSummary        tab = new OrderSummary(name, partySize);

        tab.setTabStatus(tabStatus);
        tab.setTaxStatus(taxStatus);
        addListOfOrders(tab, orders);
        tab.setTip(tip);
        tab.setTotalBill(totalBill);
        fetchList.add(tab);
    }

    /*
     * MODIFIES: system
     * EFFECTS: parses JSONArray from JSON object
     *          and adds it to tab
     */
    private void addListOfOrders(OrderSummary tab, JSONArray orders) {
        ArrayList<MenuItem> tabsOrders = tab.getOrders();

        for (Object object : orders) {
            JSONObject orderJson = (JSONObject) object;
            String itemName = orderJson.getString("itemName");
            int price = orderJson.getInt("price");
            tabsOrders.add(new MenuItem(itemName, price));
        }
    }
}

