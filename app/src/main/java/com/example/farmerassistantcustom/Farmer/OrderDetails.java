package com.example.farmerassistantcustom.Farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Controller.OrderController;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

public class OrderDetails extends AppCompatActivity {
    Toolbar toolbar;
    Orders entity;
    TextView pname, qauntity, amount, customername, dt;
    Spinner spinner;
    String[] statuslist = {"Ordered", "Dispatched", "Delivered", "Cancelled"};
    String status;
    Button btn;
    OrderController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Orders Details");
        setSupportActionBar(toolbar);
        entity = (Orders) getIntent().getSerializableExtra("orders");
        controller = new OrderController(this);
        pname = (TextView) findViewById(R.id.orderproduct);
        qauntity = findViewById(R.id.orderquantity);
        amount = (TextView) findViewById(R.id.orderamount);
        customername = (TextView) findViewById(R.id.ordercustomername);
        dt = (TextView) findViewById(R.id.orderbookingdt);
        spinner = (Spinner) findViewById(R.id.spinner_order);
        btn = (Button) findViewById(R.id.btn_orderstatus);

        pname.setText(entity.getName());
        qauntity.setText(entity.getQauntity());
        amount.setText(entity.getAmount());
        customername.setText(entity.getCustname());
        dt.setText(entity.getDt());
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statuslist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        if (entity.getStatus().compareTo("Ordered") == 0) {
            spinner.setSelection(0);
        } else if (entity.getStatus().compareTo("Dispatched") == 0) {
            spinner.setSelection(1);
        } else if (entity.getStatus().compareTo("Delivered") == 0) {
            spinner.setSelection(2);
        } else if (entity.getStatus().compareTo("Cancelled") == 0) {
            spinner.setSelection(3);
        } else {

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = statuslist[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (Util.gettype(getApplicationContext()).compareTo("Admin") == 0) {
            btn.setVisibility(View.GONE);
        } else if (Util.gettype(getApplicationContext()).compareTo("Farmer") == 0) {
            btn.setVisibility(View.VISIBLE);
        } else {

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders orders = new Orders();
                orders.setOid(entity.getOid());
                orders.setStatus(status);
                controller.update(orders);
                Toast.makeText(getApplicationContext(), "Status Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}