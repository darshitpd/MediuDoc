package com.example.asus.mediudoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleBlogPostFull extends AppCompatActivity {

    private TextView mpost_title, mpost_date, mpost_author, mpost_description;
    private ImageView mpost_image;

    private DatabaseReference mBlogDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_blog_post_full);

        mpost_author= (TextView)findViewById(R.id.post_author);
        mpost_date=(TextView)findViewById(R.id.post_date);
        mpost_title=(TextView)findViewById(R.id.post_title);
        mpost_description= (TextView)findViewById(R.id.post_desc);
        mpost_image =(ImageView)findViewById(R.id.post_image);

        final String blog_id = getIntent().getStringExtra("blog_id");

        mBlogDatabase= FirebaseDatabase.getInstance().getReference().child("Feed").child(blog_id);

        mBlogDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String author = dataSnapshot.child("author").getValue().toString();
                String title = dataSnapshot.child("title").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String description = dataSnapshot.child("desc").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mpost_author.setText(author);
                mpost_date.setText(date);
                mpost_title.setText(title);
                mpost_description.setText(description);
                Picasso.with(SingleBlogPostFull.this).load(image).placeholder(R.drawable.progress_animation).into(mpost_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
