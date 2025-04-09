package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest {
    private Menu     menu;
    private MenuItem item1;
    private MenuItem item2;
    private MenuItem item3;
    
    @BeforeEach
    void runBefore() {
        menu = new Menu();
        item1 = new MenuItem("FocacciaBread&Dip", 10);
        item2 = new MenuItem("SpinachRavioli", 25);
        item3 = new MenuItem("TruffleFries", 13);
    }

    @Test
    void testNewMenu() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertEquals(menu.getMains().size(), 0);
        assertEquals(menu.getSides().size(), 0);
        assertEquals(menu.getDesserts().size(), 0);
        assertEquals(menu.getDrinks().size(), 0);
    }

    @Test
    void testAddOneToList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
    }

    @Test
    void testAddTwoDiffToList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
        assertTrue(menu.addItem(menu.getAppetizers(), item2));
        assertEquals(menu.getAppetizers().size(), 2);
    }

    @Test
    void testAddTwoSameToList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
        assertFalse(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
    }

    @Test
    void testRemoveOneAndOnlyItemInList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
        assertTrue(menu.removeItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 0);
    }

    @Test
    void testRemoveItemTwoDownInList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertTrue(menu.addItem(menu.getAppetizers(), item2));
        assertEquals(menu.getAppetizers().size(), 2);
        assertTrue(menu.removeItem(menu.getAppetizers(), item2));
        assertEquals(menu.getAppetizers().size(), 1);
    }

    @Test
    void testRemoveNonExistantItemFromList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertEquals(menu.getAppetizers().size(), 1);
        assertFalse(menu.removeItem(menu.getAppetizers(), item2));
        assertEquals(menu.getAppetizers().size(), 1);
    }

    @Test
    void testSearchForNonexistantItemTwoDownInList() {
        assertEquals(menu.getAppetizers().size(), 0);
        assertTrue(menu.addItem(menu.getAppetizers(), item1));
        assertTrue(menu.addItem(menu.getAppetizers(), item2));
        assertEquals(menu.getAppetizers().size(), 2);
        assertFalse(menu.removeItem(menu.getAppetizers(), item3));
        assertEquals(menu.getAppetizers().size(), 2);
    }

}
