package com.example.venta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.venta.Product;

public class SaleDetails {
    private List<SaleItem> saleItems;
    private double totalAmount;

    public SaleDetails() {
        this.saleItems = new ArrayList<>();
    }

    public void addSaleItem(SaleItem item) {
        saleItems.add(item);
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

