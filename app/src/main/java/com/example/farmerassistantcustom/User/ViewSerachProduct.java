package com.example.farmerassistantcustom.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Adapter.ProductAdapter;
import com.example.farmerassistantcustom.Adapter.ViewProductAdapter;
import com.example.farmerassistantcustom.Controller.ProductController;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;

import java.util.List;

public class ViewSerachProduct extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv;
    EditText edt;
    ImageView img;
    Product entity;
    ProductController controller;
    ViewProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_serach_product);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("View Products");
        rv = (RecyclerView) findViewById(R.id.rv_viewproducts);
        edt = (EditText) findViewById(R.id.vproductname_edt);
        img = (ImageView) findViewById(R.id.search_img);
        entity = new Product();
        controller = new ProductController(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Product Found", Toast.LENGTH_SHORT).show();
                } else {
                    getProduct(edt.getText().toString());
                }
            }
        });

    }

    private void getProduct(String name) {
        List<Product> productList = controller.getallProductwithFarmer(name);
        if (productList.size() > 0) {
            adapter = new ViewProductAdapter(productList, ViewSerachProduct.this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No product Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> productList = controller.getProductwithFarmer();
        if (productList.size() > 0) {
            adapter = new ViewProductAdapter(productList, ViewSerachProduct.this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No product Found", Toast.LENGTH_SHORT).show();
        }

    }
}