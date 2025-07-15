package ui.graphics.tabs.menuTab;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.RestaurantDatabase;
import ui.graphics.tabs.Tab;

public class MenuTab extends Tab {
    private JPanel overall;
    
    public MenuTab(RestaurantDatabase controller) {
        super(controller);
        
        overall = new JPanel();
        overall.setLayout(new BoxLayout(overall, BoxLayout.PAGE_AXIS));

        overall.add(new AppetizersMenuTabSection(controller));
        overall.add(new MainsMenuTabSection(controller));
        overall.add(new SidesMenuTabSection(controller));
        overall.add(new DrinksMenuTabSection(controller));
        overall.add(new DessertsMenuTabSection(controller));

        add(overall);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TO-DO
    }
}
