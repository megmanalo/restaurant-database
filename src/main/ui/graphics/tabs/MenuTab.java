package ui.graphics.tabs;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import ui.RestaurantDatabase;
import model.*;

public class MenuTab extends Tab {
    private BoxLayout menuLayout;

    private JPanel appetizerDisplayPanel;
    private JPanel newAppetizerPanel;
    private JPanel buttonPanel;
    private JPanel panel;
    private JPanel newAppetizerNamePanel;
    private JPanel newAppetizerPricePanel;

    private JButton loadBtn;
    private JButton deleteAppetizerBtn;
    private JButton saveAppetizerBtn;

    private JTextField newAppetizerName;
    private JTextField newAppetizerPrice;

    private JList<MenuItem> list;

    private DefaultListModel<MenuItem> model;

    private JLabel label;
    private JLabel newNameLabel;
    private JLabel newPriceLabel;
    private JSplitPane splitPane;

    /*
     * Creates the menu tab of the application
     */
    public MenuTab(RestaurantDatabase controller) {
        super(controller);

        appetizerDisplayPanel = new JPanel();
        panel = new JPanel();
        newAppetizerNamePanel = new JPanel();
        newAppetizerPricePanel = new JPanel();

        label = new JLabel();
        newNameLabel = new JLabel();
        newPriceLabel = new JLabel();

        list = new JList<>();
        model = new DefaultListModel<>();
        splitPane = new JSplitPane();

        menuLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(menuLayout);

        setupNewAppetizerSection();
        setupButtons();
        setupMenuList();

        repaint();
        revalidate();
    }

    /*
     * EFFECTS: loads display of buttons for menu tab
     */
    public void setupButtons() {
        loadBtn = new JButton("Refresh Menu...");
        loadBtn.setActionCommand("Load");
        loadBtn.addActionListener(this);

        saveAppetizerBtn = new JButton("Make New Appetizer");
        saveAppetizerBtn.setActionCommand("Save");
        saveAppetizerBtn.addActionListener(this);

        deleteAppetizerBtn = new JButton("Delete Selected Item");
        deleteAppetizerBtn.setActionCommand("Delete");
        deleteAppetizerBtn.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(loadBtn);
        buttonPanel.add(saveAppetizerBtn);
        buttonPanel.add(deleteAppetizerBtn);

        add(buttonPanel);

        repaint();
        revalidate();
    }

    /*
     * EFFECTS: loads text fields for a new appetizer menu item
     */
    public void setupNewAppetizerSection() {
        newAppetizerPanel = new JPanel();

        newNameLabel = new JLabel("New item name: ");
        newPriceLabel = new JLabel("New item price (in cents): ");

        newAppetizerName = new JTextField(10);
        newAppetizerPrice = new JTextField(10);

        newAppetizerNamePanel.add(newNameLabel);
        newAppetizerNamePanel.add(newAppetizerName);
        newAppetizerPricePanel.add(newPriceLabel);
        newAppetizerPricePanel.add(newAppetizerPrice);

        newAppetizerPanel.add(newAppetizerNamePanel);
        newAppetizerPanel.add(newAppetizerPricePanel);

        add(newAppetizerPanel);

        repaint();
        revalidate();
    }

    /*
     * EFFECTS: loads list of appetizers from database
     */
    public void setupMenuList() {
        splitPane.setResizeWeight(1.0);
        list.setModel(model);

        for (MenuItem item : database.getMenu().getAppetizers()) {
            if (!model.contains(item)) {
                model.addElement(item);
            }
        }

        list.getSelectionModel().addListSelectionListener(e -> {
            MenuItem item = list.getSelectedValue();
            if (!(item == null)) {
                label.setText(item.getPrice() + " cents");
            } else {
                label.setText("");
            }
        });

        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label);
        splitPane.setRightComponent(panel);

        appetizerDisplayPanel.add(splitPane);

        add(appetizerDisplayPanel);

        repaint();
        revalidate();
    }

    /*
     * EFFECTS: takes input from text fields and
     *          uses these to create new appetizer in database
     */
    public void createNewAppetizer() {
        int price;
        MenuItem newItem = null;

        try {
            price = Integer.parseInt(newAppetizerPrice.getText());
            newItem = new MenuItem(newAppetizerName.getText(), price);
        } catch (NumberFormatException e) {
            price = -1;
        }

        if (!database.getMenu().getAppetizers().contains(newItem)) {
            if (price >= 0) {
                database.getMenu().addItem(database.getMenu().getAppetizers(), newItem);
            }
        }
    }

    /*
     * EFFECTS: deletes current selection from list of appetizers in the database
     */
    public void deleteMenuItem() {
        database.getMenu().removeItem(database.getMenu().getAppetizers(), list.getSelectedValue());
        model.removeElementAt(list.getSelectedIndex());
    }

    /*
     * EFFECTS: assigns functions per button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load")) {
            setupMenuList();
        } else if (e.getActionCommand().equals("Save")) {
            createNewAppetizer();
        } else if (e.getActionCommand().equals("Delete")) {
            deleteMenuItem();
        }
    }
}
