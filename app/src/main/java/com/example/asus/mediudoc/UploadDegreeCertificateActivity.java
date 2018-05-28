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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.util.Date;

public class UploadDegreeCertificateActivity extends AppCompatActivity {
    private ImageButton mSelectImage;
    private Button mSubmitBtn;
    private Uri mImageUri=null;
    private static final int Gallery_request=1;
    private StorageReference mStorage;
    private FirebaseUser mCurrent_user;
    private ProgressDialog mProgress;
    private DatabaseReference mDoctorInfoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_degree_certificate);

        mProgress=new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
        mDoctorInfoDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(mCurrent_user.getUid());
        mSelectImage = (ImageButton) findViewById(R.id.btAddCertificateImage);

        mSubmitBtn = (Button) findViewById(R.id.btSaveCertificate);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(4, 3)
                        .start(UploadDegreeCertificateActivity.this);
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
        mProgress.setMessage("Uploading..");
        mProgress.show();

        String user_id= mCurrent_user.getUid();
        if (mImageUri!=null){
            StorageReference filepath=mStorage.child("DoctorCertificates").child(mCurrent_user.getUid()).child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    CharSequence sucess = "Sucess!";
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();

                    mDoctorInfoDatabase.child("degree_certificate").setValue(downloadUrl.toString());
                    mProgress.dismiss();

                    Toast.makeText(UploadDegreeCertificateActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                }
            });
        }
        else{
            mProgress.dismiss();
            Toast.makeText(UploadDegreeCertificateActivity.this, "Please full up all the details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri=result.getUri();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}
