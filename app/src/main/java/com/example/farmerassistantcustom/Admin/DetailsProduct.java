package com.example.farmerassistantcustom.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Controller.CartController;
import com.example.farmerassistantcustom.Model.Cart;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

public class DetailsProduct extends AppCompatActivity {
    Toolbar toolbar;
    TextView name, qauntity, suppliername, contact;
    Button cart;
    ImageView img;
    Product entity;
    CartController controller;
    Cart cartentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        initUi();
    }

    private void initUi() {
        entity = (Product) getIntent().getSerializableExtra("product");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details of Product");
        name = (TextView) findViewById(R.id.dpname);
        qauntity = (TextView) findViewById(R.id.dprice);
        suppliername = (TextView) findViewById(R.id.dsname);
        contact = (TextView) findViewById(R.id.dcontact);
        img = (ImageView) findViewById(R.id.dimg);
        cart = (Button) findViewById(R.id.btn_addtocart);
        cartentity = new Cart();
        controller = new CartController(this);

        name.setText(entity.getFname());
        Bitmap bitmap = convertBase64ToBitmap(entity.getImg());
        img.setImageBitmap(bitmap);
        suppliername.setText(entity.getName());
        contact.setText(entity.getContact());
        qauntity.setText(entity.getPrice());
        if (Util.gettype(getApplicationContext()).compareTo("User") == 0) {
            cart.setVisibility(View.VISIBLE);
        } else {
            cart.setVisibility(View.GONE);
        }
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartentity = new Cart();
                cartentity.setUid(Util.getSP(getApplicationContext()));
                cartentity.setFid(entity.getFid());
                cartentity.setPid(entity.getPid());
                cartentity.setName(entity.getFname());
                cartentity.setFname(entity.getName());
                cartentity.setUname(Util.getName(getApplicationContext()));
                cartentity.setPrice(entity.getPrice());
                cartentity.setTotal(entity.getPrice());
                cartentity.setImg(entity.getImg());
                cartentity.setQauntity("1");
                controller.addCart(cartentity);
                Toast.makeText(DetailsProduct.this, "Addded!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}