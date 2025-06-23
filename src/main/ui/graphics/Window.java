package ui.graphics;

import javax.swing.*;

import model.EventLog;
import model.Event;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;

import ui.graphics.tabs.*;
import ui.RestaurantDatabase;

// Code below is modelled after `DrawingEditor` class in `tools` package of SimpleDrawingPlayer-Complete;
public class Window extends JFrame implements WindowListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int HOME_TAB_INDEX = 0;
    public static final int MENU_TAB_INDEX = 1;
    public static final int POS_TAB_INDEX = 2;

    private RestaurantDatabase database;
    private JTabbedPane sidebar;

    /*
     * Creates the main window of the application
     */
    public Window(RestaurantDatabase database) {
        super("Restaurant Database");
        this.database = database;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        initializeGraphics();
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the JFrame window where this DrawingEditor will operate,
     * and populates the tools to be used to manipulate this drawing
     */
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        loadTabs();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up sidebar of application
     */
    private void loadTabs() {
        JPanel homeTab = new HomeTab(database);
        JPanel menuTab = new AppetizersMenuTab(database);
        JPanel posTab = new PointOfSaleTab(database);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(menuTab, MENU_TAB_INDEX);
        sidebar.setTitleAt(MENU_TAB_INDEX, "Menu");
        sidebar.add(posTab, POS_TAB_INDEX);
        sidebar.setTitleAt(POS_TAB_INDEX, "P.O.S. System");

        add(sidebar);
    }

    /*
     * EFFECTS: returns sidebar of application
     */
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event + "\n");
            }
        } catch (Exception exception) {
            // do nothing
        }

        System.exit(1);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
