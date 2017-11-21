package com.example.android.testlogin;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soul on 10/26/2017.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder> {

    private List<ChatMessage> mChatMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private String child_name;
    private String child_photo;
    private DatabaseReference mPicUserRef;

    public ChatMessageAdapter(List<ChatMessage> mChatMessageList) {

        this.mChatMessageList = mChatMessageList;
    }

    @Override
    public ChatMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item , parent , false);
        return new ChatMessageViewHolder(v);
    }

    public class ChatMessageViewHolder extends RecyclerView.ViewHolder {


        public TextView chatMessage;
        public TextView chatName;
        public CircleImageView chatPic;

        public ChatMessageViewHolder(View view) {
            super(view);


            chatMessage = (TextView) view.findViewById(R.id.messageTextView);
            chatName = (TextView) view.findViewById(R.id.nameTextView);
            chatPic = (CircleImageView) view.findViewById(R.id.photoImageView);
        }
    }

    @Override
    public void onBindViewHolder(final ChatMessageViewHolder holder, final int position) {

        mAuth = FirebaseAuth.getInstance();

        String current_user_id = mAuth.getCurrentUser().getUid();

        ChatMessage m = mChatMessageList.get(position);

        final String from_user = m.getFrom();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               child_name = dataSnapshot.child("displayName").getValue().toString();

                mPicUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);
                mPicUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        child_photo = dataSnapshot.child("userThumbPic").getValue().toString();


                        ChatMessage c = mChatMessageList.get(position);

                        if (child_name.equals(c.getName())) {

                            holder.chatMessage.setBackgroundResource(R.drawable.message_text_background2);
                            holder.chatMessage.setTextColor(Color.BLACK);

                        }
                        else {
                            holder.chatMessage.setBackgroundResource(R.drawable.message_text_background);
                            holder.chatMessage.setTextColor(Color.WHITE);

                        }


                        Picasso.with(holder.chatPic.getContext()).load(child_photo).placeholder(R.drawable.defaultavatar)
                                .into(holder.chatPic);
                        holder.chatMessage.setText(c.getText());
                        holder.chatName.setText(c.getName());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

    @Override
    public int getItemCount() {

        return mChatMessageList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
