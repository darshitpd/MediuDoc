package com.example.asus.mediudoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayBlogsActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mBlogDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_blogs);

        mBlogDatabase = FirebaseDatabase.getInstance().getReference().child("Feed");
//
//        mBlogList = (RecyclerView)findViewById(R.id.blog_list);
//        mBlogList.setHasFixedSize(true);
//        mBlogList.setLayoutManager(new LinearLayoutManager(this));
    }
}
