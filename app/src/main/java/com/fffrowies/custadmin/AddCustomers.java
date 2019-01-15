package com.fffrowies.custadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fffrowies.custadmin.Database.Database;

public class AddCustomers extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAddress;
    private EditText edtEmail;
    private EditText edtPhone;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);

        database = new Database(this);
    }

    public void addCustomer(View view) {
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String email = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();

        database.add(name, address, email, phone);
        returnToMain();
    }

    public void cancelAddCustomer(View view) {
        returnToMain();
    }

    private void returnToMain() {
        Intent main = new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(main);
    }
}
