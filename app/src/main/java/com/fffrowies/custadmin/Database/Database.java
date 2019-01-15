package com.fffrowies.custadmin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.fffrowies.custadmin.Model.Customer;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    ////////////////////////////////////////////////////
    // Table Name
    public static final String TABLE_NAME = "Customers";

    // Table columns
    public static final String _ID = "Id";
    public static final String NAME = "Name";
    public static final String ADDRESS = "Address";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Phone";

    // Database Information
    static final String DB_NAME = "customer.db";

    // Database Version
    static final int DB_VER = 1;

    private SQLiteDatabase database;
    ////////////////////////////////////////////////////

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //Function get all customers
    public List<Customer> getCustomers() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Make sure all is column name in your Table
        String[] sqlSelect = { "Id", "Name", "Address", "Email", "Phone" };

        qb.setTables(TABLE_NAME);
        Cursor cursor = qb.query(db, sqlSelect, null, null,
                null, null, null);
        List<Customer> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                customer.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                customer.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                customer.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                customer.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));

                result.add(customer);
            } while (cursor.moveToNext());
        }
        return result;
    }

    //Function get all customer's name
    public List<String> getNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Make sure all is column name in your Table
        String[] sqlSelect = { NAME };

        qb.setTables(TABLE_NAME);
        Cursor cursor = qb.query(db, sqlSelect, null, null,
                null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(NAME)));
            } while (cursor.moveToNext());
        }
        return result;
    }

    //Function get customer by name
    public List<Customer> getCustomerByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Make sure all is column name in your Table
        String[] sqlSelect = { _ID, NAME, ADDRESS, EMAIL, PHONE };

        qb.setTables(TABLE_NAME);

        //If you want to get extract name, just change
//        Cursor cursor = qb.query(db, sqlSelect, "Name = ?", new String[]{name},
//                null, null, null);

        //This will like query : SELECT * FROM Customers WHERE Name LIKE %pattern%
        Cursor cursor = qb.query(db, sqlSelect, "Name LIKE ?", new String[]{"%"+name+"%"},
                null, null, null);
        List<Customer> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                customer.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                customer.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                customer.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                customer.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));

                result.add(customer);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = " + id, null);
    }

    public void add(String name, String address, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(ADDRESS, address);
        contentValues.put(EMAIL, email);
        contentValues.put(PHONE, phone);

        db.insert(TABLE_NAME, null, contentValues);
    }
}
