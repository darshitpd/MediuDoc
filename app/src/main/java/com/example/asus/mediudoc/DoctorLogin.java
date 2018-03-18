package com.example.asus.mediudoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class DoctorLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button signin;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        signin= (Button)findViewById(R.id.signin);
        email= (EditText)findViewById(R.id.etEmail);
        password= (EditText)findViewById(R.id.etPassword);

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();

                if(!TextUtils.isEmpty(getemail) && !TextUtils.isEmpty(getpassword))
                {
                    mProgressDialog.setTitle("Signing In");
                    mProgressDialog.setMessage("Please wait while we check your credentials");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    callsignin(getemail,getpassword);
                }
                else {
                    Toast.makeText(DoctorLogin.this, "Please enter all the details.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //Now start Sign In Process
//SignIn Process
    private void callsignin(String email,String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Toast.makeText(DoctorLogin.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mProgressDialog.dismiss();
                            Intent i = new Intent(DoctorLogin.this, Home.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });

    }

    public void forgetpassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }
}
