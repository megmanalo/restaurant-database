package ui.graphics.tabs.pointOfSaleTab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.MenuItem;
import model.OrderSummary;
import ui.RestaurantDatabase;
import ui.graphics.tabs.Tab;

public class UpdateTab extends Tab {
    private JPanel overall;
    private JLabel label;

    private DefaultListModel<String> menuModel;
    private JList<String> menuList;
    private DefaultListModel<MenuItem> menuListModel;
    private JList<MenuItem> menuListList;

    private DefaultListModel<MenuItem> ordersModel;
    private JList<MenuItem> ordersList;

    private JSplitPane menuDisplayPanel;

    /*
     * The interactive section of the Point Of Sale Tab,
     * displaying a selected order summary and
     * allowing for edits to be made to its components.
     */
    public UpdateTab(RestaurantDatabase controller, OrderSummary tab) {
        super(controller);

        overall = new JPanel();
        overall.setLayout(new BoxLayout(overall, BoxLayout.PAGE_AXIS));

        label = new JLabel("Update Tab: " + tab.getName() + " for " + tab.getPartySize());
        overall.add(label);
        overall.add(new JPanel()); // filler
        overall.add(new JPanel()); // filler

        createMenuDisplayPanel();
        // createOrdersPanel(); !!! TO-DO
        // createTipPanel(); !!! TO-DO
        // createPaymentPanel(); !!! TO-DO

        add(overall);
    }

    /*
     * EFFECTS: creates display of current menu items and tab orders
     */
    private void createMenuDisplayPanel() {
        menuDisplayPanel = new JSplitPane();
        menuDisplayPanel.setPreferredSize(new Dimension(250, 75));
        menuDisplayPanel.setDividerLocation(0.50);

        createMenuSectionsDisplay();
        createMenuItemsDisplay();
        overall.add(menuDisplayPanel);
    }

    /*
     * EFFECTS: adds list of menu sections to split pane
     */
    private void createMenuSectionsDisplay() {
        menuModel = new DefaultListModel<>();
        menuList = new JList<>();
        menuList.setModel(menuModel);
        menuList.setVisibleRowCount(-1);

        menuModel.addElement("APPETIZERS");
        menuModel.addElement("MAINS");
        menuModel.addElement("SIDES");
        menuModel.addElement("DRINKS");
        menuModel.addElement("DESSERTS");

        menuDisplayPanel.setLeftComponent(new JScrollPane(menuList));
    }

    /*
     * EFFECTS: adds display of menu items per menu section to split pane
     */
    private void createMenuItemsDisplay() {
        menuListList = new JList<>();
        menuListList.setVisibleRowCount(-1);

        menuList.getSelectionModel().addListSelectionListener(e -> {
            String item = menuList.getSelectedValue();
            if (item.equals("APPETIZERS")) {
                createMenuItemsDisplayAbstract(database.getMenu().getAppetizers());
            } else if (item.equals("MAINS")) {
                createMenuItemsDisplayAbstract(database.getMenu().getMains());
            } else if (item.equals("SIDES")) {
                createMenuItemsDisplayAbstract(database.getMenu().getSides());
            } else if (item.equals("DRINKS")) {
                createMenuItemsDisplayAbstract(database.getMenu().getDrinks());
            } else if (item.equals("DESSERTS")) {
                createMenuItemsDisplayAbstract(database.getMenu().getDesserts());
            } else {
                // display nothing
            }

        });

        menuDisplayPanel.setRightComponent(new JScrollPane(menuListList));
    }

    /*
     * EFFECTS: adds each menu item of a given menu section
     *          to the menuListModel; displays nothing if 
     *          menu section is empty
     */
    private void createMenuItemsDisplayAbstract(ArrayList<MenuItem> list) {
        menuListModel = new DefaultListModel<>();
        menuListList.setModel(menuListModel);

        for (MenuItem menuItem : list) {
            if (!menuListModel.contains(menuItem)) {
                menuListModel.addElement(menuItem);
            } else if (menuListModel.getSize() == 0) {
                menuListModel.addElement(null);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // !!! TO-DO
    }

}
