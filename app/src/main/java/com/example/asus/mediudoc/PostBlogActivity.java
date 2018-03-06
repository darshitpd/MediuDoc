package com.example.asus.mediudoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostBlogActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitBtn;
    private Uri mImageUri=null;
    private static final int Gallery_request=1;
    private StorageReference mStorage;
    private FirebaseUser mCurrent_user;
    private DatabaseReference mDatabase;
    private DatabaseReference mDoctorInfoDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blog);
        mStorage= FirebaseStorage.getInstance().getReference();
        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Feed");
        mDoctorInfoDatabase=FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrent_user.getUid());
        mProgress=new ProgressDialog(this);
        mSelectImage =(ImageButton)findViewById(R.id.addImageButton);
        mPostTitle=(EditText)findViewById(R.id.etTitle);
        mPostDesc=(EditText)findViewById(R.id.etDescription);
        mSubmitBtn=(Button)findViewById(R.id.buttonsave);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_request);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void  startPosting(){
        mProgress.setMessage("Posting to blog..");
        mProgress.show();
        final String currentdate = DateFormat.getDateTimeInstance().format(new Date());
        final String title_val=mPostTitle.getText().toString().trim();
        final  String desc_val=mPostDesc.getText().toString().trim();

        String user_id= mCurrent_user.getUid();
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri!=null){
            StorageReference filepath=mStorage.child("mediu-bd0ec").child("Health_feed").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    CharSequence sucess = "Sucess!";
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    final DatabaseReference newPost=mDatabase.push();

                    newPost.child("title").setValue(title_val);
                    newPost.child("desc").setValue(desc_val);
                    newPost.child("date").setValue(currentdate);
                    newPost.child("image").setValue(downloadUrl.toString());
                    mDoctorInfoDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String firstname = dataSnapshot.child("firstname").getValue().toString();
                            String lastname = dataSnapshot.child("lastname").getValue().toString();
                            String display_name = firstname+" "+lastname;
                            newPost.child("author").setValue(display_name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mProgress.dismiss();
                    Toast.makeText(PostBlogActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_request && resultCode==RESULT_OK){
            mImageUri=data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}


