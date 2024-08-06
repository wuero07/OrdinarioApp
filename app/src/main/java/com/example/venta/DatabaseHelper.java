package com.example.venta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OrdinarioBD.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_PRODUCTS = "productos";
    private static final String TABLE_SALES = "venta";

    private static final String COLUMN_PRODUCT_ID = "id_producto";
    private static final String COLUMN_PRODUCT_CATEGORY = "categoria";
    private static final String COLUMN_PRODUCT_NAME = "nombre";
    private static final String COLUMN_PRODUCT_PRICE = "precio";
    private static final String COLUMN_PRODUCT_QUANTITY = "cantidad";
    private static final String COLUMN_PRODUCT_DATE = "fecha";

    private static final String COLUMN_SALE_ID = "id_venta";
    private static final String COLUMN_SALE_PRODUCT_ID = "id_producto";
    private static final String COLUMN_SALE_QUANTITY = "cantidad";
    private static final String COLUMN_SALE_PRICE = "precio";
    private static final String COLUMN_SALE_AMOUNT = "importe";
    private static final String COLUMN_SALE_DATE = "fecha";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_CATEGORY + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + COLUMN_PRODUCT_DATE + " TEXT NOT NULL);";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_SALES_TABLE = "CREATE TABLE " + TABLE_SALES + " ("
                + COLUMN_SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SALE_PRODUCT_ID + " INTEGER NOT NULL, "
                + COLUMN_SALE_QUANTITY + " INTEGER NOT NULL, "
                + COLUMN_SALE_PRICE + " REAL NOT NULL, "
                + COLUMN_SALE_AMOUNT + " REAL NOT NULL, "
                + COLUMN_SALE_DATE + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_SALE_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_ID + "));";
        db.execSQL(CREATE_SALES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
            onCreate(db);
        }
    }

    public void insertProduct(String category, String name, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_CATEGORY, category);
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(COLUMN_PRODUCT_DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public void deleteProduct(String category, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_PRODUCT_CATEGORY + "=? AND " + COLUMN_PRODUCT_ID + "=?";
        String[] whereArgs = new String[]{category, String.valueOf(id)};
        db.delete(TABLE_PRODUCTS, whereClause, whereArgs);
        db.close();
    }

    public void updateProduct(String category, int id, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_QUANTITY, quantity);

        String whereClause = COLUMN_PRODUCT_CATEGORY + "=? AND " + COLUMN_PRODUCT_ID + "=?";
        String[] whereArgs = new String[]{category, String.valueOf(id)};
        db.update(TABLE_PRODUCTS, values, whereClause, whereArgs);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = getIntFromCursor(cursor, COLUMN_PRODUCT_ID);
                String category = getStringFromCursor(cursor, COLUMN_PRODUCT_CATEGORY);
                String name = getStringFromCursor(cursor, COLUMN_PRODUCT_NAME);
                double price = getDoubleFromCursor(cursor, COLUMN_PRODUCT_PRICE);
                int quantity = getIntFromCursor(cursor, COLUMN_PRODUCT_QUANTITY);
                String date = getStringFromCursor(cursor, COLUMN_PRODUCT_DATE);

                Product product = new Product(id, category, name, price, quantity, date);
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCT_CATEGORY + " = ?", new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                int id = getIntFromCursor(cursor, COLUMN_PRODUCT_ID);
                String name = getStringFromCursor(cursor, COLUMN_PRODUCT_NAME);
                double price = getDoubleFromCursor(cursor, COLUMN_PRODUCT_PRICE);
                int quantity = getIntFromCursor(cursor, COLUMN_PRODUCT_QUANTITY);
                String date = getStringFromCursor(cursor, COLUMN_PRODUCT_DATE);

                Product product = new Product(id, category, name, price, quantity, date);
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_PRODUCT_CATEGORY + " FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                String category = getStringFromCursor(cursor, COLUMN_PRODUCT_CATEGORY);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void insertSale(int productId, int quantity, double price, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALE_PRODUCT_ID, productId);
        values.put(COLUMN_SALE_QUANTITY, quantity);
        values.put(COLUMN_SALE_PRICE, price);
        values.put(COLUMN_SALE_AMOUNT, amount);
        values.put(COLUMN_SALE_DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        db.insert(TABLE_SALES, null, values);
        db.close();
    }

    public SaleDetails getSaleDetails() {
        SaleDetails saleDetails = new SaleDetails();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SALES, null);

        double totalAmount = 0.0;

        if (cursor.moveToFirst()) {
            do {
                int productId = getIntFromCursor(cursor, COLUMN_SALE_PRODUCT_ID);
                int quantity = getIntFromCursor(cursor, COLUMN_SALE_QUANTITY);
                double price = getDoubleFromCursor(cursor, COLUMN_SALE_PRICE);
                double amount = getDoubleFromCursor(cursor, COLUMN_SALE_AMOUNT);

                saleDetails.addSaleItem(new SaleItem(productId, quantity, price, amount));
                totalAmount += amount;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        saleDetails.setTotalAmount(totalAmount);
        return saleDetails;
    }

    private int getIntFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getInt(index) : 0;
    }

    private double getDoubleFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getDouble(index) : 0.0;
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getString(index) : "";
    }
}



