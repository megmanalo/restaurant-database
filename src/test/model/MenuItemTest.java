package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

public class MenuItemTest {
    private MenuItem item1;
    
    @BeforeEach
    void runBefore() {
        item1 = new MenuItem("FocacciaBread&Dip", 10);
    }

    @Test
    void testNewMenuItem() {
        assertEquals(item1.getItemName(), "FocacciaBread&Dip");
        assertEquals(item1.getPrice(), 10);

        item1.setItemName("ChickenNuggets");
        item1.setPrice(123);

        assertEquals(item1.getItemName(), "ChickenNuggets");
        assertEquals(item1.getPrice(), 123);
    }

    @Test
    void testToJsonDirectly() {
        JSONObject json = item1.toJson();

        assertEquals(item1.getItemName(), json.getString("Item Name"));
        assertEquals(item1.getPrice(), json.getInt("Price"));
    }

    @Test
    void testToString() {
        assertEquals(item1.toString(), "FocacciaBread&Dip");
    }
}