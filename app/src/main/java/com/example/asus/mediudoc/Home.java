package com.example.asus.mediudoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private TextView mFirstname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance(); // important Call

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(current_uid);


        mFirstname = (TextView) findViewById(R.id.firstname);


//Again check if the user is Already Logged in or Not
        if(mAuth.getCurrentUser() == null)
        {
//User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

//Fetch the Display name of current User

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstname").getValue().toString();

                mFirstname.setText(firstname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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


    public void openconnectionsettings (View view) {

        Intent intent = new Intent(this, Doctor_connect_patient.class);
        startActivity(intent);

    }
    public void degree(View view) {
        Intent intent = new Intent(this, UploadDegreeCertificateActivity.class);
        startActivity(intent);    }

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


    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
