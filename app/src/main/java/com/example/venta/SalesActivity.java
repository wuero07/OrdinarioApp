package com.example.venta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.text.InputType;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SalesActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private Button buttonFinalizeSale;
    private DatabaseHelper dbHelper;
    private List<Product> productList;
    private ArrayAdapter<Product> productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);


        listViewProducts = findViewById(R.id.listViewProducts);
        buttonFinalizeSale = findViewById(R.id.buttonFinalizeSale);


        dbHelper = new DatabaseHelper(this);


        loadProducts();


        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        listViewProducts.setAdapter(productAdapter);


        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                showProductDialog(selectedProduct);
            }
        });


        buttonFinalizeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeSale();
            }
        });
    }

    private void loadProducts() {

        productList = dbHelper.getAllProducts();
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        listViewProducts.setAdapter(productAdapter);
    }

    private void showProductDialog(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingresar Cantidad");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantityString = input.getText().toString().trim();
                if (!quantityString.isEmpty()) {
                    int quantity = Integer.parseInt(quantityString);
                    if (quantity > 0) {

                        dbHelper.insertSale(product.getId(), quantity, product.getPrice(), quantity * product.getPrice());
                        Toast.makeText(SalesActivity.this, "Producto agregado a la venta.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SalesActivity.this, "La cantidad debe ser mayor a 0.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SalesActivity.this, "Por favor, ingrese una cantidad.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void finalizeSale() {

        SaleItem saleItem = new SaleItem(1, 1, 20.0, 20.0);

        Intent intent = new Intent(this, SaleSummaryActivity.class);
        intent.putExtra("saleItem", saleItem);
        startActivity(intent);
    }

}



