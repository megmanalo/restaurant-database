package ui.graphics.tabs;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;

import ui.RestaurantDatabase;

public class PointOfSaleTab extends Tab {
    private JLabel msg;

    /*
     * Creates the P.O.S. tab of the application
     */
    public PointOfSaleTab(RestaurantDatabase controller) {
        super(controller);
        loadPointOfSaleButtons();
    }

    /*
     * EFFECTS: sets up buttons for P.O.S. System tab of application
     */
    private void loadPointOfSaleButtons() {
        msg = new JLabel("Work in progress...");

        add(msg);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
    }

    /*
     * EFFECTS: assigns functions per button
     */
    public void actionPerformed(ActionEvent e) {
        // if (e.getActionCommand().equals("...")) {
        // }
    }
}
