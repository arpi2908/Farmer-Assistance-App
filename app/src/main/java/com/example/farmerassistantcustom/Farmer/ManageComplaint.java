package com.example.farmerassistantcustom.Farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.farmerassistantcustom.Adapter.ComplainAdapter;
import com.example.farmerassistantcustom.Controller.ComplaintController;
import com.example.farmerassistantcustom.Model.Complain;
import com.example.farmerassistantcustom.R;
import com.example.farmerassistantcustom.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageComplaint extends AppCompatActivity implements ComplainAdapter.click {
    Toolbar toolbar;
    RecyclerView rv;
    FloatingActionButton fab;
    ComplaintController controller;
    Complain entity;
    ComplainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_complaint);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Complaints");
        setSupportActionBar(toolbar);
        rv = (RecyclerView) findViewById(R.id.rv_complaint);
        fab = (FloatingActionButton) findViewById(R.id.fab_addcomplaint);

        if (Util.gettype(getApplicationContext()).compareTo("Admin") == 0) {
            fab.setVisibility(View.GONE);
        }
        controller = new ComplaintController(this);
        entity = new Complain();
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddComplaint.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Util.gettype(getApplicationContext()).compareTo("Farmer") == 0) {
            List<Complain> complianlist = controller.getComplaint(Util.getSP(getApplicationContext()), Util.gettype(getApplicationContext()));
            if (complianlist != null) {
                adapter = new ComplainAdapter(complianlist, ManageComplaint.this,this);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No Complaint Found", Toast.LENGTH_SHORT).show();
            }
        } else if (Util.gettype(getApplicationContext()).compareTo("Admin") == 0) {

            List<Complain> complianlist = controller.getallComplaint();
            if (complianlist != null) {
                adapter = new ComplainAdapter(complianlist,ManageComplaint.this,this);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No Complaint Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            List<Complain> complianlist = controller.getComplaint(Util.getSP(getApplicationContext()), Util.gettype(getApplicationContext()));
            if (complianlist != null) {
                adapter = new ComplainAdapter(complianlist, ManageComplaint.this,this);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No Complaint Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onclick(String click) {
        if (click.compareTo("true")==0){
            onResume();
        }
    }
}