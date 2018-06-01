package com.example.asus.mediudoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, firstname, lastname, password;
    private Button register;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        register= (Button)findViewById(R.id.register);
        email= (EditText)findViewById(R.id.etEmail);
        firstname= (EditText)findViewById(R.id.etFirstName);
        lastname= (EditText)findViewById(R.id.etLastName);
        password= (EditText)findViewById(R.id.etPassword);

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getfirstname = firstname.getText().toString().trim();
                String getlastname = lastname.getText().toString().trim();
                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();

                if(!TextUtils.isEmpty(getfirstname) && !TextUtils.isEmpty(getlastname) && !TextUtils.isEmpty(getemail) && !TextUtils.isEmpty(getpassword))
                {
                    mProgressDialog.setTitle("Registering User");
                    mProgressDialog.setMessage("Please wait while we check your credentials");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    callregister(getfirstname, getlastname, getemail,getpassword);
                }
                else {
                    Toast.makeText(DoctorRegister.this, "Please enter all the details.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void callregister(final String firstname, final String lastname, final String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mProgressDialog.dismiss();
                            FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                            String uid= current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(uid);
                            HashMap<String, String> userMap= new HashMap<>();
                            userMap.put("firstname",firstname);
                            userMap.put("lastname", lastname);
                            userMap.put("specialist", "");
                            userMap.put("image", "default");
                            userMap.put("thumbnail", "default");
                            userMap.put("dob", "");
                            userMap.put("address", "");
                            userMap.put("gender", "");
                            userMap.put("pincode", "");
                            userMap.put("mobile", "");
                            userMap.put("email", email);
                            userMap.put("experience","");
                            userMap.put("degree_certificate","");
                            userMap.put("description", "");
                            userMap.put("working","");

                            mDatabase.setValue(userMap);



                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(DoctorRegister.this, "Registration Success. Please continue",
                                    Toast.LENGTH_SHORT).show();
                            userProfile();
                            startActivity(new Intent(getApplicationContext(), UploadDegreeCertificateActivity.class));

                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(DoctorRegister.this, "Registration failed. Please try again later",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    //Set User Display Name
    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(firstname.getText().toString().trim())
//.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                            }
                        }
                    });
        }
    }

    public void opensigninpage(View view){
        Intent intent = new Intent(this, DoctorLogin.class);
        startActivity(intent);

    }

}


