package ui.graphics.tabs.menuTab;

import javax.swing.JScrollPane;

import model.MenuItem;
import ui.RestaurantDatabase;

public class MainsMenuTabSection extends MenuTabSection {

    /*
     * Creates the mains section of the menu tab
     */
    public MainsMenuTabSection(RestaurantDatabase controller) {
        super(controller);
        
        createFirstComponent("MAINS", "Main");
        createSecondComponent();

        add(sectionPanel);
    }

    /*
     * EFFECTS: adds menu list to split pane
     */
    protected void createListPanel() {
        super.createListPanel();

        // TO-DO: make abstract function per section of menu tab
        for (MenuItem item : database.getMenu().getMains()) {
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
        if (!database.getMenu().getMains().contains(newItem)) {
            if (price >= 0) {
                database.getMenu().addItem(database.getMenu().getMains(), newItem);
            }
        }
    }

    /*
     * EFFECTS: deletes current selection from appropriate list
     *          of menu items in the database
     */
    public void deleteMenuItem() {
        // TO-DO: make abstract function per section of menu tab
        database.getMenu().removeItem(database.getMenu().getMains(), list.getSelectedValue());
        model.removeElementAt(list.getSelectedIndex());
    }
}
