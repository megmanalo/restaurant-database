package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Menu;
import model.MenuItem;
import model.OrderSummary;
import model.Tabs;

import persistence.JsonReader;
import persistence.JsonWriter;

// Code below is modelled after `JsonReaderTest` class in `test` package of JsonSerializationDemo.
public class JsonReaderTest extends JsonTest {
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
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonexistentFile.json");
        try {
            Menu menu = reader.readMenu();
            Tabs system = reader.readTabs();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDatabase.json");
        try {
            Menu menu = reader.readMenu();
            Tabs system = reader.readTabs();
            assertEquals(0, menu.getAppetizers().size());
            assertEquals(0, menu.getMains().size());
            assertEquals(0, menu.getSides().size());
            assertEquals(0, menu.getDesserts().size());
            assertEquals(0, menu.getDrinks().size());
            assertEquals(0, system.getPaidTabs().size());
            assertEquals(0, system.getUnpaidTabs().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
        try {
            Menu menu = reader.readMenu();
            Tabs system = reader.readTabs();

            ArrayList<MenuItem> genOrder = new ArrayList<>();
            genOrder.add(appetizer);

            menu = reader.readMenu();
            system = reader.readTabs();
            assertEquals(1, menu.getAppetizers().size());
            assertEquals(1, system.getPaidTabs().size());
            assertEquals(1, system.getUnpaidTabs().size());

            checkTab("Meg", true, true, genOrder, 4, 0, 1120, table1);
            checkTab("Moira", false, false, genOrder, 3, 0, 1000, table2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testJsonAddMenuItem() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
        try {
            Menu menu = reader.readMenu();

            assertEquals(1, menu.getAppetizers().size());
            assertEquals("FocacciaBread&Dip", menu.getAppetizers().get(0).getItemName());

            assertEquals(1000, menu.getAppetizers().get(0).getPrice());
            assertEquals(0, menu.getMains().size());
            assertEquals(0, menu.getSides().size());
            assertEquals(0, menu.getDesserts().size());
            assertEquals(0, menu.getDrinks().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testAddMenuItemForAllSections() {
        JsonReader reader = new JsonReader("./data/testReaderAllMenuSections.json");

        try {
            Menu menu = reader.readMenu();

            assertEquals(1, menu.getAppetizers().size());
            assertEquals("FocacciaBread&Dip", menu.getAppetizers().get(0).getItemName());
            assertEquals(1000, menu.getAppetizers().get(0).getPrice());

            assertEquals(1, menu.getMains().size());
            assertEquals("SpinachRavioli", menu.getMains().get(0).getItemName());
            assertEquals(2500, menu.getMains().get(0).getPrice());

            assertEquals(1, menu.getSides().size());
            assertEquals("GarlicFries", menu.getSides().get(0).getItemName());
            assertEquals(800, menu.getSides().get(0).getPrice());

            assertEquals(1, menu.getDesserts().size());
            assertEquals("ChocolateLavaCake", menu.getDesserts().get(0).getItemName());
            assertEquals(1200, menu.getDesserts().get(0).getPrice());

            assertEquals(1, menu.getDrinks().size());
            assertEquals("Mojito", menu.getDrinks().get(0).getItemName());
            assertEquals(700, menu.getDrinks().get(0).getPrice());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
