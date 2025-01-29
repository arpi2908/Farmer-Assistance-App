package com.example.farmerassistantcustom.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.farmerassistantcustom.Adapter.TransactionAdapter;
import com.example.farmerassistantcustom.Adapter.TransactionAllAdapter;
import com.example.farmerassistantcustom.Controller.OrderController;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.R;

import java.util.List;

import kotlin.text.UStringsKt;

public class ViewTransactions extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    RecyclerView rv;
    String[] typelist = {"Seller", "Buyer"};
    String type;
    Toolbar toolbar;
    Orders entity;
    OrderController orderController;
    TransactionAllAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);
        rv = (RecyclerView) findViewById(R.id.rv_alltransactions);
        spinner = (Spinner) findViewById(R.id.spinner_transactions);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Transactions");
        spinner.setOnItemSelectedListener(this);
        orderController = new OrderController(this);
        entity = new Orders();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                typelist);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = typelist[position];
        getdata(type);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getdata(type);
    }

    private void getdata(String type) {
        List<Orders> ordersList = orderController.getallOrders();
        if (ordersList != null) {

            adapter = new TransactionAllAdapter(ordersList, ViewTransactions.this, type);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No Transactions", Toast.LENGTH_SHORT).show();
        }
    }


}