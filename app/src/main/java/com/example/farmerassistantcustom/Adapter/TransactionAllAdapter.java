package com.example.farmerassistantcustom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionAllAdapter extends RecyclerView.Adapter<TransactionAllAdapter.myview> {
    List<Orders> productList = new ArrayList<>();
    Context context;
    String type;

    public TransactionAllAdapter(List<Orders> productList, Context context,String type) {
        this.productList = productList;
        this.context = context;
        this.type=type;
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
        if (type.compareTo("Buyer")==0){
            holder.name.setText(product.getCustname());
        }else {
            holder.name.setText(product.getFarmerName());
        }

        holder.status.setText("Date&Time: " + product.getDt());
        holder.amount.setText("Amount: " + product.getAmount());

        if (type.compareTo("Seller")==0) {
            if (product.getStatus().compareTo("Cancelled") == 0) {
                holder.img.setImageResource(R.drawable.baseline_arrow_upward_24);
            } else {
                holder.img.setImageResource(R.drawable.baseline_arrow_downward_24);
            }
        }else {
            if (product.getStatus().compareTo("Cancelled") == 0) {
                holder.img.setImageResource(R.drawable.baseline_arrow_downward_24);
            } else {
                holder.img.setImageResource(R.drawable.baseline_arrow_upward_24);
            }
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
