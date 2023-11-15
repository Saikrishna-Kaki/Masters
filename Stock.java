package com.stock.management.system;

public class Stock {

    private String tagID;
    private String name;
    private int availableQuantity;
    private double price;

    public Stock(String tagID, String name, int availableQuantity, double price) {
        this.tagID = tagID;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public String getTagID() {
        return tagID;
    }

    public String getName() {
        return name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tag ID: " + tagID + ", Name: " + name + ", Available Quantity: " + availableQuantity + ", Price: " + price;
    }
}
