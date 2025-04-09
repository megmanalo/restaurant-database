package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;
import persistence.JsonWriter;

public class OrderSummaryTest extends JsonTest {
    private OrderSummary table1;
    private OrderSummary table2;
    private MenuItem item1;
    private MenuItem item2;

    @BeforeEach
    void runBefore() {
        item1 = new MenuItem("FocacciaBread&Dip", 1000);
        item2 = new MenuItem("SpinachRavioli", 2500);
        table1 = new OrderSummary("Meg", 4);
        table2 = new OrderSummary("Moira", 8);
    }

    @Test
    void testNewOrderSummary() {
        assertFalse(table1.getTabStatus());
        assertFalse(table1.getTaxStatus());
        assertEquals(table1.getOrders().size(), 0);
        assertEquals(table1.getPartySize(), 4);
        assertEquals(table1.getTip(), 0);
        assertEquals(table1.getTotalBill(), 0);
        assertEquals(table1.getName(), "Meg");
    }

    @Test
    void testAddOneOrder() {
        table1.addOrder(item1);
        assertEquals(table1.getOrders().size(), 1);
        assertEquals(table1.getTotalBill(), 1000);
    }

    @Test
    void testAddTwoOrders() {
        table1.addOrder(item1);
        table1.addOrder(item2);
        assertEquals(table1.getOrders().size(), 2);
        assertEquals(table1.getTotalBill(), 3500);
    }

    @Test
    void testAddSameOrderToTwoTabs() {
        table1.addOrder(item1);
        table2.addOrder(item1);
        assertTrue(table1.getOrders().equals(table2.getOrders()));
    }

    @Test
    void testAddTipOnce() {
        table1.addOrder(item1);
        table1.addTip(15);
        assertEquals(table1.getTip(), 15);
    }

    @Test
    void testAddTipTwice() {
        table1.addOrder(item1);
        table1.addTip(15);
        table1.addTip(5);
        assertEquals(table1.getTip(), 20);
    }

    @Test
    void testProcessOrderUnderPartyCutoff() {
        table1.addOrder(item1);
        table1.addTip(25);
        assertEquals(1370, table1.processOrder());
    }

    @Test
    void testProcessOrderOverPartyCutoff() {
        table2.addOrder(item1);
        table2.addTip(10);
        assertEquals(1400, table2.processOrder());
    }

    @Test
    void testUnderPayTab() {
        table1.addOrder(item1);
        assertEquals(0, table1.payTab(1100));
        assertFalse(table1.getTabStatus());
    }

    @Test
    void testPayTabFullyInParts() {
        table1.addOrder(item1);
        assertEquals(0, table1.payTab(700));
        assertEquals(0, table1.payTab(420));
        assertTrue(table1.getTabStatus());
    }

    @Test
    void testPayTabFullyOnce() {
        table1.addOrder(item1);
        assertEquals(0, table1.payTab(1120));
        assertTrue(table1.getTabStatus());
    }

    @Test
    void testPayTabWithChange() {
        table1.addOrder(item1);
        assertEquals(80, table1.payTab(1200));
        assertTrue(table1.getTabStatus());
    }

    @Test
    void testProcessTabTwice() {
        table1.addOrder(item1);
        assertEquals(table1.processOrder(), 1120);
        assertEquals(table1.processOrder(), 1120);
    }

    @Test
    void testToJson() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
            Tabs system = reader.readTabs();

            ArrayList<MenuItem> genOrder = new ArrayList<>();
            genOrder.add(item1);

            system = reader.readTabs();
            assertEquals(1, system.getPaidTabs().size());
            assertEquals(1, system.getUnpaidTabs().size());

            checkTab("Moira", false, false, genOrder,
                    3, 0, 1000, system.getUnpaidTabs().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testToJsonDirectly() {
        table1.addOrder(item1);
        table1.addTip(20);
        table1.processOrder();

        JSONObject json = table1.toJson();

        assertEquals("Meg", table1.getName());
        assertEquals(table1.getTabStatus(), json.getBoolean("Tab Status"));
        assertEquals(table1.getTaxStatus(), json.getBoolean("Tax Status"));

        JSONArray ordersJson = json.getJSONArray("Orders");
        assertEquals(1, ordersJson.length());

        JSONObject firstOrder = ordersJson.getJSONObject(0);
        assertEquals("FocacciaBread&Dip", firstOrder.getString("itemName"));
        assertEquals(1000, firstOrder.getInt("price"));

        assertEquals(table1.getPartySize(), json.getInt("Party Size"));
        assertEquals(table1.getTip(), json.getInt("Tip"));
        assertEquals(table1.getTotalBill(), json.getInt("Total Bill"));
    }
}