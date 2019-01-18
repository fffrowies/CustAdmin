package com.fffrowies.custadmin.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.fffrowies.custadmin.Interface.ICustomerClickListener;
import com.fffrowies.custadmin.Model.Customer;
import com.fffrowies.custadmin.R;

class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {

    //TODO check if it can be private
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

        menu.add(this.getAdapterPosition(), 0, 0, "Account");
        menu.add(this.getAdapterPosition(), 1, 4, "Edit");
        menu.add(this.getAdapterPosition(), 2, 5, "Delete");
        menu.add(this.getAdapterPosition(), 3, 1, "Call");
        menu.add(this.getAdapterPosition(), 4, 2, "SMS");
        menu.add(this.getAdapterPosition(), 5, 3, "Whatsapp");
    }

    @Override
    public void onClick(View v) {
        customerClickListener.onCustomerClick(v, getAdapterPosition());
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

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

        if (position % 2 == 0) {
            holder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));
        }

        holder.setCustomerClickListener(new ICustomerClickListener() {
            @Override
            public void onCustomerClick(View view, int position) {
                Toast.makeText(context, ""+customers.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
