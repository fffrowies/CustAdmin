package com.fffrowies.custadmin.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.fffrowies.custadmin.Model.Customer;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "customer.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //Function get all customers
    public List<Customer> getCustomers() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Make sure all is column name in your Table
        String[] sqlSelect = { "Id", "Name", "Address", "Email", "Phone" };
        String tableName = "Customers"; // Make sure this is your table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null,
                null, null, null);
        List<Customer> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                customer.setName(cursor.getString(cursor.getColumnIndex("Name")));
                customer.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                customer.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                customer.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));

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
        String[] sqlSelect = { "Name" };
        String tableName = "Customers"; // Make sure this is your table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null,
                null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            } while (cursor.moveToNext());
        }
        return result;
    }

    //Function get customer by name
    public List<Customer> getCustomerByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Make sure all is column name in your Table
        String[] sqlSelect = { "Id", "Name", "Address", "Email", "Phone" };
        String tableName = "Customers"; // Make sure this is your table name

        qb.setTables(tableName);

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
                customer.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                customer.setName(cursor.getString(cursor.getColumnIndex("Name")));
                customer.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                customer.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                customer.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));

                result.add(customer);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String tableName = "Customers"; // Make sure this is your table name
        db.delete(tableName, " id = " + id, null);
    }
}
