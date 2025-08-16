package ui.graphics.tabs.pointOfSaleTab;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.OrderSummary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import ui.RestaurantDatabase;
import ui.graphics.tabs.Tab;

public class PointOfSaleTab extends Tab {
    // private PointOfSaleSection unpaidTabsSection;
    // private PointOfSaleSection paidTabsSection;
    private JPanel overall;
    private JLabel label;
    private JButton refreshBtn;
    private JPanel filler = new JPanel();

    /*
     * Creates the P.O.S. tab of the application
     */
    public PointOfSaleTab(RestaurantDatabase controller) {
        super(controller);

        overall = new JPanel();
        overall.setLayout(new BoxLayout(overall, BoxLayout.LINE_AXIS));

        loadPointOfSaleButtons();

        // Just a placeholder to work on UpdateTab
        UpdateTab test = new UpdateTab(controller, new OrderSummary("Meg", 3));

        // loadUnpaidTabsSection(); !!! TO-DO
        // loadPaidTabsSection(); !!! TO-DO

        overall.add(test);
        add(overall);
    }

    /*
     * EFFECTS: sets up buttons for P.O.S. System tab of application
     */
    private void loadPointOfSaleButtons() {
        // JPanel panel = new JPanel();

        // label = new JLabel("ORDER SUMMARIES");
        // createRefreshButton();

        // panel.add(label);
        // panel.add(refreshBtn);

        // overall.add(panel);
        // overall.add(filler);
    }

    /*
     * EFFECTS: initializes the refresh button
     */
    private void createRefreshButton() {
        // refreshBtn = new JButton("REFRESH");
        // refreshBtn.setActionCommand("Load");
        // refreshBtn.addActionListener(this);
        // refreshBtn.setLayout(new BoxLayout(refreshBtn, BoxLayout.LINE_AXIS));
        // refreshBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /*
     * EFFECTS: assigns functions per button
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load")) {
            // unpaidTabsSection.actionPerformed(e);
            // paidTabsSection.actionPerformed(e);
        }
    }
}
