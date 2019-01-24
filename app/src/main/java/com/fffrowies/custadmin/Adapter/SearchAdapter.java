package com.fffrowies.custadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.fffrowies.custadmin.Interface.ICustomerClickListener;
import com.fffrowies.custadmin.InvoicingActivity;
import com.fffrowies.custadmin.Model.Customer;
import com.fffrowies.custadmin.R;

class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {

    public TextView name, address, email, phone;
    public CardView root_view;

    ICustomerClickListener customerClickListener;

    public void setCustomerClickListener(ICustomerClickListener customerClickListener) {
        this.customerClickListener = customerClickListener;
    }

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        root_view = itemView.findViewById(R.id.root_view);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        email = itemView.findViewById(R.id.email);
        phone = itemView.findViewById(R.id.phone);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //TODO use references to Strings.xml

        menu.setHeaderTitle("Select The Action");

        menu.add(this.getAdapterPosition(), 0, 0, "Call");
        menu.add(this.getAdapterPosition(), 1, 1, "SMS");
        menu.add(this.getAdapterPosition(), 2, 2, "Whatsapp");
        menu.add(this.getAdapterPosition(), 3, 3, "Edit");
        menu.add(this.getAdapterPosition(), 4, 4, "Delete");
    }

    @Override
    public void onClick(View v) {
        customerClickListener.onCustomerClick(v, getAdapterPosition());
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

//    private static final String TAG = SearchAdapter.class.getSimpleName();

    private Context context;
    public List<Customer> customers;

    public SearchAdapter(Context context, List<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.name.setText(customers.get(position).getName());
        holder.address.setText(customers.get(position).getAddress());
        holder.email.setText(customers.get(position).getEmail());
        holder.phone.setText(customers.get(position).getPhone());

//        Log.i(TAG, "Position: " + position);

        if (position % 2 == 0) {
            holder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));
        }

        holder.setCustomerClickListener(new ICustomerClickListener() {
            @Override
            public void onCustomerClick(View view, int position) {
                Intent invoicing_intent = new Intent(context.getApplicationContext(), InvoicingActivity.class);

                invoicing_intent.putExtra("id", customers.get(position).getId());
                invoicing_intent.putExtra("name", customers.get(position).getName());
                invoicing_intent.putExtra("address", customers.get(position).getAddress());
                invoicing_intent.putExtra("email", customers.get(position).getEmail());
                invoicing_intent.putExtra("phone", customers.get(position).getPhone());

                context.startActivity(invoicing_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
