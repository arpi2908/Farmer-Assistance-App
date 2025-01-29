package com.example.farmerassistantcustom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Controller.ComplaintController;
import com.example.farmerassistantcustom.Farmer.AddorUpdateProduct;
import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.myview> {
    List<Complain> productList = new ArrayList<>();
    Context context;
    Complain entity;
    ComplaintController controller;
    click click;

    public ComplainAdapter(List<Complain> productList, Context context, click click) {
        this.productList = productList;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Complain product = productList.get(position);
        holder.name.setText(product.getName());
        holder.reply.setText("Reply: " + product.getReply());
        holder.complain.setText("Complaint: "+product.getComplain());
        if (product.getReply().compareTo("") == 0) {
            holder.reply.setVisibility(View.GONE);
        } else {
            holder.reply.setText(product.getReply());
        }
        if (Util.gettype(context).compareTo("Admin") == 0) {
            holder.type.setVisibility(View.VISIBLE);
            holder.type.setText("Type: "+product.getType());
        } else {
            holder.type.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.gettype(context).compareTo("Admin") == 0) {

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_layout);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);
                    EditText edt = dialog.findViewById(R.id.reply_edt);
                    Button btn = dialog.findViewById(R.id.btn_updatereply);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            entity=new Complain();
                            controller = new ComplaintController(context);
                            entity.setCid(product.getCid());
                            entity.setReply(edt.getText().toString());
                            controller.update(entity);
                            click.onclick("true");
                        }
                    });


                    dialog.show();


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView name, complain, reply, type;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_complainername);
            complain = itemView.findViewById(R.id.item_complain);
            reply = itemView.findViewById(R.id.item_reply);
            type = itemView.findViewById(R.id.item_type);
        }
    }

    public static interface click {
        void onclick(String click);
    }
}
