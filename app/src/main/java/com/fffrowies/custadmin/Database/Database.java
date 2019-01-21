package com.fffrowies.custadmin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.fffrowies.custadmin.Model.Customer;
import com.fffrowies.custadmin.Model.Invoicing;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    // Table Name
    public static final String CUSTOMERS_TABLE_NAME = "Customers";
    public static final String INVOICES_TABLE_NAME = "Invoices";

    // CUSTOMERS_TABLE_NAME Table columns
    public static final String _ID = "Id";
    public static final String NAME = "Name";
    public static final String ADDRESS = "Address";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Phone";

    // INVOICES_TABLE_NAME Table columns
    public static final String _INV_ID = "Inv_Id";
    public static final String CUST_ID = "Cust_Id";
    public static final String DATE = "Date";
    public static final String TOTAL = "Total";

    // Database Information
    static final String DB_NAME = "customer.db";

    // Database Version
    static final int DB_VER = 2;

    private SQLiteDatabase database;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
//        setForcedUpgradeVersion(2);
    }

    //Function get all customers
    public List<Customer> getCustomers() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Make sure all is column name in your Table
        String[] sqlSelect = { "Id", "Name", "Address", "Email", "Phone" };

        qb.setTables(CUSTOMERS_TABLE_NAME);
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

        qb.setTables(CUSTOMERS_TABLE_NAME);
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

        qb.setTables(CUSTOMERS_TABLE_NAME);

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
        db.delete(CUSTOMERS_TABLE_NAME, _ID + " = " + id, null);
    }

    public void addCustomer(String name, String address, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(ADDRESS, address);
        contentValues.put(EMAIL, email);
        contentValues.put(PHONE, phone);

        db.insert(CUSTOMERS_TABLE_NAME, null, contentValues);
    }

    public void updateCustomer(int _id, String name, String address, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(ADDRESS, address);
        contentValues.put(EMAIL, email);
        contentValues.put(PHONE, phone);

        db.update(CUSTOMERS_TABLE_NAME, contentValues, this._ID + " = " + _id, null);
    }

    //// ACCOUNT //////////////////////////////////////////////////////////////////////////////////

    //Function get invoices by customer id
    public List<Invoicing> getInvoicesByCustomerId(int cust_id) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Convert argument cust_id to string to use in SELECT clause
        String myCustId = Integer.toString(cust_id);

        //Make sure all is column name in your Table
        String[] sqlSelect = { _INV_ID, CUST_ID, DATE, TOTAL };

        qb.setTables(INVOICES_TABLE_NAME);

        String z = qb.getTables();

        //This will like query : SELECT * FROM Invoices WHERE Cust_Id LIKE %pattern%
        Cursor cursor = qb.query(db, sqlSelect, "Cust_Id LIKE ?", new String[]{"%"+myCustId+"%"},
                null, null, null);

        // Cursor cursor = db.rawQuery("SELECT * FROM " + INVOICES_TABLE_NAME, null);

        List<Invoicing> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Invoicing invoice = new Invoicing();
                invoice.setId(cursor.getInt(cursor.getColumnIndex(_INV_ID)));
                invoice.setCust_id(cursor.getInt(cursor.getColumnIndex(CUST_ID)));
                invoice.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                invoice.setTotal(cursor.getString(cursor.getColumnIndex(TOTAL)));

                result.add(invoice);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
