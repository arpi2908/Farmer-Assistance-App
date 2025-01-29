package com.example.farmerassistantcustom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Farmer.AddorUpdateProduct;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myview> {
    List<Product> productList = new ArrayList<>();
    Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Product product = productList.get(position);
        holder.price.setText("Price/kg: " + product.getPrice());
        holder.name.setText(product.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddorUpdateProduct.class);
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
        TextView name, price;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_pname);
            price = itemView.findViewById(R.id.item_pprice);
        }
    }
}
