package com.example.farmerassistantcustom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.farmerassistantcustom.Admin.MainActivity;
import com.example.farmerassistantcustom.Db.SqliteDatabase;
import com.example.farmerassistantcustom.Farmer.FarmerHome;
import com.example.farmerassistantcustom.User.UserHome;
import com.example.farmerassistantcustom.Util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] typelist = {"Admin", "Farmer", "User"};
    String type;
    Toolbar toolbar;
    ProgressBar pb;
    TextInputEditText username, pass;
    Button btn, register;
    LinearLayout layout;
    SqliteDatabase sqliteDatabase;

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        if (Util.getSP(getApplicationContext()).compareTo("") != 0) {
            if (Util.gettype(getApplicationContext()).compareTo("Admin") == 0) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else if (Util.gettype(getApplicationContext()).compareTo("User") == 0) {
                startActivity(new Intent(getApplicationContext(), UserHome.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), FarmerHome.class));
                finish();
            }

        } else {
            initui();
        }

    }

    private void initui() {

        spin = (Spinner) findViewById(R.id.spinner_login);
        spin.setOnItemSelectedListener(this);
        sqliteDatabase = new SqliteDatabase(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typelist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        register = findViewById(R.id.registerbtn);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        setSupportActionBar(toolbar);
        btn = (Button) findViewById(R.id.loginbtn);
        pb = (ProgressBar) findViewById(R.id.pb_login);
        username = (TextInputEditText) findViewById(R.id.email_edt);
        pass = (TextInputEditText) findViewById(R.id.pass_edt);
        layout = (LinearLayout) findViewById(R.id.layout_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (username.getText().toString().isEmpty()) {
                    Snackbar.make(layout, "Enter Username", Snackbar.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                } else if (pass.getText().toString().isEmpty()) {
                    Snackbar.make(layout, "Enter Password", Snackbar.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                } else {
                    login();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

    }

    private void login() {
        if (type.compareTo("Admin") == 0) {
            if (username.getText().toString().trim().compareTo("admin") == 0 && pass.getText().toString().trim().compareTo("admin") == 0) {
                Util.setType(getApplicationContext(), "Admin");
                Util.setSP(getApplicationContext(), "1");
                Util.setName(getApplicationContext(), "Admin");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                pb.setVisibility(View.GONE);
                finish();
            } else {
                pb.setVisibility(View.GONE);
                Snackbar.make(layout, "Failed! Wrong Credentials", Snackbar.LENGTH_SHORT).show();
            }
        } else if (type.compareTo("User") == 0) {
            pb.setVisibility(View.VISIBLE);
            if (sqliteDatabase.Ucheckemail(username.getText().toString().trim())) {
                Log.d("check", "working");
                Cursor reult = sqliteDatabase.Ugetdata(username.getText().toString());

                while (reult.moveToNext()) {

                    Log.d("c", reult.getString(4));
                    Log.d("fid", reult.getString(0));
                    String pass1 = reult.getString(4);
                    String name = reult.getString(1);

                    if (pass.getText().toString().compareTo(pass1) == 0) {

                        Util.setSP(getApplicationContext(), reult.getString(0));
                        Util.setType(getApplicationContext(), "User");
                        Util.setName(getApplicationContext(), name);
                        Intent accountsIntent = new Intent(getApplicationContext(), UserHome.class);
                        accountsIntent.putExtra("uid", reult.getInt(0));
                        startActivity(accountsIntent);

                        pb.setVisibility(View.GONE);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(layout, "Email or Password Is Incorrect", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        pb.setVisibility(View.GONE);
                    }


                }
            } else {
                Snackbar snackbar = Snackbar.make(layout, "Email or Password Is Incorrect", Snackbar.LENGTH_SHORT);
                snackbar.show();
                pb.setVisibility(View.GONE);
            }
        } else {
            pb.setVisibility(View.VISIBLE);
            if (sqliteDatabase.Fcheckemail(username.getText().toString().trim())) {
                Log.d("check", "working");
                Cursor reult = sqliteDatabase.Fgetdata(username.getText().toString());

                while (reult.moveToNext()) {

                    Log.d("c", reult.getString(4));
                    Log.d("fid", reult.getString(0));
                    String pass1 = reult.getString(4);
                    String name = reult.getString(1);

                    if (pass.getText().toString().compareTo(pass1) == 0) {
                        Util.setSP(getApplicationContext(), reult.getString(0));
                        Util.setType(getApplicationContext(), "Farmer");
                        Util.setName(getApplicationContext(), name);
                        Intent accountsIntent = new Intent(getApplicationContext(), FarmerHome.class);
                        accountsIntent.putExtra("uid", reult.getInt(0));
                        startActivity(accountsIntent);

                        pb.setVisibility(View.GONE);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(layout, "Email or Password Is Incorrect", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        pb.setVisibility(View.GONE);
                    }


                }
            } else {
                Snackbar snackbar = Snackbar.make(layout, "Email or Password Is Incorrect", Snackbar.LENGTH_SHORT);
                snackbar.show();
                pb.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = typelist[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}