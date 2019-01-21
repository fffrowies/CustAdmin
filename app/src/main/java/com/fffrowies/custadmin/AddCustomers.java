package com.fffrowies.custadmin;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fffrowies.custadmin.Database.Database;

import java.util.regex.Pattern;

public class AddCustomers extends AppCompatActivity {

    private TextInputLayout til_name, til_address, til_email, til_phone;
    private EditText edt_name, edt_address, edt_email, edt_phone;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        // til References
        til_name = findViewById(R.id.til_name);
        til_address = findViewById(R.id.til_address);
        til_email = findViewById(R.id.til_email);
        til_phone = findViewById(R.id.til_phone);

        // Btn Reference
        Button btnAdd = findViewById(R.id.btn_add);

        // edt References
        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);

        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_name.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_address.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isValidEmail(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isValidPhone(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Database reference
        database = new Database(this);
    }

    private boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
        if (!pattern.matcher(name).matches() || name.length() > 30) {
            til_name.setError("Invalid name");
            return false;
        } else {
            til_name.setError(null);
        }

        return true;
    }

    private boolean isValidAddress(String address) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z,. ]+$");
        if (!pattern.matcher(address).matches() || address.length() > 40) {
            til_address.setError("Invalid address");
            return false;
        } else {
            til_address.setError(null);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til_email.setError("Invalid email");
            return false;
        } else {
            til_email.setError(null);
        }

        return true;
    }

    private boolean isValidPhone(String phone) {
        if (!Patterns.PHONE.matcher(phone).matches()) {
            til_phone.setError("Invalid phone number");
            return false;
        } else {
            til_phone.setError(null);
        }

        return true;
    }

    public void addCustomer(View view) {
        String name = edt_name.getText().toString();
        String address = edt_address.getText().toString();
        String email = edt_email.getText().toString();
        String phone = edt_phone.getText().toString();

        boolean test_name = isValidName(name);
        boolean test_address = isValidAddress(address);
        boolean test_email = isValidEmail(email);
        boolean test_phone = isValidPhone(phone);

        if (test_name && test_address && test_email && test_phone) {
            database.addCustomer(name, address, email, phone);
            returnToMain();
        }
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
