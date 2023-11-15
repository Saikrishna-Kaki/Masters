package com.stock.management.system;

import javax.swing.*;

public class StockManagementSystem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StockDatabaseManager dbManager = new StockDatabaseManager();
                dbManager.getMysqlConnection("root", "MySql"); // Replace "MySql" with your actual MySQL password
                StockManagementUI ui = new StockManagementUI(dbManager);
                ui.setVisible(true);
            }
        });
    }
}
