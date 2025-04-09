package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.*;
import ui.graphics.*;

// Management system for all of the restaurant's tabs.
public class RestaurantDatabase {
    private static final int PARTY_CUTOFF = 6;
    private static final int CUTOFF_GRATUITY = 18;
    private static final int GST = 5;
    private static final int PST = 7;
    private static final int BACK_BUTTON = 0;
    private static final String JSON_HOME = "./data/restaurantDatabase.json";

    private Scanner input;
    private Tabs system = new Tabs();
    private Menu menu = new Menu();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Window window;

    /*
     * Creates the restaurant database of the application
     */
    public RestaurantDatabase() {
        jsonWriter = new JsonWriter(JSON_HOME);
        jsonReader = new JsonReader(JSON_HOME);
        window = new Window(this);
        databaseOpening();
    }

    /*
     * EFFECTS: opens initial window for user interaction
     */
    public void databaseOpening() {
        input = new Scanner(System.in);
        System.out.println("\nWelcome!");
        System.out.println("\n\tType 1 to access the menu.");
        System.out.println("\tType 2 to access P.O.S. system.");
        System.out.println("\tType 3 to save current database to file.");
        System.out.println("\tType 4 to load exisiting database from file.");
        System.out.println("\tType " + BACK_BUTTON + " to quit the application.");

        try {
            int choice = input.nextInt();

            if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == BACK_BUTTON) {
                handleOpeningChoice(choice, system, menu);
            } else {
                System.out.println("\nPlease type a valid option.");
                databaseOpening();
            }
        } catch (InputMismatchException e) {
            System.out.println("\nPlease type a valid option.");
            databaseOpening();
        }
    }

    /*
     * EFFECTS: manages user choice to either:
     *          1 - be redirected to the menu
     *          2 - be redirected to P.O.S. System
     *          3 - save restaurant database to file
     *          4 - load restaurant database from file
     *          BACK_BUTTON - quit the application
     */
    public void handleOpeningChoice(int choice, Tabs system, Menu menu) {
        if (choice == 1) {
            System.out.println("\nRedirected to MENU:");
            System.out.println("\n\tType 1 to view current menu.");
            System.out.println("\tType 2 to update the menu.");
            System.out.println("\tType " + BACK_BUTTON + " to return to main menu.");
            handleRedirectionToMenu();

        } else if (choice == 2) {
            System.out.println("\nRedirected to P.O.S. System:");
            System.out.println("\n\tType 1 to create a new open tab.");
            System.out.println("\tType 2 to view list of unpaid tabs.");
            System.out.println("\tType 3 to view list of paid tabs.");
            System.out.println("\tType " + BACK_BUTTON + " to return to main menu.");
            handleRedirectionToSystem();

        } else if (choice == 3) {
            saveDatabase();

        } else if (choice == 4) {
            loadDatabase();

        } else {
            System.out.println("\nSystem shutting down...");
            System.exit(0);
        }
    }

    /*
     * EFFECTS: handles redirection to menu options
     */
    public void handleRedirectionToMenu() {
        try {
            int option = input.nextInt();

            if (option == 1 || option == 2 || option == BACK_BUTTON) {
                handleMenuSystem(option, system, menu);
            } else {
                invalidInput();
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: handles redirection to P.O.S. System options
     */
    public void handleRedirectionToSystem() {
        try {
            int option = input.nextInt();

            if (option == 1 || option == 2 || option == 3 || option == BACK_BUTTON) {
                handleOrderSummaries(option, system, menu);
            } else {
                invalidInput();
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: manages user choice for:
     *          1 - viewing the menu;
     *          2 - updating the menu; and
     *          BACK_BUTTON - returning to the main menu
     */
    public void handleMenuSystem(int option, Tabs system, Menu menu) {
        if (option == 1) {
            printMenu(menu);
            handleOpeningChoice(1, system, menu);

        } else if (option == 2) {
            System.out.println("\nUPDATE MENU:");
            System.out.println("\n\tType 1 to add a new menu item.");
            System.out.println("\tType 2 to remove a current menu item.");
            System.out.println("\tType " + BACK_BUTTON + " to go back.");

            try {
                int choice = input.nextInt();

                if (option == 1 || option == 2 || option == BACK_BUTTON) {
                    handleMenuUpdate(choice, system, menu);
                } else {
                    invalidInput();
                }
            } catch (InputMismatchException e) {
                invalidInput();
            }
        } else {
            databaseOpening();
        }
    }

    /*
     * EFFECTS: manages user choice for:
     *          1 - creating a new open tab;
     *          2 - viewing unpaid tabs;
     *          3 - viewing paid tabs; and
     *          BACK_BUTTON - returning to the main menu
     */
    public void handleOrderSummaries(int option, Tabs system, Menu menu) {
        if (option == 1) {
            try {
                System.out.println("\nPlease indicate name for reservation (One name/No space):");
                String name = input.next();
                System.out.println("\nPlease indicate party size:");
                int size = input.nextInt();

                if (size >= PARTY_CUTOFF) {
                    System.out.println("\nAutomatic " + CUTOFF_GRATUITY + "% gratuity applied to tab.");
                }

                OrderSummary newTab = new OrderSummary(name, size);
                system.organizeTab(newTab);
                handleOrderSummary(newTab, menu, system);
            } catch (InputMismatchException e) {
                invalidInput();
            }

        } else if (option == 2) {
            handleTabs(2, system, menu);
        } else if (option == 3) {
            handleTabs(3, system, menu);
        } else {
            databaseOpening();
        }
    }

    /*
     * EFFECTS: manages user choice for:
     *          1 - making a new menu item;
     *          2 - removing a menu item; and
     *          BACK_BUTTON - returning to the menu options
     */
    public void handleMenuUpdate(int option, Tabs system, Menu menu) {
        try {
            if (option == 1) {
                printMenuSections();
                int type = input.nextInt();

                if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5) {
                    makeNewMenuItem(type, menu);
                } else {
                    invalidInput();
                }
            } else if (option == 2) {
                printMenuSections();
                int section = input.nextInt();

                if (section == 1 || section == 2 || section == 3 || section == 4 || section == 5) {
                    removeMenuItem(section, menu);
                } else {
                    invalidInput();
                }
            } else {
                handleOpeningChoice(1, system, menu);
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: presents user with options for handling a tab
     */
    public void handleOrderSummary(OrderSummary tab, Menu menu, Tabs system) {
        System.out.println("\nTAB: " + tab.getName() + " for " + tab.getPartySize());
        System.out.println("\n\tType 1 to add a new order.");
        System.out.println("\tType 2 to specify tip percentage.");
        System.out.println("\tType 3 to proceed with payment.");
        System.out.println("\tType " + BACK_BUTTON + " to go back.");

        try {
            int choice = input.nextInt();

            if (choice == 1 || choice == 2 || choice == 3 || choice == BACK_BUTTON) {
                handleCurrentTab(choice, tab, menu, system);
            } else {
                invalidInput();
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: manages user choice for:
     *          2 - viewing unpaid tabs; and
     *          3 - viewing paid tabs;
     */
    public void handleTabs(int option, Tabs system, Menu menu) {
        if (option == 2) {
            printUnpaidTabs(system);

            System.out.println("\nWould you like to view a tab? (Y/N)");
            String choice1 = input.next();

            try {
                if (choice1.equals("N") || choice1.equals("Y")) {
                    handleUnpaidTabs(choice1);
                } else {
                    invalidInput();
                }
            } catch (InputMismatchException e) {
                invalidInput();
            }
        } else {
            printPaidTabs(system);
            handleOpeningChoice(2, system, menu);
        }
    }

    /*
     * EFFECTS: manages options for viewing unpaid tabs
     */
    public void handleUnpaidTabs(String choice1) {
        if (choice1.equals("N")) {
            handleOpeningChoice(2, system, menu);
        } else {
            try {
                System.out.println("\nTo select tab, please provide...\nName of Reservation:");
                String choice2 = input.next();
                System.out.println("\nParty Size:");
                int size = input.nextInt();
                OrderSummary chosenTab = null;

                for (OrderSummary tab : system.getUnpaidTabs()) {
                    if (choice2.equals(tab.getName()) && size == (tab.getPartySize())) {
                        chosenTab = tab;
                    }
                }

                handleUnpaidTabOfChoice(chosenTab);
                
            } catch (InputMismatchException e) {
                invalidInput();
            }
        }
    }

    /*
     * EFFECTS: handles retrieved tab according to user's choice
     */
    public void handleUnpaidTabOfChoice(OrderSummary chosenTab) {
        if (chosenTab == null) {
            System.out.println("\nReservation not found.\nRedirecting to P.O.S. System...");
            handleOpeningChoice(2, system, menu);
        } else {
            handleOrderSummary(chosenTab, menu, system);
        }

    }

    /*
     * EFFECTS: manages user choice for:
     *          1 - adding a menu item to tab's order;
     *          2 - adding a tip amount for tab;
     *          3 - proceeding with payment for tab; and
     *          BACK_BUTTON - returning to P.O.S. System
     */
    public void handleCurrentTab(int choice, OrderSummary tab, Menu menu, Tabs system) {
        if (choice == 1) {
            handleAddOrder(menu, tab);

        } else if (choice == 2) {
            handleAddTip(tab, menu, system);

        } else if (choice == 3) {
            handleTabPayment(tab, menu, system);

        } else {
            handleOpeningChoice(2, system, menu);
        }
    }

    /*
     * EFFECTS: handles adding an order to a tab
     */
    public void handleAddOrder(Menu menu, OrderSummary tab) {
        printMenuSections();

        try {
            int section = input.nextInt();

            if (section == 1 || section == 2 || section == 3 || section == 4 || section == 5) {
                addNewOrderToTab(menu, section, tab);
            } else {
                invalidInput();
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: handles adding to a tab's tip
     */
    public void handleAddTip(OrderSummary tab, Menu menu, Tabs system) {
        System.out.println("\nInput tip amount (__%):");

        try {
            int tip = input.nextInt();

            if (tip < 0) {
                invalidInput();
            } else {
                tab.addTip(tip);
                System.out.println("\n" + tip + "% tip added to tab.");
                handleOrderSummary(tab, menu, system);
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: handles payment for a tab
     */
    public void handleTabPayment(OrderSummary tab, Menu menu, Tabs system) {
        try {
            int finalTotalAfterTax = tab.processOrder();

            System.out.println("\nApplied:\n\tPST " + PST + "%\n\tGST " + GST + "%");
            System.out.println("\nPENDING BILL: " + finalTotalAfterTax + " cents");
            System.out.println("\nInput amount to pay (in cents):");
            int payment = input.nextInt();

            if (payment < 0) {
                invalidInput();
            } else if (payment >= finalTotalAfterTax) {
                System.out.println("\nReceived: " + payment + " | Change: " + tab.payTab(payment));
                system.getUnpaidTabs().remove(tab);
                system.getPaidTabs().add(tab);
                System.out.println("\nAdded to list of paid tabs.");
                defaultMenu();
            } else {
                System.out.println("\nInsufficient payment.");
                handleOrderSummary(tab, menu, system);
            }

        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: handles making of new menu item
     */
    public void makeNewMenuItem(int type, Menu menu) {
        try {
            System.out.println("\nMake new menu item? (Y/N)");
            String choice1 = input.next();

            if (choice1.equals("N")) {
                handleMenuSystem(2, system, menu);
            } else if (choice1.equals("Y")) {
                handleNewMenuItem(type);

            } else {
                invalidInput();
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * MODIFIES: menu
     * EFFECTS: makes a new menu item
     */
    public void handleNewMenuItem(int type) {
        System.out.println("\nInput new item name (One word/No space):");
        String name = input.next();
        MenuItem toRemove = new MenuItem("x", 0);
        System.out.println("Input new item price (in cents):");

        int price = input.nextInt();

        if (price < 0) {
            invalidInput();
        } else {
            MenuItem newItem = new MenuItem(name, price);

            if (type == 1) {
                menu.addItem(menu.getAppetizers(), newItem);
            } else if (type == 2) {
                menu.addItem(menu.getMains(), newItem);
            } else if (type == 3) {
                menu.addItem(menu.getSides(), newItem);
            } else if (type == 4) {
                menu.addItem(menu.getDesserts(), newItem);
            } else {
                menu.addItem(menu.getDrinks(), newItem);
            }
        }

        System.out.println("\nSuccess! New item has been added to menu.");
        handleMenuSystem(2, system, menu);
    }

    /*
     * EFFECTS: handles removal of menu item
     */
    public void removeMenuItem(int section, Menu menu) {
        ArrayList<MenuItem> menuSection = menu.getAppetizers();

        if (section == 1) {
            menuSection = menu.getAppetizers();
        } else if (section == 2) {
            menuSection = menu.getMains();
        } else if (section == 3) {
            menuSection = menu.getSides();
        } else if (section == 4) {
            menuSection = menu.getDesserts();
        } else {
            menuSection = menu.getDrinks();
        }

        printMenuSection(menu, section);

        try {
            handleRemoveMenuItem(menuSection);
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * EFFECTS: handles decision to remove a current menu item
     */
    public void handleRemoveMenuItem(ArrayList<MenuItem> menuSection) {
        System.out.println("\nRemove a current menu item? (Y/N)");
        String choice1 = input.next();

        if (choice1.equals("N") || choice1.equals("Y")) {
            if (choice1.equals("N")) {
                handleMenuUpdate(2, system, menu);
            } else {
                handleRemoveMenuItemFinal(menuSection);
            }
        }
    }

    /*
     * MODIFIES: menu
     * EFFECTS: removes a current menu item
     */
    public void handleRemoveMenuItemFinal(ArrayList<MenuItem> menuSection) {
        System.out.println("Input item name to be removed from menu:");
        String item = input.next();
        MenuItem toRemove = new MenuItem("x", 0);
        boolean exists = false;

        for (MenuItem menuItem : menuSection) {
            if (menuItem.getItemName().equals(item)) {
                exists = true;
            }
        }

        if (exists = false) {
            System.out.println("\nMenu Item does not exist.");
            handleRemoveMenuItem(menuSection);
        } else {
            for (MenuItem menuItem : menuSection) {
                if (menuItem.getItemName().equals(item)) {
                    toRemove = menuItem;
                    System.out.println("\nSuccess! Item removed from menu.");
                }
            }
        }

        menuSection.remove(toRemove);
        handleMenuSystem(2, system, menu);
    }

    /*
     * EFFECTS: handles the addition of a new order to the tab
     */
    public void addNewOrderToTab(Menu menu, int section, OrderSummary tab) {
        ArrayList<MenuItem> menuSection = menu.getAppetizers();

        if (section == 1) {
            menuSection = menu.getAppetizers();
        } else if (section == 2) {
            menuSection = menu.getMains();
        } else if (section == 3) {
            menuSection = menu.getSides();
        } else if (section == 4) {
            menuSection = menu.getDesserts();
        } else {
            menuSection = menu.getDrinks();
        }

        printMenuSection(menu, section);

        try {
            continueAddOrderToTab(menuSection, tab);
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    /*
     * MODIFIES: tab
     * EFFECTS: adds a new order to the tab if its exists on the menu
     */
    public void continueAddOrderToTab(ArrayList<MenuItem> menuSection, OrderSummary tab) {
        System.out.println("\nADD ORDER:");
        System.out.println("\nMenu Item Name:");
        String order = input.next();

        MenuItem newOrder = null;

        for (MenuItem menuItem : menuSection) {
            if (menuItem.getItemName().equals(order)) {
                newOrder = menuItem;
            }
        }

        if (newOrder == null) {
            System.out.println("\nMenu Item does not exist.");
            System.out.println("Returning to tab menu...");
        } else {
            tab.addOrder(newOrder);
            System.out.println("\nSuccesfully added " + newOrder.getItemName() + " to order.");
        }
        handleOrderSummary(tab, menu, system);
    }

    /*
     * EFFECTS: presents user with default menu options
     */
    public void defaultMenu() {
        System.out.println("\n\tType 'home' to return to main menu.");
        String choice = input.next();

        if (choice.equals("home")) {
            databaseOpening();
        } else {
            invalidInput();
            // System.out.println("\nSystem shutting down...");
            // System.exit(0);
        }
    }

    /*
     * EFFECTS: gracefully shuts down application
     */
    public void invalidInput() {
        System.out.println("\nInvalid input. System shutting down...");
        System.exit(0);
    }

    /*
     * EFFECTS: prints entire menu
     */
    public void printMenu(Menu menu) {
        printMenuSection(menu, 1);
        printMenuSection(menu, 2);
        printMenuSection(menu, 3);
        printMenuSection(menu, 4);
        printMenuSection(menu, 5);
    }

    /*
     * EFFECTS: prints menu section according to choice
     */
    public void printMenuSection(Menu menu, int choice) {
        String label;
        ArrayList<MenuItem> section;

        if (choice == 1) {
            label = "\nAPPETIZERS:";
            section = menu.getAppetizers();
        } else if (choice == 2) {
            label = "\nMAINS:";
            section = menu.getMains();
        } else if (choice == 3) {
            label = "\nSIDES:";
            section = menu.getSides();
        } else if (choice == 4) {
            label = "\nDESSERTS:";
            section = menu.getDesserts();
        } else {
            label = "\nDRINKS:";
            section = menu.getDrinks();
        }

        System.out.println(label);

        for (MenuItem item : section) {
            System.out.println(item.getItemName());
        }
    }

    /*
     * EFFECTS: prints sections of menu for user to browse
     */
    public void printMenuSections() {
        System.out.println("\nSelect menu section:\n");
        System.out.println("\t1 - Appetizer");
        System.out.println("\t2 - Main");
        System.out.println("\t3 - Side");
        System.out.println("\t4 - Dessert");
        System.out.println("\t5 - Drink");
    }

    /*
     * EFFECTS: prints list of unpaid tabs
     */
    public void printUnpaidTabs(Tabs system) {
        System.out.println("\nUNPAID TABS:");
        for (OrderSummary tab : system.getUnpaidTabs()) {
            System.out.println(tab.getName() + " for " + tab.getPartySize());
        }
    }

    /*
     * EFFECTS: prints list of paid tabs
     */
    public void printPaidTabs(Tabs system) {
        System.out.println("\nPAID TABS:");
        for (OrderSummary tab : system.getPaidTabs()) {
            System.out.println(tab.getName() + " for " + tab.getPartySize());
        }
    }

    /*
     * EFFECTS: creates Json object for the restaurant database
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("MENU", menu);
        json.put("P.O.S. SYSTEM", system);
        return json;
    }

    /*
     * EFFECTS: saves the restaurant database to a file
     */
    public void saveDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu, system);
            jsonWriter.close();
            System.out.println("\nSaved current restaurant database to " + JSON_HOME);
            databaseOpening();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_HOME);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads the restaurant database from a file
     */
    public void loadDatabase() {
        try {
            system = jsonReader.readTabs();
            menu = jsonReader.readMenu();
            System.out.println("\nLoaded restaurant database from " + JSON_HOME);
            databaseOpening();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_HOME);
        }
    }

    public Tabs getSystem() {
        return system;
    }

    public Menu getMenu() {
        return menu;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public void setSystem(Tabs system) {
        this.system = system;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setJsonWriter(JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }
}
