package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance(); // important Call



//Again check if the user is Already Logged in or Not
        if(mAuth.getCurrentUser() == null)
        {
//User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

//Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }
    public void appointments(View view) {
        Intent intent = new Intent(this, Appointments.class);
        startActivity(intent);    }

    public void healthfeed(View view) {
        Intent intent = new Intent(this, HealthFeed.class);
        startActivity(intent);    }
    public void openrequestlist(View view){
        Intent intent = new Intent(this, ReceivedRequestListActivity.class);
        startActivity(intent);
    }
    public void openconnecteduserslist(View view){
        Intent intent = new Intent(this, ConnectedPatientList.class);
        startActivity(intent);
    }

//    public void myhealth(View view) {
//        Intent intent = new Intent(this, MyHealth.class);
//        startActivity(intent);    }
//


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_accountsettings:
                // Code to run when the settings item is clicked
                Intent i = new Intent(this, DoctorAccountSettings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
