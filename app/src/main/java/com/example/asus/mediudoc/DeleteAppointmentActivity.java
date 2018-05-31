package com.example.asus.mediudoc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteAppointmentActivity extends AppCompatActivity {
    private DatabaseReference mDocDatabase, mPatientDatabase;
    private FirebaseUser mCurrentUser;
    private String doc_id, apt_id;
    private Button mProfileDeclineReqBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);
        apt_id = getIntent().getStringExtra("apt_id");
        mProfileDeclineReqBtn= (Button)findViewById(R.id.bb);
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mDocDatabase= FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrentUser.getUid()).child("Appointments").child("Request").child(apt_id);
        mPatientDatabase= FirebaseDatabase.getInstance().getReference().child("Patient_Users");

        mProfileDeclineReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDocDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                        }else {
                            Toast.makeText(DeleteAppointmentActivity.this, "Error in declining.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
//    public void deleteApt(View view){
//        FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrentUser.getUid()).child("Appointments").removeValue();
//    }
}
