package com.example.farmerassistantcustom.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerassistantcustom.Controller.CartController;
import com.example.farmerassistantcustom.Farmer.OrderDetails;
import com.example.farmerassistantcustom.Model.Cart;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.myview> {
    List<Cart> productList = new ArrayList<>();
    Context context;
    Cart entity;
    CartController controller;
    clickinterface clickinterface;


    public CartAdapter(List<Cart> productList, Context context, clickinterface clickinterface) {
        this.productList = productList;
        this.context = context;
        this.clickinterface = clickinterface;
    }


    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        Cart product = productList.get(position);
        holder.price.setText("Total Amount: " + product.getTotal());
        holder.name.setText("Name: " + product.getName());
        holder.quantity.setText("Quantity: " + product.getQauntity());
        holder.img.setImageBitmap(Util.StringToBitMap(product.getImg()));

        holder.deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity = new Cart();
                controller = new CartController(context);
                controller.delete(Long.parseLong(product.getCartid()));
                clickinterface.click("true");

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.item_cartselection);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);

                EditText edt = dialog.findViewById(R.id.cart_qauntity);
                TextView total = dialog.findViewById(R.id.cart_total);
                Button btn = dialog.findViewById(R.id.submit_cart);
                edt.setText(product.getQauntity());
                long totalsum = Long.parseLong(product.getPrice()) * Long.parseLong(edt.getText().toString());

                edt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                      long totalsum = (Long.parseLong(product.getPrice()) * Long.parseLong(edt.getText().toString()));
                        total.setText("Total Amount is: 0");
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (count != 0) {
                            long totalsum = (Long.parseLong(product.getPrice()) * Long.parseLong(edt.getText().toString()));
                            total.setText("Total Amount is: " + totalsum);
                        } else {
                            total.setText("Total Amount is: 0");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                total.setText("Total Amount is: " + totalsum);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edt.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter Qauntity", Toast.LENGTH_SHORT).show();
                        } else if (edt.getText().toString().compareTo("0") == 0) {
                            Toast.makeText(context, "Quantity cant be zero", Toast.LENGTH_SHORT).show();
                        } else {
                            controller = new CartController(context);
                            entity = new Cart();
                            entity.setCartid(product.getCartid());
                            entity.setQauntity(edt.getText().toString());
                            entity.setTotal(String.valueOf(Long.parseLong(product.getPrice()) * Long.parseLong(edt.getText().toString())));
                            controller.update(entity);
                            clickinterface.click("true");
                            dialog.dismiss();
                        }
                    }
                });


                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myview extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView deleteimg, img;

        public myview(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_cartpname);
            price = itemView.findViewById(R.id.item_cartprice);

            quantity = itemView.findViewById(R.id.item_cartquantity);
            deleteimg = itemView.findViewById(R.id.item_cartdelete);
            img = itemView.findViewById(R.id.item_cartimg);
        }
    }

    public interface clickinterface {
        void click(String s);

    }
}
