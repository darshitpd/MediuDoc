package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorAccountSettings extends AppCompatActivity {

    private TextView openprofile, changepassword, logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        openprofile=(TextView)findViewById(R.id.action_profile);
        changepassword = (TextView)findViewById(R.id.action_changepassword);
        logout = (TextView)findViewById(R.id.action_logout);
        mAuth= FirebaseAuth.getInstance();

        openprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PasswordChange.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
