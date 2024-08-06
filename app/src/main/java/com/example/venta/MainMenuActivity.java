package com.example.venta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.venta.Product;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonSales;
    private Button buttonInventory;
    private Button buttonExit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        buttonSales = findViewById(R.id.buttonSales);
        buttonInventory = findViewById(R.id.buttonInventory);
        buttonExit = findViewById(R.id.buttonExit);


        buttonSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SalesActivity.class);
                startActivity(intent);
            }
        });

        buttonInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
