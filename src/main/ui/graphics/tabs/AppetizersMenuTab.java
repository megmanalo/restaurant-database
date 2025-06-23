package ui.graphics.tabs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import model.MenuItem;
import ui.RestaurantDatabase;

public class AppetizersMenuTab extends MenuTab {

    /*
     * Creates the menu tab of the application
     */
    public AppetizersMenuTab(RestaurantDatabase controller) {
        super(controller);

        createFirstComponent("APPETIZERS", "Appetizer");
        createSecondComponent();

        add(sectionPanel);
    }

    // /*
    //  * EFFECTS: creates first component of section panel
    //  */
    // private void createFirstComponent(String label, String item) {
    //     sectionFirstPanel = new JPanel();
    //     sectionFirstPanel.setLayout(new BoxLayout(sectionFirstPanel, BoxLayout.PAGE_AXIS));

    //     createLabelPanel(label);
    //     createInteractionPanel(item);

    //     sectionPanel.add(sectionFirstPanel);
    // }
    
    // /*
    //  * EFFECTS: creates first row, label, for section panel
    //  */
    // private void createLabelPanel(String label) {
    //     JPanel labelPanel = new JPanel();
    //     labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
    //     labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JLabel sectionLabel = new JLabel(label);
    //     sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JButton refreshBtn = new JButton("Refresh");
    //     refreshBtn.setActionCommand("Load");
    //     refreshBtn.addActionListener(this);
    //     refreshBtn.setLayout(new BoxLayout(refreshBtn, BoxLayout.LINE_AXIS));
    //     refreshBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     labelPanel.add(sectionLabel);
    //     labelPanel.add(refreshBtn);

    //     sectionFirstPanel.add(labelPanel);
    // }

    // /*
    //  * EFFECTS: creates interactive section for section panel
    //  */
    // private void createInteractionPanel(String item) {
    //     interactivePanel = new JPanel();
    //     interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.LINE_AXIS));
    //     interactivePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     createInputPanel();
    //     createButtonPanel(item);

    //     sectionFirstPanel.add(interactivePanel);
    // }

    // /*
    //  * EFFECTS: adds input text fields to interaction panel
    //  */
    // private void createInputPanel() {
    //     JPanel inputPanel = new JPanel();
    //     inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
    //     inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JPanel newMenuItemNamePanel = new JPanel();
    //     JLabel newNameLabel = new JLabel("New item name: ");
    //     newMenuItemName = new JTextField(10);
    //     newMenuItemNamePanel.add(newNameLabel);
    //     newMenuItemNamePanel.add(newMenuItemName);
    //     newMenuItemNamePanel.setLayout(new BoxLayout(newMenuItemNamePanel, BoxLayout.LINE_AXIS));
    //     newMenuItemNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JPanel newMenuItemPricePanel = new JPanel();
    //     JLabel newPriceLabel = new JLabel("New item price (in cents): ");
    //     newMenuItemPrice = new JTextField(10);
    //     newMenuItemPricePanel.add(newPriceLabel);
    //     newMenuItemPricePanel.add(newMenuItemPrice);
    //     newMenuItemPricePanel.setLayout(new BoxLayout(newMenuItemPricePanel, BoxLayout.LINE_AXIS));
    //     newMenuItemPricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     inputPanel.add(newMenuItemNamePanel);
    //     inputPanel.add(newMenuItemPricePanel);

    //     interactivePanel.add(inputPanel);
    // }

    // /*
    //  * EFFECTS: adds buttons to interaction panel
    //  */
    // private void createButtonPanel(String item) {
    //     JPanel buttonPanel = new JPanel();
    //     buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
    //     buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JButton saveMenuItemBtn = new JButton("Make New " + item);
    //     saveMenuItemBtn.setActionCommand("Save");
    //     saveMenuItemBtn.addActionListener(this);
    //     saveMenuItemBtn.setLayout(new BoxLayout(saveMenuItemBtn, BoxLayout.LINE_AXIS));
    //     saveMenuItemBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     JButton deleteMenuItemBtn = new JButton("Delete Selected Item");
    //     deleteMenuItemBtn.setActionCommand("Delete");
    //     deleteMenuItemBtn.addActionListener(this);
    //     deleteMenuItemBtn.setLayout(new BoxLayout(deleteMenuItemBtn, BoxLayout.LINE_AXIS));
    //     deleteMenuItemBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

    //     buttonPanel.add(saveMenuItemBtn);
    //     buttonPanel.add(deleteMenuItemBtn);

    //     interactivePanel.add(buttonPanel);
    // }

    // /*
    //  * EFFECTS: creates second component of section panel
    //  */
    // private void createSecondComponent() {
    //     sectionSecondPanel = new JPanel();
    //     sectionSecondPanel.setLayout(new BoxLayout(sectionSecondPanel, BoxLayout.PAGE_AXIS));

    //     createListDisplay();

    //     sectionPanel.add(sectionSecondPanel);
    // }

    // /*
    //  * EFFECTS: creates list display of menu section
    //  */
    // private void createListDisplay() {
    //     splitPane = new JSplitPane();
    //     splitPane.setPreferredSize(new Dimension(250, 75));
    //     splitPane.setDividerLocation(0.65);

    //     createListPanel();
    //     createPricePanel();
        
    //     sectionSecondPanel.add(splitPane);
    // }

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

    // /*
    //  * EFFECTS: adds display of menu item prices to split pane
    //  */
    // private void createPricePanel() {
    //     JPanel pricePanel = new JPanel();
    //     JLabel priceDisplay = new JLabel();

    //     list.getSelectionModel().addListSelectionListener(e -> {
    //         MenuItem item = list.getSelectedValue();
    //         if (!(item == null)) {
    //             priceDisplay.setText(item.getPrice() + " cents");
    //         } else {
    //             priceDisplay.setText("");
    //         }
    //     });

    //     pricePanel.add(priceDisplay);
    //     splitPane.setRightComponent(pricePanel);
    // }

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

    // /*
    //  * EFFECTS: assigns functions per button
    //  */
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     if (e.getActionCommand().equals("Load")) {
    //         createListPanel();
    //         splitPane.setDividerLocation(0.65);
    //         createPricePanel();
    //     } else if (e.getActionCommand().equals("Save")) {
    //         createNewMenuItem();
    //     } else if (e.getActionCommand().equals("Delete")) {
    //         deleteMenuItem();
    //     }
    // }
}
