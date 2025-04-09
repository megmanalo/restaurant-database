package model;

import java.io.IOException;

import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

// Code below is modelled after `JsonWriterTest` class in `test` package of JsonSerializationDemo.
public class JsonWriterTest extends JsonTest {
    MenuItem appetizer;
    OrderSummary table1;
    OrderSummary table2;

    @BeforeEach
    void runBefore() {
        appetizer = new MenuItem("FocacciaBread&Dip", 1000);
        
        table1 = new OrderSummary("Meg", 4);
        table1.addOrder(appetizer);
        table1.processOrder();
        table1.setTabStatus(true);

        table2 = new OrderSummary("Moira", 3);
        table2.addOrder(appetizer);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Menu menu = new Menu();
            Tabs system = new Tabs();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDatabase() {
        try {
            Menu menu = new Menu();
            Tabs system = new Tabs();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDatabase.json");
            writer.open();
            writer.write(menu, system);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDatabase.json");
            menu = reader.readMenu();
            system = reader.readTabs();
            assertEquals(0, menu.getAppetizers().size());
            assertEquals(0, menu.getMains().size());
            assertEquals(0, menu.getSides().size());
            assertEquals(0, menu.getDesserts().size());
            assertEquals(0, menu.getDrinks().size());
            assertEquals(0, system.getPaidTabs().size());
            assertEquals(0, system.getUnpaidTabs().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ArrayList<MenuItem> genOrder = new ArrayList<>();
            genOrder.add(appetizer);

            Menu menu = new Menu();
            menu.addItem(menu.getAppetizers(), appetizer);

            Tabs system = new Tabs();
            system.getPaidTabs().add(table1);
            system.getUnpaidTabs().add(table2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDatabase.json");
            writer.open();
            writer.write(menu, system);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDatabase.json");
            menu = reader.readMenu();
            system = reader.readTabs();
            assertEquals(1, menu.getAppetizers().size());
            assertEquals(1, system.getPaidTabs().size());
            assertEquals(1, system.getUnpaidTabs().size());

            checkTab("Meg", true, true, genOrder, 4, 0, 1120, table1);
            checkTab("Moira", false, false, genOrder, 3, 0, 1000, table2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
