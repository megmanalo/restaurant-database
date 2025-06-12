package ui.graphics.tabs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import model.MenuItem;
import ui.RestaurantDatabase;

public class MenuTab extends Tab {
    private JPanel appetizersPanel;
    private JPanel appetizersFirstPanel;
    private JPanel interactivePanel;
    private JPanel appetizersSecondPanel;
    private JSplitPane splitPane;
    private JList<MenuItem> list;
    private JTextField newAppetizerPrice;
    private JTextField newAppetizerName;
    private DefaultListModel<MenuItem> model;

    public MenuTab(RestaurantDatabase controller) {
        super(controller);

        appetizersPanel = new JPanel();
        appetizersPanel.setLayout(new BoxLayout(appetizersPanel, BoxLayout.LINE_AXIS));

        createFirstComponent();
        createSecondComponent();

        add(appetizersPanel);
    }

    // creates first component of appetizers panel
    private void createFirstComponent() {
        appetizersFirstPanel = new JPanel();
        appetizersFirstPanel.setLayout(new BoxLayout(appetizersFirstPanel, BoxLayout.PAGE_AXIS));

        createLabelPanel();
        createInteractionPanel();

        appetizersPanel.add(appetizersFirstPanel);
    }
    
    // creates first row, label, for appetizers panel
    private void createLabelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel appetizersLabel = new JLabel("APPETIZERS");
        appetizersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setActionCommand("Load");
        refreshBtn.addActionListener(this);
        refreshBtn.setLayout(new BoxLayout(refreshBtn, BoxLayout.LINE_AXIS));
        refreshBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelPanel.add(appetizersLabel);
        labelPanel.add(refreshBtn);

        appetizersFirstPanel.add(labelPanel);
    }

    // creates interactive section for appetizers panel
    private void createInteractionPanel() {
        interactivePanel = new JPanel();
        interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.LINE_AXIS));
        interactivePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        createInputPanel();
        createButtonPanel();

        appetizersFirstPanel.add(interactivePanel);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel newAppetizerNamePanel = new JPanel();
        JLabel newNameLabel = new JLabel("New item name: ");
        newAppetizerName = new JTextField(10);
        newAppetizerNamePanel.add(newNameLabel);
        newAppetizerNamePanel.add(newAppetizerName);
        newAppetizerNamePanel.setLayout(new BoxLayout(newAppetizerNamePanel, BoxLayout.LINE_AXIS));
        newAppetizerNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel newAppetizerPricePanel = new JPanel();
        JLabel newPriceLabel = new JLabel("New item price (in cents): ");
        newAppetizerPrice = new JTextField(10);
        newAppetizerPricePanel.add(newPriceLabel);
        newAppetizerPricePanel.add(newAppetizerPrice);
        newAppetizerPricePanel.setLayout(new BoxLayout(newAppetizerPricePanel, BoxLayout.LINE_AXIS));
        newAppetizerPricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputPanel.add(newAppetizerNamePanel);
        inputPanel.add(newAppetizerPricePanel);

        interactivePanel.add(inputPanel);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton saveAppetizerBtn = new JButton("Make New Appetizer");
        saveAppetizerBtn.setActionCommand("Save");
        saveAppetizerBtn.addActionListener(this);
        saveAppetizerBtn.setLayout(new BoxLayout(saveAppetizerBtn, BoxLayout.LINE_AXIS));
        saveAppetizerBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton deleteAppetizerBtn = new JButton("Delete Selected Item");
        deleteAppetizerBtn.setActionCommand("Delete");
        deleteAppetizerBtn.addActionListener(this);
        deleteAppetizerBtn.setLayout(new BoxLayout(deleteAppetizerBtn, BoxLayout.LINE_AXIS));
        deleteAppetizerBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonPanel.add(saveAppetizerBtn);
        buttonPanel.add(deleteAppetizerBtn);

        interactivePanel.add(buttonPanel);
    }

    private void createSecondComponent() {
        appetizersSecondPanel = new JPanel();
        appetizersSecondPanel.setLayout(new BoxLayout(appetizersSecondPanel, BoxLayout.PAGE_AXIS));

        createListDisplay();

        appetizersPanel.add(appetizersSecondPanel);
    }

    private void createListDisplay() {
        splitPane = new JSplitPane();
        splitPane.setPreferredSize(new Dimension(250, 75));
        splitPane.setDividerLocation(0.65);

        createListPanel();
        createPricePanel();
        
        appetizersSecondPanel.add(splitPane);
    }

    private void createListPanel() {
        model = new DefaultListModel<>();
        list = new JList<>();
        list.setModel(model);
        list.setVisibleRowCount(-1);

        for (MenuItem item : database.getMenu().getAppetizers()) {
            if (!model.contains(item)) {
                model.addElement(item);
            }
        }

        splitPane.setLeftComponent(new JScrollPane(list));
    }

    private void createPricePanel() {
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
            createListPanel();
            splitPane.setDividerLocation(0.65);
            createPricePanel();
        } else if (e.getActionCommand().equals("Save")) {
            createNewAppetizer();
        } else if (e.getActionCommand().equals("Delete")) {
            deleteMenuItem();
        }
    }
}
