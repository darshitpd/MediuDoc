package com.example.asus.mediudoc;

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

public class ConfirmAptListActivity extends AppCompatActivity {
    private RecyclerView mConfirmAptList;
    private DatabaseReference mDocUsersDatabase;
    private FirebaseUser mCurrent_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_apt_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mDocUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrent_user.getUid()).child("Appointments").child("Confirm");

        mConfirmAptList = (RecyclerView) findViewById(R.id.confirm_apt_list);
        mConfirmAptList.setHasFixedSize(true);
        mConfirmAptList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AptSingle, ConfirmAptListActivity.AptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AptSingle, ConfirmAptListActivity.AptViewHolder>(
                AptSingle.class, R.layout.activity_apt_request_received_list_single, ConfirmAptListActivity.AptViewHolder.class, mDocUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(ConfirmAptListActivity.AptViewHolder aptsViewHolder, AptSingle apts, int i) {

                aptsViewHolder.setDisplayName(apts.getName());
                aptsViewHolder.setDate(apts.getDate());

                final String apt_id = getRef(i).getKey();
                aptsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(ConfirmAptListActivity.this, SingleConfirmAppointment.class);
                        profileIntent.putExtra("apt_id", apt_id);
                        startActivity(profileIntent);
                    }
                });

            }
        };
        mConfirmAptList.setAdapter(firebaseRecyclerAdapter);
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
        public void setDate(String date) {

            TextView userDateView = (TextView) mView.findViewById(R.id.apt_single_date);
            userDateView.setText(date);
        }

    }
}
