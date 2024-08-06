package com.example.venta;

import java.io.Serializable;

public class Product {
    private int id;
    private String category;
    private String name;
    private double price;
    private int quantity;
    private String date;

    public Product(int id, String category, String name, double price, int quantity, String date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}



