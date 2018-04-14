package com.example.asus.mediudoc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectedPatientList extends AppCompatActivity {

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrent_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_patient_list);
        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("ConnectedList").child(mCurrent_user.getUid());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Connected Patients");



        mUsersList = (RecyclerView) findViewById(R.id.req_users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserSingle, ConnectedPatientList.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserSingle, ConnectedPatientList.UsersViewHolder>(
                UserSingle.class, R.layout.activity_user_request_received_list_single, ConnectedPatientList.UsersViewHolder.class, mUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(ConnectedPatientList.UsersViewHolder usersViewHolder, UserSingle users, int i) {

                usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setUserImage(users.getImage(), getApplicationContext());
                final String user_id = getRef(i).getKey();
                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[] = new CharSequence[]{"Open Profile", "Send Message"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(ConnectedPatientList.this);

                        builder.setTitle("Select Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if (i == 0)
                                {
                                    Intent profileIntent = new Intent(ConnectedPatientList.this, ConnectedPatientProfile.class);
                                    profileIntent.putExtra("user_id", user_id);
                                    startActivity(profileIntent);

                                }

                                if (i == 1)
                                {
                                    Intent chatIntent = new Intent(ConnectedPatientList.this,Chat.class);
                                    chatIntent.putExtra("user_id", user_id);
                                    startActivity(chatIntent);
                                }


                            }
                        }) ;

                        builder.show();


                    }
                });

            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

    }

}
