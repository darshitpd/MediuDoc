package com.example.asus.mediudoc;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by devanshgandhi on 13/03/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    private List<Messages> mMessageList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase;





    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;
    }


    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)

    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{


        public TextView messaageText;
//        public CircleImageView profileImage;
        //        public TextView timeText;
        public ImageView messageImage;

        public MessageViewHolder(View itemView) {
            super(itemView);


            messaageText = (TextView) itemView.findViewById(R.id.message_text_layout);
//            profileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_layout);
//            timeText = (TextView) itemView.findViewById(R.id.message_item_time);
            messageImage = (ImageView) itemView.findViewById(R.id.message_image_layout);

        }
    }


    public void  onBindViewHolder(MessageViewHolder viewHolder , int i)
    {
        String current_user_id = mAuth.getCurrentUser().getUid();


        Messages c = mMessageList.get(i);
        String from_user = c.getFrom();
        String message_type = c.getType();

        //change child to Doctor_Users
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(from_user);

        if (from_user.equals(current_user_id)){


            viewHolder.messaageText.setBackgroundColor(Color.WHITE);
            viewHolder.messaageText.setTextColor(Color.BLACK);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewHolder.messaageText.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.messaageText.setLayoutParams(lp);

            viewHolder.messaageText.setBackgroundResource(R.drawable.message_user_background);


        } else
        {

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewHolder.messaageText.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            viewHolder.messaageText.setLayoutParams(lp);
            viewHolder.messaageText.setBackgroundResource(R.drawable.message_text_background);
            viewHolder.messaageText.setTextColor(Color.WHITE);

        }


        if(message_type.equals("text")){

            viewHolder.messaageText.setText(c.getMessage());
            viewHolder.messageImage.setVisibility(View.INVISIBLE);

        }
        else {

            viewHolder.messaageText.setVisibility(View.INVISIBLE);

            Picasso.with(viewHolder.messageImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);

        }


//        viewHolder.timeText.setText(c.getTime());
    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
