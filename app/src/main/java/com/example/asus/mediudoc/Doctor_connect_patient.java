package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Doctor_connect_patient extends AppCompatActivity {

    private TextView ReceivedRequestList,ConnectedPatientslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_connect_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Connections");

        ReceivedRequestList = (TextView) findViewById(R.id.action_openReceivedRequestlist);
        ConnectedPatientslist = (TextView) findViewById(R.id.action_openconnectedPatientslist);


        ReceivedRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReceivedRequestListActivity.class));
            }
        });

        ConnectedPatientslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ConnectedPatientList.class));
            }
        });
    }
}
