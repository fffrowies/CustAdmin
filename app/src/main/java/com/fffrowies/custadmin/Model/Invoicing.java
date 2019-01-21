package com.fffrowies.custadmin.Model;

public class Invoicing {
    public int id, cust_id;
    public String date, total;

    public Invoicing() {
    }

    public Invoicing(int id, int cust_id, String date, String total) {
        this.id = id;
        this.cust_id = cust_id;
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
