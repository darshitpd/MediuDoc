package com.example.asus.mediudoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HealthFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_feed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void postblog(View view){
        Intent intent = new Intent(this, PostBlogActivity.class);
        startActivity(intent);    }

    public void showblog(View view){
        Intent intent = new Intent(this, DisplayBlogsActivity.class);
        startActivity(intent);    }
}
