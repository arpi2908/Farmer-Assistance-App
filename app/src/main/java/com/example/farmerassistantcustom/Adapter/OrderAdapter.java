package com.example.farmerassistantcustom.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Farmer.AddorUpdateProduct;
import com.example.farmerassistantcustom.Farmer.OrderDetails;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Helper;
import com.example.farmerassistantcustom.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.myview> {
    List<Orders> productList = new ArrayList<>();
    Context context;

    public OrderAdapter(List<Orders> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new myview(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, @SuppressLint("RecyclerView") int position) {
        Orders product = productList.get(position);
        holder.price.setText("Total Amount " + product.getAmount());
        holder.name.setText("Name: " + product.getCustname());
        holder.status.setText("Status: " + product.getStatus());
        holder.dt.setText("Booking date: " + product.getDt());
        holder.product.setText(product.getName());
        holder.qty.setText("Qty: " + product.getQauntity());

        String input = product.getPid() + product.getFid() + product.getName() + product.getQauntity() + product.getAmount() + product.getCustname()
                + product.getDt() + product.getFarmerName() + product.getUid() + product.getPreviousblock();
        if (product.getBlock().compareTo(Helper.sha256(input)) == 0) {
            holder.ismaniculate.setText("isMainculated: No");
        } else {
            holder.ismaniculate.setText("isMainculated: Yes");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.gettype(context).compareTo("User") != 0) {
                    Intent intent = new Intent(context, OrderDetails.class);
                    intent.putExtra("orders", productList.get(position));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView name, price, product, status, dt, ismaniculate, qty;


        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_custname);
            price = itemView.findViewById(R.id.item_amount);
            ismaniculate = itemView.findViewById(R.id.item_maniculate);
            product = itemView.findViewById(R.id.item_productname);
            status = itemView.findViewById(R.id.item_orderstatus);
            qty = itemView.findViewById(R.id.item_qty);
            dt = itemView.findViewById(R.id.item_orderdt);
        }
    }
}
