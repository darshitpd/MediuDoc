package com.example.asus.mediudoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DisplayBlogsActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mBlogDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_blogs);

        mBlogDatabase = FirebaseDatabase.getInstance().getReference().child("Feed");

        mBlogList = (RecyclerView)findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BlogPostSingle, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogPostSingle, UsersViewHolder>(
                BlogPostSingle.class, R.layout.single_blog_post, UsersViewHolder.class, mBlogDatabase
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, BlogPostSingle users, int i) {

                usersViewHolder.setTitle(users.getTitle());
                usersViewHolder.setAuthor(users.getAuthor());
                usersViewHolder.setDate(users.getDate());

                usersViewHolder.setUserImage(users.getImage(), getApplicationContext());

                final String blog_id = getRef(i).getKey();

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(DisplayBlogsActivity.this, SingleBlogPostFull.class);
                        profileIntent.putExtra("blog_id", blog_id);
                        startActivity(profileIntent);
                    }
                });

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title) {

            TextView userNameView = (TextView) mView.findViewById(R.id.post_title);
            userNameView.setText(title);

        }

        public void setDate(String date) {

            TextView userDateView = (TextView) mView.findViewById(R.id.post_date);
            userDateView.setText(date);

        }

        public void setAuthor(String author){

            TextView userAuthorNameView = (TextView) mView.findViewById(R.id.post_author);
            userAuthorNameView.setText(author);

        }

        public void setUserImage(String image, Context ctx){

            ImageView userImageView = (ImageView) mView.findViewById(R.id.post_image);

            Picasso.with(ctx).load(image).placeholder(R.drawable.loading_text).into(userImageView);

        }

    }
}
