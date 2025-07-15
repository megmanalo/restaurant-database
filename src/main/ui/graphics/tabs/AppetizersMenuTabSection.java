package ui.graphics.tabs;

import javax.swing.*;

import model.MenuItem;
import ui.RestaurantDatabase;

public class AppetizersMenuTabSection extends MenuTabSection {

    /*
     * Creates the menu tab of the application
     */
    public AppetizersMenuTabSection(RestaurantDatabase controller) {
        super(controller);

        createFirstComponent("APPETIZERS", "Appetizer");
        createSecondComponent();

        add(sectionPanel);
    }

    /*
     * EFFECTS: adds menu list to split pane
     */
    protected void createListPanel() {
        super.createListPanel();

        // TO-DO: make abstract function per section of menu tab
        for (MenuItem item : database.getMenu().getAppetizers()) {
            if (!model.contains(item)) {
                model.addElement(item);
            }
        }

        splitPane.setLeftComponent(new JScrollPane(list));
    }

    /*
     * EFFECTS: takes input from text fields and
     *          uses these to create new menu item in database
     */
    protected void createNewMenuItem() {
        super.createNewMenuItem();

        // TO-DO: make abstract function per section of menu tab
        if (!database.getMenu().getAppetizers().contains(newItem)) {
            if (price >= 0) {
                database.getMenu().addItem(database.getMenu().getAppetizers(), newItem);
            }
        }
    }

    /*
     * EFFECTS: deletes current selection from appropriate list
     *          of menu items in the database
     */
    public void deleteMenuItem() {
        // TO-DO: make abstract function per section of menu tab
        database.getMenu().removeItem(database.getMenu().getAppetizers(), list.getSelectedValue());
        model.removeElementAt(list.getSelectedIndex());
    }
}
