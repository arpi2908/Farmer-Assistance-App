package com.example.farmerassistantcustom.Farmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Controller.ProductController;
import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Model.Product;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.PermissionUtils;
import com.example.farmerassistantcustom.Util.Util;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddorUpdateProduct extends AppCompatActivity {
    private static final int MY_RESULT_CODE_FILECHOOSER = 8798;
    Toolbar toolbar;
    TextInputEditText name, price;
    ImageView img;
    Button btn;
    String path;
    ProductController controller;
    Product entity;
    ImageView delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_update_product);
        initui();
    }

    private void initui() {
        controller = new ProductController(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product");
        setSupportActionBar(toolbar);
        name = (TextInputEditText) findViewById(R.id.productname_edt);
        price = (TextInputEditText) findViewById(R.id.qauntity_edt);
        img = (ImageView) findViewById(R.id.pimg);
        btn = (Button) findViewById(R.id.postbtn);
        delete = (ImageView) findViewById(R.id.deleteimg);
        entity = (Product) getIntent().getSerializableExtra("product");
        try {

            if (entity.getPid() != null) {
                name.setText(entity.getName());
                price.setText(entity.getPrice());
                img.setImageBitmap(Util.StringToBitMap(entity.getImg()));

                delete.setVisibility(View.VISIBLE);
            } else {


            }
        } catch (Exception e) {

        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.delete(Long.parseLong(entity.getPid()));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (entity.getPid() != null) {
                        if (name.getText().toString().isEmpty()) {
                            Toast.makeText(AddorUpdateProduct.this, "Enter Name", Toast.LENGTH_SHORT).show();
                        } else if (price.getText().toString().isEmpty()) {
                            Toast.makeText(AddorUpdateProduct.this, "Enter Price", Toast.LENGTH_SHORT).show();
                        } else {

                            Product product = new Product();
                            product.setPid(entity.getPid());
                            product.setName(name.getText().toString());
                            product.setPrice(price.getText().toString());
                            if (path != null) {
                                product.setImg(path);
                            } else {
                                product.setImg(entity.getImg());
                            }
                            product.setFid(Util.getSP(getApplicationContext()));
                            product.setFname(Util.getName(getApplicationContext()));
                            controller.update(product);
                            Toast.makeText(AddorUpdateProduct.this, "Product Updated!", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(AddorUpdateProduct.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    } else if (price.getText().toString().isEmpty()) {
                        Toast.makeText(AddorUpdateProduct.this, "Enter Price", Toast.LENGTH_SHORT).show();
                    } else if (path == null) {
                        Toast.makeText(AddorUpdateProduct.this, "Enter Path", Toast.LENGTH_SHORT).show();
                    } else {
                        Product product = new Product();
                        product.setName(name.getText().toString());
                        product.setPrice(price.getText().toString());
                        product.setImg(path);
                        product.setFid(Util.getSP(getApplicationContext()));
                        product.setFname(Util.getName(getApplicationContext()));
                        controller.addproduct(product);
                        Toast.makeText(AddorUpdateProduct.this, "Product Added!", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.requestPermission(AddorUpdateProduct.this, 123)) {
                    select();
                }
            }
        });


    }

    private void select() {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == MY_RESULT_CODE_FILECHOOSER) {
                try {
                    Uri uri = data.getData();
                    Log.d("uri", uri.toString());
                    final InputStream inputStream = getContentResolver().openInputStream(uri);
                    final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                    Log.d("bitmap", imageMap.toString());
                    Bitmap converetdImage = getResizedBitmap(imageMap, 200);
                    path = convertBitmapToBase64(converetdImage);
                    Log.d("base64", path);
                    img.setImageBitmap(imageMap);
                } catch (Exception e) {

                    Log.d("exception", e.toString());
                }

            } else {
//            Snackbar snackbar = Snackbar.make(layout, "Failed Try Again!", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            }

        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}