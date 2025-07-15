package ui.graphics.tabs.menuTab;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.RestaurantDatabase;
import ui.graphics.tabs.Tab;

public class MenuTab extends Tab {
    private MenuTabSection appetizersSection;
    private MenuTabSection mainsSection;
    private MenuTabSection sidesSection;
    private MenuTabSection drinksSection;
    private MenuTabSection dessertsSection;
    private JPanel overall;
    private JPanel filler = new JPanel();
    private JLabel label;
    private JButton refreshBtn;

    /*
     * EFFECTS: creates menu tab of the application
     */
    public MenuTab(RestaurantDatabase controller) {
        super(controller);
        
        overall = new JPanel();
        overall.setLayout(new BoxLayout(overall, BoxLayout.PAGE_AXIS));

        createTabTopSection();
        createMenuTabSections();

        add(overall);
    }

    /*
     * EFFECTS: creates top section of menu tab
     *          with a label and refresh button
     */
    private void createTabTopSection() {
        JPanel panel = new JPanel();

        label = new JLabel("MENU");
        createRefreshButton();

        panel.add(label);
        panel.add(refreshBtn);

        overall.add(panel);
        overall.add(filler);
    }

    /*
     * EFFECTS: initializes menu tab sections and
     *          adds them to menu tab
     */
    private void createMenuTabSections() {
        appetizersSection = new AppetizersMenuTabSection(controller);
        overall.add(appetizersSection);
        overall.add(filler);

        mainsSection = new MainsMenuTabSection(controller);
        overall.add(mainsSection);
        overall.add(filler);

        sidesSection = new SidesMenuTabSection(controller);
        overall.add(sidesSection);
        overall.add(filler);

        drinksSection = new DrinksMenuTabSection(controller);
        overall.add(drinksSection);
        overall.add(filler);

        dessertsSection = new DessertsMenuTabSection(controller);
        overall.add(dessertsSection);
        overall.add(filler);
    }

    /*
     * EFFECTS: initializes the refresh button
     */
    private void createRefreshButton() {
        refreshBtn = new JButton("REFRESH");
        refreshBtn.setActionCommand("Load");
        refreshBtn.addActionListener(this);
        refreshBtn.setLayout(new BoxLayout(refreshBtn, BoxLayout.LINE_AXIS));
        refreshBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load")) {
            appetizersSection.actionPerformed(e);
            mainsSection.actionPerformed(e);
            sidesSection.actionPerformed(e);
            drinksSection.actionPerformed(e);
            dessertsSection.actionPerformed(e);
        }
    }
}
