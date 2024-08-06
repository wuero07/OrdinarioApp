package com.example.venta;

import java.io.Serializable;

public class SaleItem implements Serializable {
    private int productId;
    private int quantity;
    private double price;
    private double amount;

    public SaleItem(int productId, int quantity, double price, double amount) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getAmount() {
        return amount;
    }
}


