package com.example.farmerassistantcustom.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Adapter.CartAdapter;
import com.example.farmerassistantcustom.Controller.CartController;
import com.example.farmerassistantcustom.Controller.OrderController;
import com.example.farmerassistantcustom.Model.Cart;
import com.example.farmerassistantcustom.Model.Orders;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Helper;
import com.example.farmerassistantcustom.Util.Util;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.clickinterface {
    Toolbar toolbar;
    RecyclerView rv;
    Cart entity;
    CartController controller;
    CartAdapter adapter;
    Button btn;
    Orders orderentity;
    private View alertCardPaymentView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertBuilder;
    ScrollView layout;
    private LinearLayout alertLlDebitCardPayment;
    private EditText alertEtCardNumber, alertEtCVV, alertEtExpiry, alertadd;
    OrderController ordercontroller;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        rv = (RecyclerView) findViewById(R.id.rv_cart);
        entity = new Cart();
        controller = new CartController(this);
        btn = (Button) findViewById(R.id.orders_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Cart> cartList = controller.getcartbyuid(Util.getSP(getApplicationContext()));
        if (cartList.size() > 0) {
            adapter = new CartAdapter(cartList, CartActivity.this, this);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            btn.setVisibility(View.VISIBLE);
        } else {
            rv.setAdapter(null);
            Toast.makeText(getApplicationContext(), "No Item In Cart", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.GONE);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    alertCardPaymentView = getLayoutInflater().inflate(R.layout.payment_alertcart, null);
                    alertBuilder = new AlertDialog.Builder(CartActivity.this);
                    if (alertCardPaymentView.getParent() != null) {
                        ((ViewGroup) alertCardPaymentView.getParent()).removeView(alertCardPaymentView);
                    }
                    alertBuilder.setView(alertCardPaymentView);
                    initCardPaymentAlertUI(alertCardPaymentView, cartList);
                    alertDialog = alertBuilder.create();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                if (!alertDialog.isShowing()) alertDialog = alertBuilder.show();

            }
        });
    }

    @Override
    public void click(String s) {
        if (s.compareTo("true") == 0) {
            onResume();
        }

    }

    private void initCardPaymentAlertUI(View alertCardPaymentView, List<Cart> cartList) {

        alertLlDebitCardPayment = alertCardPaymentView.findViewById(R.id.alertLlDebitCardPayment1);
        alertEtCardNumber = alertCardPaymentView.findViewById(R.id.alertEtCardNumber1);
        alertEtCVV = alertCardPaymentView.findViewById(R.id.alertEtCVV1);
        alertEtExpiry = alertCardPaymentView.findViewById(R.id.alertEtExpiry1);
        alertadd = alertCardPaymentView.findViewById(R.id.alertEtAdd1);
        Button alertBtnPayNow = alertCardPaymentView.findViewById(R.id.alertBtnPayNow1);
        Button alertBtnCancel = alertCardPaymentView.findViewById(R.id.alertBtnCancel1);
        TextView amount_tv = alertCardPaymentView.findViewById(R.id.amount_tv1);
        amount_tv.setVisibility(View.GONE);

//        int tp1 = Integer.parseInt(quantity) * Integer.parseInt(entity.getPrice());
//        amount_tv.setText("Amount: " + String.valueOf(tp1));
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        alertBtnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    if (cartList != null) {
                        for (int i = 0; i < cartList.size(); i++) {
                            String currentDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                            ordercontroller = new OrderController(CartActivity.this);
                            orderentity = new Orders();
                            orderentity.setPid(cartList.get(i).getPid());
                            orderentity.setFid(cartList.get(i).getFid());
                            orderentity.setName(cartList.get(i).getName());
                            orderentity.setQauntity(cartList.get(i).getQauntity());
                            orderentity.setAmount(cartList.get(i).getTotal());
                            orderentity.setCustname(cartList.get(i).getUname());
                            orderentity.setDt(currentDateAndTime);
                            orderentity.setFarmerName(cartList.get(i).getFname());
                            List<Orders> ordersList = ordercontroller.getOrdersbyuid(Util.getSP(CartActivity.this));
                            String previousblock = "Genesis";
                            if (ordersList.size() > 0) {
                                for (int j = 0; j < ordersList.size(); j++) {
                                    previousblock = ordersList.get(j).getBlock();
                                }
                                orderentity.setPreviousblock(previousblock);
                            } else {
                                orderentity.setPreviousblock(previousblock);
                            }
                            ordercontroller.getOrdersbyuid(Util.getSP(getApplicationContext()));
                            orderentity.setUid(cartList.get(i).getUid());
                            orderentity.setStatus("Ordered");
                            String block = Helper.sha256(cartList.get(i).getPid() + cartList.get(i).getFid() + cartList.get(i).getName() +
                                    cartList.get(i).getQauntity() + cartList.get(i).getTotal() + cartList.get(i).getUname() +
                                    currentDateAndTime + cartList.get(i).getFname() + cartList.get(i).getUid() + previousblock);
                            orderentity.setBlock(block);
                            ordercontroller.addOrders(orderentity);
                            controller.delete(Long.parseLong(cartList.get(i).getCartid()));

                        }
                        Toast.makeText(CartActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        onResume();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cart is Empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alertBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private boolean validation() {
        if (alertadd.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(layout, "Enter Address", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;

        } else if (alertEtCardNumber.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(layout, "Enter Card Number", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;

        } else if (alertEtCVV.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(layout, "Enter CVV", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;

        } else if (alertEtExpiry.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(layout, "Enter Expiry Date", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;

        } else {

            return true;

        }
    }
}