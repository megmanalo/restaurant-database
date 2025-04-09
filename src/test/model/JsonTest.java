package model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Succintly tests all attributes of a class
// Code below is modelled after `JsonTest` class in `persistance` package of JsonSerializationDemo.
public class JsonTest {
    protected void checkMenuItem(String itemName, int price, MenuItem menuItem) {
        assertEquals(itemName, menuItem.getItemName());
        assertEquals(price, menuItem.getPrice());
    }

    protected void checkTab(String name, boolean tabStatus, boolean taxStatus,
                            ArrayList<MenuItem> orders, int partySize, int tip,
                            int totalBill, OrderSummary tab) {
        assertEquals(name, tab.getName());
        assertEquals(tabStatus, tab.getTabStatus());
        assertEquals(taxStatus, tab.getTaxStatus());

        for (MenuItem order : orders) {
            checkMenuItem(order.getItemName(), order.getPrice(), order);
        }

        assertEquals(partySize, tab.getPartySize());
        assertEquals(tip, tab.getTip());
        assertEquals(totalBill, tab.getTotalBill());
    }
}
