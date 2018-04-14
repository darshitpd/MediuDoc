package com.example.asus.mediudoc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordChange extends AppCompatActivity {

    private EditText newpassword,email,password;
    private Button changepassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");

        email= (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        newpassword = (EditText)findViewById(R.id.etNewpassword);
        changepassword = (Button)findViewById(R.id.changepassword);
        mAuth=FirebaseAuth.getInstance();

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail= email.getText().toString().trim();
                String userpass = password.getText().toString().trim();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(useremail, userpass);


// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PasswordChange.this, "reauthenticated", Toast.LENGTH_SHORT).show();
                                    String newpass = newpassword.getText().toString().trim();
                                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(PasswordChange.this, "password changed", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(PasswordChange.this, "password not changed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(PasswordChange.this, "not reauthenticated", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}

