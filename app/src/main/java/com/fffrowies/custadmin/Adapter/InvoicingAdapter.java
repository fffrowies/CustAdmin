package com.fffrowies.custadmin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.fffrowies.custadmin.Model.Invoicing;
import com.fffrowies.custadmin.R;

public class InvoicingAdapter extends RecyclerView.Adapter<InvoicingAdapter.MyViewHolder> {

    Context context;
    List<Invoicing> invoicingList;

    public InvoicingAdapter(Context context, List<Invoicing> invoicingList) {
        this.context = context;
        this.invoicingList = invoicingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_holder_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.text_view_date.setText(invoicingList.get(i).getDate());
        myViewHolder.text_view_total.setText("$ " + invoicingList.get(i).getTotal());

    }

    @Override
    public int getItemCount() {
        return invoicingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_view_date;
        TextView text_view_total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_view_date = (TextView)itemView.findViewById(R.id.text_view_date);
            text_view_total = (TextView)itemView.findViewById(R.id.text_view_total);
        }
    }
}
