package com.stock.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StockManagementUI extends JFrame {

    private JPanel contentPane;
    private JTextField tagIdField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextArea resultTextArea;
    private StockDatabaseManager databaseManager;

    public StockManagementUI(StockDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        initializeUI();
    }
    private void initializeUI() {
        setTitle("Stock Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTagId = new JLabel("Tag ID:");
        lblTagId.setBounds(30, 30, 100, 20);
        contentPane.add(lblTagId);

        tagIdField = new JTextField();
        tagIdField.setBounds(130, 30, 200, 20);
        contentPane.add(tagIdField);
        tagIdField.setColumns(10);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 70, 100, 20);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(130, 70, 200, 20);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(30, 110, 100, 20);
        contentPane.add(lblQuantity);

        quantityField = new JTextField();
        quantityField.setBounds(130, 110, 200, 20);
        contentPane.add(quantityField);
        quantityField.setColumns(10);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(30, 150, 100, 20);
        contentPane.add(lblPrice);

        priceField = new JTextField();
        priceField.setBounds(130, 150, 200, 20);
        contentPane.add(priceField);
        priceField.setColumns(10);

        JButton btnEnterStock = new JButton("Enter Stock");
        btnEnterStock.setBounds(30, 200, 120, 25);
        contentPane.add(btnEnterStock);
        btnEnterStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleEnterStock();
            }
        });

        JButton btnUpdateStock = new JButton("Update Stock");
        btnUpdateStock.setBounds(160, 200, 120, 25);
        contentPane.add(btnUpdateStock);
        btnUpdateStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUpdateStock();
            }
        });

        JButton btnDeleteStock = new JButton("Delete Stock");
        btnDeleteStock.setBounds(290, 200, 120, 25);
        contentPane.add(btnDeleteStock);
        btnDeleteStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteStock();
            }
        });

        JButton btnViewStock = new JButton("View Stock");
        btnViewStock.setBounds(420, 200, 120, 25);
        contentPane.add(btnViewStock);
        btnViewStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleViewStock();
            }
        });

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBounds(30, 250, 510, 200);
        contentPane.add(scrollPane);
    }

    private void handleEnterStock() {
        String tagId = tagIdField.getText();
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Stock stock = new Stock(tagId, name, quantity, price);
        if (databaseManager.addStock(stock)) {
            displayMessage("Stock entered successfully!");
        } else {
            displayMessage("Error entering stock!");
        }
    }

    private void handleUpdateStock() {
        String tagId = tagIdField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        Stock stock = new Stock(tagId, null, quantity, 0);
        if (databaseManager.updateStock(tagId, quantity)) {
            displayMessage("Stock updated successfully!");
        } else {
            displayMessage("Error updating stock!");
        }
    }

    private void handleDeleteStock() {
        String tagId = tagIdField.getText();

        if (databaseManager.deleteStock(tagId)) {
            displayMessage("Stock deleted successfully!");
        } else {
            displayMessage("Error deleting stock!");
        }
    }

    private void handleViewStock() {
        String tagId = tagIdField.getText();
        Stock stock = databaseManager.getStock(tagId);
        if (stock != null) {
            displayMessage(stock.toString());
        } else {
            displayMessage("Stock not found!");
        }
    }

    private void displayMessage(String message) {
        resultTextArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    StockDatabaseManager databaseManager = new StockDatabaseManager();
                    databaseManager.getMysqlConnection();
                    StockManagementUI frame = new StockManagementUI(databaseManager);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
