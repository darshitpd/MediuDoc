package com.example.asus.mediudoc;

import android.content.Context;
import android.content.Intent;
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

public class RequestedAptListActivity extends AppCompatActivity {

    private RecyclerView mReqAptList;
    private DatabaseReference mDocUsersDatabase;
    private FirebaseUser mCurrent_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_apt_list);

        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mDocUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrent_user.getUid()).child("Appointments").child("Request");

        mReqAptList = (RecyclerView) findViewById(R.id.req_apt_list);
        mReqAptList.setHasFixedSize(true);
        mReqAptList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AptSingle, RequestedAptListActivity.AptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AptSingle, AptViewHolder>(
                AptSingle.class, R.layout.activity_apt_request_received_list_single, RequestedAptListActivity.AptViewHolder.class, mDocUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(RequestedAptListActivity.AptViewHolder aptsViewHolder, AptSingle apts, int i) {

                aptsViewHolder.setDisplayName(apts.getName());

//                final String user_id = getRef(i).getKey();
//                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent profileIntent = new Intent(RequestedAptListActivity.this, RequestAcceptProfile.class);
//                        profileIntent.putExtra("user_id", user_id);
//                        startActivity(profileIntent);
//                    }
//                });

            }
        };
        mReqAptList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class AptViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public AptViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.apt_single_name);
            userNameView.setText(name);
        }

    }
}
