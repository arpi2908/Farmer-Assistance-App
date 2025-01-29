package com.example.farmerassistantcustom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.myview> {
    List<Orders> productList = new ArrayList<>();
    Context context;

    public TransactionAdapter(List<Orders> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Orders product = productList.get(position);
        holder.name.setText(product.getName());
        holder.status.setText("Status: " + product.getStatus());
        holder.amount.setText("Amount: " + product.getAmount());
        if (product.getStatus().compareTo("Cancelled") == 0) {
            holder.img.setImageResource(R.drawable.baseline_arrow_upward_24);
        } else {
            holder.img.setImageResource(R.drawable.baseline_arrow_downward_24);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView name, amount, status;
        ImageView img;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_tname);
            amount = itemView.findViewById(R.id.item_tamount);
            status = itemView.findViewById(R.id.item_tstatus);
            img = itemView.findViewById(R.id.item_timg);
        }
    }
}
