package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class RequestAcceptProfile extends AppCompatActivity {


    private ImageView mProfileImage;
    private TextView mProfileName;
    private Button mProfileAcceptReqBtn, mProfileDeclineReqBtn;

    private DatabaseReference mUsersDatabase;
    private DatabaseReference mConnectReqDatabase;
    private DatabaseReference mDoctorInfoDatabase;
    private DatabaseReference mConnectDatabase;

    private FirebaseUser mCurrent_user;

    private int mCurrent_state;  // 3= Request received  4= Connected


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_accept_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String user_id = getIntent().getStringExtra("user_id");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(user_id);
        mConnectReqDatabase = FirebaseDatabase.getInstance().getReference().child("Connect_Req");
        mConnectDatabase = FirebaseDatabase.getInstance().getReference().child("ConnectedList");
        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mDoctorInfoDatabase=FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrent_user.getUid());

        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mProfileAcceptReqBtn = (Button) findViewById(R.id.profile_accept_req_btn);
        mProfileDeclineReqBtn=(Button)findViewById(R.id.profile_decline_req_btn);

        mCurrent_state= 3;

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstname").getValue().toString();
                String lastname = dataSnapshot.child("lastname").getValue().toString();
                String display_name = firstname+" "+lastname;
                String image = dataSnapshot.child("image").getValue().toString();

                mProfileName.setText(display_name);

                Picasso.with(RequestAcceptProfile.this).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProfileAcceptReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrent_state==3){
                    final String currentdate = DateFormat.getDateTimeInstance().format(new Date());
                    mConnectDatabase.child(mCurrent_user.getUid()).child(user_id).child("date").setValue(currentdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       mConnectDatabase.child(user_id).child(mCurrent_user.getUid()).child("date").setValue(currentdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   mUsersDatabase.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                           String firstname = dataSnapshot.child("firstname").getValue().toString();
                                           String lastname = dataSnapshot.child("lastname").getValue().toString();
                                           String display_name = firstname+" "+lastname;
                                           String image = dataSnapshot.child("image").getValue().toString();

                                           mConnectDatabase.child(mCurrent_user.getUid()).child(user_id).child("name").setValue(display_name);
                                           mConnectDatabase.child(mCurrent_user.getUid()).child(user_id).child("image").setValue(image);
                                       }

                                       @Override
                                       public void onCancelled(DatabaseError databaseError) {

                                       }
                                   });

                                   mDoctorInfoDatabase.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                           String firstname = dataSnapshot.child("firstname").getValue().toString();
                                           String lastname = dataSnapshot.child("lastname").getValue().toString();
                                           String display_name = firstname+" "+lastname;
                                           String image = dataSnapshot.child("image").getValue().toString();

                                           mConnectDatabase.child(user_id).child(mCurrent_user.getUid()).child("name").setValue(display_name);
                                           mConnectDatabase.child(user_id).child(mCurrent_user.getUid()).child("image").setValue(image);
                                       }

                                       @Override
                                       public void onCancelled(DatabaseError databaseError) {

                                       }
                                   });

                                   mConnectReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue();
                                   mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue();
                                   Toast.makeText(RequestAcceptProfile.this, "Connected", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(RequestAcceptProfile.this, ConnectedPatientList.class);
                                   startActivity(intent);
                               }
                           }
                       });

                        }
                    });

                }
            }
        });

        mProfileDeclineReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mCurrent_state = 0;
                                        Toast.makeText(RequestAcceptProfile.this, "Declined.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RequestAcceptProfile.this, "Error in declining.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
