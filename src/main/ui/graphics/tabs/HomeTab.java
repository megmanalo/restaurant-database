package ui.graphics.tabs;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ui.RestaurantDatabase;

public class HomeTab extends Tab {
    public static final String JSON_HOME = "./data/restaurantDatabase.json";

    private JLabel label;
    private JButton saveBtn;
    private JButton loadBtn;

    /*
     * Creates the home tab of the application
     */
    public HomeTab(RestaurantDatabase controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        try {
            BufferedImage myPicture = ImageIO.read(new File("./images/logo.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);
        } catch (IOException e) {
            // do not print image
        }

        loadHomeButtons();
    }

    /*
     * EFFECTS: sets up buttons for home tab of application
     * with options to either
     * - save the current database; or
     * - load a previous database
     */
    private void loadHomeButtons() {
        label = new JLabel("Welcome to the Restaurant Database!", SwingConstants.CENTER);

        saveBtn = new JButton("Save Current Database");
        loadBtn = new JButton("Load Previous Database");

        saveBtn.setActionCommand("Save");
        saveBtn.addActionListener(this);
        loadBtn.setActionCommand("Load");
        loadBtn.addActionListener(this);

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);

        add(label);
        add(btnPanel);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
    }

    /*
     * EFFECTS: assigns functions per button
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            try {
                database.getJsonWriter().open();
                database.getJsonWriter().write(database.getMenu(), database.getSystem());
                database.getJsonWriter().close();
                System.out.println("\nSaved current restaurant database to " + JSON_HOME);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_HOME);
            }
        } else {
            try {
                database.setSystem(database.getJsonReader().readTabs());
                database.setMenu(database.getJsonReader().readMenu());
                System.out.println("\nLoaded restaurant database from " + JSON_HOME);
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_HOME);
            }
        }

        revalidate();
        repaint();
    }
}
