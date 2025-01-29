package com.example.farmerassistantcustom.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.farmerassistantcustom.Farmer.ManageComplaint;
import com.example.farmerassistantcustom.Farmer.ManageProduct;
import com.example.farmerassistantcustom.Farmer.OrdersActivity;
import com.example.farmerassistantcustom.Farmer.TipsActivity;
import com.example.farmerassistantcustom.LoginActivity;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView card1, card2, card3, card4, card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Home");
        setSupportActionBar(toolbar);
        card1 = (CardView) findViewById(R.id.acard1);
        card2 = (CardView) findViewById(R.id.acard2);
        card3 = (CardView) findViewById(R.id.acard3);
        card4 = (CardView) findViewById(R.id.acard4);
        card5 = (CardView) findViewById(R.id.acard5);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewProductandFarmer.class));
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManageComplaint.class));
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewTransactions.class));
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Util.setType(getApplicationContext(), "");
            Util.setSP(getApplicationContext(), "");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}