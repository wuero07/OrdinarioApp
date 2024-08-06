package com.example.venta;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SaleSummaryActivity extends AppCompatActivity {

    private TextView textViewSaleSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_summary);

        textViewSaleSummary = findViewById(R.id.textViewSaleSummary);


        SaleItem saleItem = (SaleItem) getIntent().getSerializableExtra("saleItem");

        if (saleItem != null) {

            String summary = "Product ID: " + saleItem.getProductId() + "\n" +
                    "Quantity: " + saleItem.getQuantity() + "\n" +
                    "Price: " + saleItem.getPrice() + "\n" +
                    "Amount: " + saleItem.getAmount();
            textViewSaleSummary.setText(summary);
        }
    }
}

