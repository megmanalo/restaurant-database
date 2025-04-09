package model;

import java.util.*;

import org.json.JSONObject;

// OrderSummary contains information regarding a table's tab at a restaurant. **working
public class OrderSummary {
    private static final int    PARTY_CUTOFF = 6;
    private static final double CUTOFF_GRATUITY = 0.18; // 18%
    private static final double GST = 0.05; // 5%
    private static final double PST = 0.07; // 7%
    private String              name;       // name for the reservation?
    private boolean             tabStatus;  // has the bill been paid?
    private boolean             taxStatus;  // has the bill been taxed?
    private ArrayList<MenuItem> orders;     // what did the table order?
    private int                 partySize;  // how many people were in the table's party?
    private int                 tip;        // how much did the table choose to tip?
    private int                 totalBill;  // what is the table's final bill?


    /*
     * Creates an order summary with a given name and party size.
     * It has an empty list of orders, and the total bill reflects this.
     * This tab has not been paid nor taxed, and no tip percentage has
     * been added.
     */
    public OrderSummary(String name, int partySize) {
        this.name = name;
        this.tabStatus = false;
        this.taxStatus = false;
        this.orders = new ArrayList<>();
        this.partySize = partySize;
        this.tip = 0;
        this.totalBill = 0;
    }

    /*
     * MODIFIES: orders
     * EFFECTS: adds given menu item to tab's list of orders
     */
    public void addOrder(MenuItem item) {
        orders.add(item);
        totalBill += item.getPrice();
    }

    /*
     * MODIFIES: tip
     * EFFECTS: increments tip by given amount
     */
    public void addTip(int amount) {
        tip += amount;
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns the table's final bill
     *          plus the tip percentage, PST and GST
     *  
     *          for parties greater than or equal to PARTY_CUTOFF,
     *          automatically set tip equal to CUTOFF_GRATUITY
     */
    public int processOrder() {
        int finalBill = getTotalBill();

        if (taxStatus) {
            finalBill = getTotalBill();
        } else {
            int plusPST = (int)(((double)totalBill) * PST);
            int plusGST = (int)(((double)totalBill) * GST);
            int plusTip = (int)(((double)totalBill) * ((double)tip / 100));
        
            if (partySize >= PARTY_CUTOFF) {
                plusTip += (int)((double)(totalBill) * CUTOFF_GRATUITY);
            }

            finalBill += (plusPST + plusGST + plusTip);
            taxStatus = true;
            setTotalBill(finalBill);
        }

        return finalBill;
    }

    /*
     * MODIFIES: this
     * EFFECTS: applies given amount to tab
     *          sets status to true if tab is fully paid
     *          returns difference/change if applicable
     */
    public int payTab(int paidAmount) {
        int change = 0;

        if (!taxStatus) {
            totalBill = processOrder();
        }

        if (paidAmount <= totalBill) {
            totalBill = totalBill - paidAmount;
            
            if (totalBill == 0) {
                tabStatus = true;
            }
        } else {
            change = paidAmount - totalBill;
            totalBill = 0;
            tabStatus = true;
        }
        return change;
    }

    public String getName() {
        return name;
    }

    public boolean getTabStatus() {
        return tabStatus;
    }

    public boolean getTaxStatus() {
        return taxStatus;
    }

    public ArrayList<MenuItem> getOrders() {
        return orders;
    }

    public int getPartySize() {
        return partySize;
    }

    public int getTip() {
        return tip;
    }

    public int getTotalBill() {
        return totalBill;
    }

    // public void setName(String name) {
    //     this.name = name;
    // }

    public void setTabStatus(boolean tabStatus) {
        this.tabStatus = tabStatus;
    }

    public void setTaxStatus(boolean taxStatus) {
        this.taxStatus = taxStatus;
    }

    // public void setOrders(ArrayList<MenuItem> orders) {
    //     this.orders = orders;
    // }

    // public void setPartySize(int partySize) {
    //     this.partySize = partySize;
    // }

    public void setTip(int tip) {
        this.tip = tip;    
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    
    /*
     * EFFECTS: creates Json object for OrderSummary
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Tab Status", tabStatus);
        json.put("Tax Status", taxStatus);
        json.put("Orders", orders);
        json.put("Party Size", partySize);
        json.put("Tip", tip);
        json.put("Total Bill", totalBill);
        return json;
    }         
}
