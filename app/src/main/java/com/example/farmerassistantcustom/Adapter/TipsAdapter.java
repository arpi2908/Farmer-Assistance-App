package com.example.farmerassistantcustom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.farmerassistantcustom.Admin.AddUpdateTips;
import com.example.farmerassistantcustom.Model.Tips;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.myview> {
    List<Tips> productList = new ArrayList<>();
    Context context;

    public TipsAdapter(List<Tips> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tips, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Tips product = productList.get(position);

        holder.name.setText(product.getTip());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.gettype(context).compareTo("Admin") == 0) {
                    Intent intent = new Intent(context, AddUpdateTips.class);
                    intent.putExtra("tip", productList.get(position));
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
        TextView name;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemtips);

        }
    }
}
