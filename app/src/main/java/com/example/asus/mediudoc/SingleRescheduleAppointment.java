package com.example.asus.mediudoc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SingleRescheduleAppointment extends AppCompatActivity {

    private DatabaseReference mDatabase, mDocDatabase, mPatientDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgressDialog;
    private TextView mReason, mSelectDate, mSelectTime, mPatient_name, mStatus;
    private Button mConfirm;
    private String patient_id, apt_id;
    private String reason, date, time, patientName, status, doctor_id,doctorName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_reschedule_appointment);


        apt_id = getIntent().getStringExtra("apt_id");
        mReason= (TextView)findViewById(R.id.tvReason);
        mSelectDate= (TextView)findViewById(R.id.tvDate);
        mSelectTime=(TextView)findViewById(R.id.tvTime);
        mPatient_name=(TextView)findViewById(R.id.tvPatientName);
        mStatus= (TextView)findViewById(R.id.tvStatus);
        mConfirm= (Button)findViewById(R.id.btConfirmApt);
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Appointments").child(apt_id);
        mDocDatabase= FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrentUser.getUid()).child("Appointments").child("Request").child(apt_id);
        mPatientDatabase= FirebaseDatabase.getInstance().getReference().child("Patient_Users");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reason= dataSnapshot.child("reason").getValue().toString();
                date= dataSnapshot.child("date").getValue().toString();
                time= dataSnapshot.child("time").getValue().toString();
                patientName= dataSnapshot.child("patient_name").getValue().toString();
                status= dataSnapshot.child("status").getValue().toString();
                patient_id= dataSnapshot.child("patient_id").getValue().toString();
                doctor_id=dataSnapshot.child("doctor_id").getValue().toString();
                doctorName= dataSnapshot.child("doctor_name").getValue().toString();

                mPatient_name.setText(patientName);
                mReason.setText(reason);
                mStatus.setText(status);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();

            }
        });


        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SingleRescheduleAppointment.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mSelectDate.setText(date);
                //mUserDatabase.child("dob").setValue(date);
            }
        };

        mSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SingleRescheduleAppointment.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener, mHour, mMinute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {

                String time= hr+ ":"+ min;
                mSelectTime.setText(time);
            }
        };

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date= mSelectDate.getText().toString();
                time= mSelectTime.getText().toString();
                if(date.equals("Select Date") || time.equals("Select Time")){
                    Toast.makeText(SingleRescheduleAppointment.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                }else {

                    mDatabase.child("date").setValue(date);
                    mDatabase.child("time").setValue(time);
                    mDatabase.child("status").setValue("rescheduled");
                    mDocDatabase.removeValue();

                    mPatientDatabase.child(patient_id).child("Appointments").child("Request").child(apt_id).removeValue();

                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("reason").setValue(reason);
                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("date").setValue(date);
                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("time").setValue(time);
                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("doctor_id").setValue(doctor_id);
                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("doctor_name").setValue(doctorName);
                    mPatientDatabase.child(patient_id).child("Appointments").child("Reschedule").child(apt_id).child("status").setValue("rescheduled");

                    startActivity(new Intent(SingleRescheduleAppointment.this, Appointments.class));
                }
            }
        });

    }
}
