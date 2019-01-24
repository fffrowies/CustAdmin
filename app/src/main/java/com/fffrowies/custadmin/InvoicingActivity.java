package com.fffrowies.custadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.fffrowies.custadmin.Adapter.InvoicingAdapter;
import com.fffrowies.custadmin.Database.Database;
import com.fffrowies.custadmin.Model.Invoicing;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;

public class InvoicingActivity extends AppCompatActivity {

    private boolean isShowingCardHeaderShadow;
    List<Invoicing> invoicingList = new ArrayList<>();

    private int id;
    private String name, address, email, phone;
    private TextView card_name, card_address, card_email, card_phone;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicing);

        //Init DB
        database = new Database(this);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");

        generateModelList(id);

        final MaxHeightRecyclerView rv = (MaxHeightRecyclerView)findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new InvoicingAdapter(this, invoicingList));

        rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));

        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;

                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = true;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
                else {
                    isShowingCardHeaderShadow = false;
                    showOrHideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = (AdvancedNestedScrollView)findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && oldScrollY > 0) {
                    //Reset the RecyclerView's scroll position each time the card return to its starting position
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });

        //Card TextView References
        card_name = findViewById(R.id.card_name);
        card_address = findViewById(R.id.card_address);
        card_email = findViewById(R.id.card_email);
        card_phone = findViewById(R.id.card_phone);

        card_name.setText(name);
        card_address.setText(address);
        card_email.setText(email);
        card_phone.setText(phone);
    }

    private void showOrHideView(View view, boolean isShow) {
        view.animate().alpha(isShow ? 1f:0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    private void generateModelList(int id) {

        invoicingList = database.getInvoicesByCustomerId(id);
    }

    public void billInvoice(View view) {

        Toast.makeText(this, "Llamado a activity para crear factura", Toast.LENGTH_SHORT).show();

//        Intent invoicing_intent = new Intent(this, InvoicingActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        invoicing_intent.putExtra("id", id);
//        invoicing_intent.putExtra("name", name);
//        invoicing_intent.putExtra("address", address);
//        invoicing_intent.putExtra("email", email);
//        invoicing_intent.putExtra("phone", phone);
//
//        startActivity(invoicing_intent);
    }
}
