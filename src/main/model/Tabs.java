package model;

import java.util.*;

import org.json.JSONObject;

// List of paid and unpaid tabs of the restaurant.
public class Tabs {
    private ArrayList<OrderSummary> paidTabs;
    private ArrayList<OrderSummary> unpaidTabs;


    /*
     * Creates two separate lists of order summaries:
     *      - one for unpaid tabs of the restaurant; and
     *      - one for paid tabs of the restaurant.
     */
    public Tabs() {
        paidTabs = new ArrayList<>();
        unpaidTabs = new ArrayList<>();
    }

    /*
     *  MODIFIES: paidTabs or unpaidTabs
     *  EFFECTS: adds given tab to appropriate list of tabs
     *           according to tabStatus
     */
    public void organizeTab(OrderSummary tab) {
        if (tab.getTabStatus() == false) {
            if (!unpaidTabs.contains(tab)) {
                unpaidTabs.add(tab);
            }
        } else {
            if (!paidTabs.contains(tab)) {
                paidTabs.add(tab);
            }
        }
    }

    public ArrayList<OrderSummary> getPaidTabs() {
        return paidTabs;
    }

    public ArrayList<OrderSummary> getUnpaidTabs() {
        return unpaidTabs;
    }

    /*
     * EFFECTS: creates Json object for Tabs
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Paid Tabs", paidTabs);
        json.put("Unpaid Tabs", unpaidTabs);
        return json;
    }
}
