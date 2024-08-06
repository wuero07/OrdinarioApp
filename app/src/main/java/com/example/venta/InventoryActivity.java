package com.example.venta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.venta.Product;


import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {

    private EditText editTextCategory;
    private Button buttonAddCategory, buttonBackToMenu;
    private LinearLayout layoutCategoryButtons;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        dbHelper = new DatabaseHelper(this);

        editTextCategory = findViewById(R.id.editTextCategory);
        buttonAddCategory = findViewById(R.id.buttonAddCategory);
        layoutCategoryButtons = findViewById(R.id.layoutCategoryButtons);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = editTextCategory.getText().toString().trim();
                if (!categoryName.isEmpty()) {
                    addCategoryButtons(categoryName);
                } else {
                    Toast.makeText(InventoryActivity.this, "El campo de categoría es obligatorio.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para regresar al menú principal
                finish();
            }
        });
    }

    private void addCategoryButtons(String categoryName) {
        Button buttonInsertProduct = new Button(this);
        buttonInsertProduct.setText("Insertar Producto en " + categoryName);
        buttonInsertProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInsertProductDialog(categoryName);
            }
        });

        Button buttonDeleteProduct = new Button(this);
        buttonDeleteProduct.setText("Eliminar Producto de " + categoryName);
        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteProductDialog(categoryName);
            }
        });

        Button buttonUpdateProduct = new Button(this);
        buttonUpdateProduct.setText("Actualizar Producto en " + categoryName);
        buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateProductDialog(categoryName);
            }
        });

        layoutCategoryButtons.addView(buttonInsertProduct);
        layoutCategoryButtons.addView(buttonDeleteProduct);
        layoutCategoryButtons.addView(buttonUpdateProduct);
    }

    private void showInsertProductDialog(String categoryName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insertar Producto en " + categoryName);

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_insert_product, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editTextName = customLayout.findViewById(R.id.editTextName);
                EditText editTextPrice = customLayout.findViewById(R.id.editTextPrice);
                EditText editTextQuantity = customLayout.findViewById(R.id.editTextQuantity);

                String name = editTextName.getText().toString().trim();
                String priceStr = editTextPrice.getText().toString().trim();
                String quantityStr = editTextQuantity.getText().toString().trim();

                if (!name.isEmpty() && !priceStr.isEmpty() && !quantityStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    dbHelper.insertProduct(categoryName, name, price, quantity);
                    Toast.makeText(InventoryActivity.this, "Producto insertado en " + categoryName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
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

    private void showDeleteProductDialog(String categoryName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Producto de " + categoryName);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productIdStr = input.getText().toString().trim();

                if (!productIdStr.isEmpty()) {
                    int productId = Integer.parseInt(productIdStr);
                    dbHelper.deleteProduct(categoryName, productId);
                    Toast.makeText(InventoryActivity.this, "Producto eliminado de " + categoryName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "El campo ID es obligatorio.", Toast.LENGTH_SHORT).show();
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

    private void showUpdateProductDialog(String categoryName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar Producto en " + categoryName);

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_update_product, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editTextId = customLayout.findViewById(R.id.editTextId);
                EditText editTextPrice = customLayout.findViewById(R.id.editTextPrice);
                EditText editTextQuantity = customLayout.findViewById(R.id.editTextQuantity);

                String idStr = editTextId.getText().toString().trim();
                String priceStr = editTextPrice.getText().toString().trim();
                String quantityStr = editTextQuantity.getText().toString().trim();

                if (!idStr.isEmpty() && !priceStr.isEmpty() && !quantityStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    dbHelper.updateProduct(categoryName, id, price, quantity);
                    Toast.makeText(InventoryActivity.this, "Producto actualizado en " + categoryName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InventoryActivity.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
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
}


