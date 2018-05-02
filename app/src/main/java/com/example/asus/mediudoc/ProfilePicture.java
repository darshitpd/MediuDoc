package com.example.asus.mediudoc;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
public class ProfilePicture extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        image_profile = (ImageView)findViewById(R.id.profilepicture);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("image").getValue().toString();
                String chat_first_name = dataSnapshot.child("firstname").getValue().toString();
                getSupportActionBar().setTitle(chat_first_name);

                Picasso.with(ProfilePicture.this).load(image).placeholder(R.drawable.default_avatar).into(image_profile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}