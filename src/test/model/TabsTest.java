package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabsTest {
    private Tabs         system;
    private OrderSummary tab1;
    private OrderSummary tab2;
    
    @BeforeEach
    void runBefore() {
        system = new Tabs();
        tab1 = new OrderSummary("Meg", 5);
        tab2 = new OrderSummary("Moira", 7);
    }

    @Test
    void testBrandNewSystem() {
        assertEquals(system.getPaidTabs().size(), 0);
        assertEquals(system.getUnpaidTabs().size(), 0);
    }

    @Test
    void testOrganizeUnpaidTab() {
        system.organizeTab(tab1);
        assertEquals(system.getPaidTabs().size(), 0);
        assertEquals(system.getUnpaidTabs().size(), 1);
        assertEquals(system.getUnpaidTabs().get(0), tab1);
    }

    @Test
    void testOrganizePaidTab() {
        tab2.setTabStatus(true);
        system.organizeTab(tab2);
        assertEquals(system.getUnpaidTabs().size(), 0);
        assertEquals(system.getPaidTabs().size(), 1);
        assertEquals(system.getPaidTabs().get(0), tab2);
    }

    @Test
    void testOrganizeSameTabTwice() {
        system.organizeTab(tab1);
        system.organizeTab(tab1);
        assertEquals(system.getPaidTabs().size(), 0);
        assertEquals(system.getUnpaidTabs().size(), 1);
        assertEquals(system.getUnpaidTabs().get(0), tab1);
    }

    @Test
    void testOrganizeTwoPaidTabs() {
        tab1.setTabStatus(true);
        tab2.setTabStatus(true);
        system.organizeTab(tab1);
        system.organizeTab(tab2);
        assertEquals(system.getPaidTabs().size(), 2);
        assertEquals(system.getUnpaidTabs().size(), 0);
        assertEquals(system.getPaidTabs().get(1), tab2);
    }

    @Test
    void testOrganizeTwoUnpaidTabs() {
        system.organizeTab(tab1);
        system.organizeTab(tab2);
        assertEquals(system.getPaidTabs().size(), 0);
        assertEquals(system.getUnpaidTabs().size(), 2);
        assertEquals(system.getUnpaidTabs().get(1), tab2);
    }

    @Test
    void testOrganizeAlreadyOrganizedPaidTab() {
        tab1.setTabStatus(true);
        system.organizeTab(tab1);
        system.organizeTab(tab1);
        assertEquals(system.getPaidTabs().size(), 1);
    }
}