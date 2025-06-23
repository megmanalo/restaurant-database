package ui.graphics.tabs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import model.MenuItem;
import ui.RestaurantDatabase;

public abstract class MenuTab extends Tab {
    protected JPanel sectionPanel;
    protected JPanel sectionFirstPanel;
    protected JPanel interactivePanel;
    protected JPanel sectionSecondPanel;
    protected JSplitPane splitPane;
    protected JList<MenuItem> list;
    protected JTextField newMenuItemPrice;
    protected JTextField newMenuItemName;
    protected DefaultListModel<MenuItem> model;
    protected int price;
    protected MenuItem newItem;

    /*
     * Creates the menu tab of the application
     */
    public MenuTab(RestaurantDatabase controller) {
        super(controller);

        sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.LINE_AXIS));

        // additional functions added per dependent class 
    }

    /*
     * EFFECTS: creates first component of section panel
     */
    protected void createFirstComponent(String label, String item) {
        sectionFirstPanel = new JPanel();
        sectionFirstPanel.setLayout(new BoxLayout(sectionFirstPanel, BoxLayout.PAGE_AXIS));

        createLabelPanel(label);
        createInteractionPanel(item);

        sectionPanel.add(sectionFirstPanel);
    }
    
    /*
     * EFFECTS: creates first row, label, for section panel
     */
    protected void createLabelPanel(String label) {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sectionLabel = new JLabel(label);
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setActionCommand("Load");
        refreshBtn.addActionListener(this);
        refreshBtn.setLayout(new BoxLayout(refreshBtn, BoxLayout.LINE_AXIS));
        refreshBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelPanel.add(sectionLabel);
        labelPanel.add(refreshBtn);

        sectionFirstPanel.add(labelPanel);
    }

    /*
     * EFFECTS: creates interactive section for section panel
     */
    protected void createInteractionPanel(String item) {
        interactivePanel = new JPanel();
        interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.LINE_AXIS));
        interactivePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        createInputPanel();
        createButtonPanel(item);

        sectionFirstPanel.add(interactivePanel);
    }

    /*
     * EFFECTS: adds input text fields to interaction panel
     */
    protected void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel newMenuItemNamePanel = new JPanel();
        JLabel newNameLabel = new JLabel("New item name: ");
        newMenuItemName = new JTextField(10);
        newMenuItemNamePanel.add(newNameLabel);
        newMenuItemNamePanel.add(newMenuItemName);
        newMenuItemNamePanel.setLayout(new BoxLayout(newMenuItemNamePanel, BoxLayout.LINE_AXIS));
        newMenuItemNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel newMenuItemPricePanel = new JPanel();
        JLabel newPriceLabel = new JLabel("New item price (in cents): ");
        newMenuItemPrice = new JTextField(10);
        newMenuItemPricePanel.add(newPriceLabel);
        newMenuItemPricePanel.add(newMenuItemPrice);
        newMenuItemPricePanel.setLayout(new BoxLayout(newMenuItemPricePanel, BoxLayout.LINE_AXIS));
        newMenuItemPricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputPanel.add(newMenuItemNamePanel);
        inputPanel.add(newMenuItemPricePanel);

        interactivePanel.add(inputPanel);
    }

    /*
     * EFFECTS: adds buttons to interaction panel
     */
    protected void createButtonPanel(String item) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton saveMenuItemBtn = new JButton("Make New " + item);
        saveMenuItemBtn.setActionCommand("Save");
        saveMenuItemBtn.addActionListener(this);
        saveMenuItemBtn.setLayout(new BoxLayout(saveMenuItemBtn, BoxLayout.LINE_AXIS));
        saveMenuItemBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton deleteMenuItemBtn = new JButton("Delete Selected Item");
        deleteMenuItemBtn.setActionCommand("Delete");
        deleteMenuItemBtn.addActionListener(this);
        deleteMenuItemBtn.setLayout(new BoxLayout(deleteMenuItemBtn, BoxLayout.LINE_AXIS));
        deleteMenuItemBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonPanel.add(saveMenuItemBtn);
        buttonPanel.add(deleteMenuItemBtn);

        interactivePanel.add(buttonPanel);
    }

    /*
     * EFFECTS: creates second component of section panel
     */
    protected void createSecondComponent() {
        sectionSecondPanel = new JPanel();
        sectionSecondPanel.setLayout(new BoxLayout(sectionSecondPanel, BoxLayout.PAGE_AXIS));

        createListDisplay();

        sectionPanel.add(sectionSecondPanel);
    }

    /*
     * EFFECTS: creates list display of menu section
     */
    protected void createListDisplay() {
        splitPane = new JSplitPane();
        splitPane.setPreferredSize(new Dimension(250, 75));
        splitPane.setDividerLocation(0.65);

        createListPanel();
        createPricePanel();
        
        sectionSecondPanel.add(splitPane);
    }

    /*
     * EFFECTS: adds menu list to split pane
     */
    protected void createListPanel() {
        model = new DefaultListModel<>();
        list = new JList<>();
        list.setModel(model);
        list.setVisibleRowCount(-1);

        // additional functions added per dependent class
    }

    /*
     * EFFECTS: adds display of menu item prices to split pane
     */
    protected void createPricePanel() {
        JPanel pricePanel = new JPanel();
        JLabel priceDisplay = new JLabel();

        list.getSelectionModel().addListSelectionListener(e -> {
            MenuItem item = list.getSelectedValue();
            if (!(item == null)) {
                priceDisplay.setText(item.getPrice() + " cents");
            } else {
                priceDisplay.setText("");
            }
        });

        pricePanel.add(priceDisplay);
        splitPane.setRightComponent(pricePanel);
    }

    /*
     * EFFECTS: takes input from text fields and
     *          uses these to create new menu item in database
     */
    protected void createNewMenuItem() {
        newItem = null;

        try {
            price = Integer.parseInt(newMenuItemPrice.getText());
            newItem = new MenuItem(newMenuItemName.getText(), price);
        } catch (NumberFormatException e) {
            price = -1;
        }

        // additional functions added per dependent class
    }

    /*
     * EFFECTS: deletes current selection from appropriate list
     *          of menu items in the database
     */
    abstract void deleteMenuItem();
    // additional functions added per dependent class

    /*
     * EFFECTS: assigns functions per button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load")) {
            createListPanel();
            splitPane.setDividerLocation(0.65);
            createPricePanel();
        } else if (e.getActionCommand().equals("Save")) {
            createNewMenuItem();
        } else if (e.getActionCommand().equals("Delete")) {
            deleteMenuItem();
        }
    }
}
