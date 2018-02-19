package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText email;
    private Button submit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email= (EditText)findViewById(R.id.etEmail);
        submit =(Button)findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                if(userEmail.equals("")){
                    Toast.makeText(ForgetPassword.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgetPassword.this, "Password reset Email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), DoctorLogin.class));
                            }

                            else {
                                Toast.makeText(ForgetPassword.this, "Please enter your registered Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

