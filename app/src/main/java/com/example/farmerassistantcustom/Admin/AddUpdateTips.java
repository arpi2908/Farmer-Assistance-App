package com.example.farmerassistantcustom.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farmerassistantcustom.Controller.TipsController;
import com.example.farmerassistantcustom.Model.Tips;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;
import com.google.android.material.textfield.TextInputEditText;

public class AddUpdateTips extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText tip_edt;
    Button btn;
    Tips entity;
    ImageView img;
    TipsController controller;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_tips);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tips");
        img = (ImageView) findViewById(R.id.deleteimg);
        tip_edt = (TextInputEditText) findViewById(R.id.tip_edt);
        btn = (Button) findViewById(R.id.btn_addtip);
        img = (ImageView) findViewById(R.id.deleteimg);
        controller = new TipsController(this);

        try {
            entity = (Tips) getIntent().getSerializableExtra("tip");
            if (entity != null && entity.getTip() != null) {
                tip_edt.setText(entity.getTip());
                img.setVisibility(View.VISIBLE);
            } else {

            }
        } catch (Exception ignored) {

        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.delete(Long.parseLong(entity.getTid()));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tip_edt.getText().toString().isEmpty()) {
                    Toast.makeText(AddUpdateTips.this, "Enter Tip", Toast.LENGTH_SHORT).show();
                } else {
                    if (entity != null && entity.getTip() != null) {
                        Tips tip = new Tips();
                        tip.setTid(String.valueOf(entity.getTid()));
                        tip.setTip(tip_edt.getText().toString());
                        controller.update(tip);
                        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Tips tip = new Tips();
                        tip.setTip(tip_edt.getText().toString());
                        controller.addTips(tip);
                        Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }
}