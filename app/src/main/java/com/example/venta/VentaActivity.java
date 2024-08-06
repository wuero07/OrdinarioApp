package com.example.venta;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class VentaActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ListView productListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        dbHelper = new DatabaseHelper(this);
        categorySpinner = findViewById(R.id.categorySpinner);
        productListView = findViewById(R.id.productListView);

        loadCategories();
    }

    private void loadCategories() {
        List<String> categories = dbHelper.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                loadProducts(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                productListView.setAdapter(null);
            }
        });
    }

    private void loadProducts(String category) {
        List<Product> products = dbHelper.getProductsByCategory(category);
        ProductAdapter adapter = new ProductAdapter(this, products);
        productListView.setAdapter(adapter);
    }
}
