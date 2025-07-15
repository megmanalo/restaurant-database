package ui.graphics.tabs;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.RestaurantDatabase;

public class MenuTab extends Tab {
    private JPanel overall;
    
    public MenuTab(RestaurantDatabase controller) {
        super(controller);
        
        overall = new JPanel();
        overall.setLayout(new BoxLayout(overall, BoxLayout.PAGE_AXIS));

        overall.add(new AppetizersMenuTabSection(controller));

        add(overall);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TO-DO
    }
}
