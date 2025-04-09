package ui.graphics.tabs;

import javax.swing.*;
import java.awt.event.ActionListener;

import ui.RestaurantDatabase;

import java.awt.*;

// Code below and all its extensions are modelled after 
// - `SmartHomeUI` class in `ui` package of SmartHome;
// - various classes in `ui` package of AlarmSystem;
public abstract class Tab extends JPanel implements ActionListener {
    private final RestaurantDatabase controller;
    protected JLabel menu;
    protected RestaurantDatabase database;
    protected JTabbedPane sidebar;

    /*
     * REQUIRES: Restaurant Database controller that holds this tab
     */
    public Tab(RestaurantDatabase controller) {
        this.controller = controller;
        database = controller;
    }

    /*
     * EFFECTS: creates and returns row with button included
     */
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    /*
     * EFFECTS: returns the Restaurant Database controller for this tab
     */
    public RestaurantDatabase getController() {
        return controller;
    }
}
