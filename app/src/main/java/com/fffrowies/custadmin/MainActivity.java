package com.fffrowies.custadmin;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fffrowies.custadmin.Adapter.SearchAdapter;
import com.fffrowies.custadmin.Database.Database;
import com.fffrowies.custadmin.Model.Customer;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    Database database;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //init View
        recyclerView = findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        materialSearchBar = findViewById(R.id.search_bar);

        //Init DB
        database = new Database(this);

        //Setup search bar
        Resources res = getResources();
        String search = res.getString(R.string.search);
        String owner = res.getString(R.string.owner);

        String hint = res.getString(R.string.search_phrase, search, owner);

        materialSearchBar.setHint(hint);
        materialSearchBar.setCardViewElevation(10);
        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for(String search:suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    //If close Search, just restore default
                    adapter = new SearchAdapter(getBaseContext(), database.getCustomers());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //Init Adapter default set all result
        adapter = new SearchAdapter(this, database.getCustomers());
        recyclerView.setAdapter(adapter);
    }

    private void startSearch(String text) {
        adapter = new SearchAdapter(this, database.getCustomerByName(text));
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = database.getNames();
        materialSearchBar.setLastSuggestions(suggestList);
    }

    public boolean onContextItemSelected(MenuItem item) {

        int card = item.getGroupId();
        Customer customer = adapter.customers.get(card);
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,"Account " + customer.name,Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this,"Edit " + customer.name,Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this,"Delete " + customer.name,Toast.LENGTH_LONG).show();
                database.deleteCustomer(customer.id);
                database.close();
                returnToMainActivity();
//            case 3:
//                Toast.makeText(this,"Call " + card,Toast.LENGTH_LONG).show();
//                break;
//            case 4:
//                Toast.makeText(this,"SMS " + card,Toast.LENGTH_LONG).show();
//                break;
        }
        return super.onContextItemSelected(item);
    }

    private void returnToMainActivity() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}

