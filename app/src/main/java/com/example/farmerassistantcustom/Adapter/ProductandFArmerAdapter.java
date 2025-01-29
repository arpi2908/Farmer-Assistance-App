package com.example.farmerassistantcustom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Admin.DetailsProduct;
import com.example.farmerassistantcustom.Farmer.AddorUpdateProduct;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;

import java.util.ArrayList;
import java.util.List;

public class ProductandFArmerAdapter extends RecyclerView.Adapter<ProductandFArmerAdapter.myview> {
    List<Product> productList = new ArrayList<>();
    Context context;

    public ProductandFArmerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordernadfarmer, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Product product = productList.get(position);
        holder.price.setText("Price/kg: " + product.getPrice());
        holder.name.setText(product.getFname());
        holder.fname.setText(product.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsProduct.class);
                intent.putExtra("product", productList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView name, price, fname;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_aname);
            price = itemView.findViewById(R.id.item_afname);
            fname = itemView.findViewById(R.id.item_famount);

        }
    }
}
