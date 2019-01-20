package com.fffrowies.custadmin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fffrowies.custadmin.Model.Invoicing;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        //Use Picasso to fetch image
        Picasso.get().load(invoicingList.get(i).getImage_link()).into(myViewHolder.image_view);
        myViewHolder.text_view.setText(invoicingList.get(i).getText());

    }

    @Override
    public int getItemCount() {
        return invoicingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView text_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image_view = (ImageView)itemView.findViewById(R.id.image_view);
            text_view = (TextView)itemView.findViewById(R.id.text_view);
        }
    }
}
