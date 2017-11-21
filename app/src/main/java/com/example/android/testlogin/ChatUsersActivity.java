package com.example.android.testlogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soul on 10/30/2017.
 */

public class ChatUsersActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mRef;
    private DatabaseReference mUserChatRef;
    private DatabaseReference mHostRef;

    String host_target_name;
    String host_target_message;
    String host_target_pic;

    private String current_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_users_activity);

        mToolbar = (Toolbar) findViewById(R.id.all_users_bar);

        //Firstly, set action bar first before setting title and other things.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        current_uid = mAuth.getCurrentUser().getUid();

        mRef = FirebaseDatabase.getInstance().getReference();
        mUserChatRef = mRef.child("userTargetMessage").child(current_uid);



        mUsersList = (RecyclerView) findViewById(R.id.all_users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<SingleChatUser , UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<SingleChatUser, UsersViewHolder>(
                    //For parameters to put in//
                        SingleChatUser.class ,
                        R.layout.single_chat_user ,
                        UsersViewHolder.class ,
                        mUserChatRef
                ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, SingleChatUser users, int position) {

            viewHolder.setName(users.getTheName());
            viewHolder.setLastMessage(users.getLastMessage());
            viewHolder.setTargetsPic(users.getTargetPic() , getApplicationContext());

            final String key = getRef(position).getKey();





           //Set the whole view to be clickable to the trip profile page.
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHostRef = mRef.child("userTargetMessage").child(current_uid).child(key);
                    mHostRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            host_target_name = dataSnapshot.child("name").getValue().toString();
                            host_target_pic = dataSnapshot.child("targetPic").getValue().toString();



                            Intent targetHostIntent = new Intent(ChatUsersActivity.this , ChatActivity.class);
                            targetHostIntent.putExtra("hostDisplayName" , host_target_name);
                            targetHostIntent.putExtra("hostPic" , host_target_pic);
                            targetHostIntent.putExtra("hostUID", key);
                            startActivity(targetHostIntent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });


                    }
                };


            mUsersList.setAdapter(firebaseRecyclerAdapter);






    }


    //Must put static when defining another class in a class.
    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        //View to be used by the Firebase Adapter
        //Be used for onClickListener for the recycling items.
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        //Create a method to populate the recycler view.//
        public void setName(String name) {

            //Set the value to the textview in the xml layout file
            TextView userNameView = (TextView) mView.findViewById(R.id.single_user_name);
             userNameView.setText(name);

        }

        //Create a method to populate the recycler view.//
        public void setLastMessage(String lastMessage) {

            //Set the value to the textview in the xml layout file
            TextView lastMessageView = (TextView) mView.findViewById(R.id.single_user_message);
             lastMessageView.setText(lastMessage);

        }

        public void setTargetsPic(String targetsPic, Context ctx) {

            CircleImageView targetPicture = (CircleImageView) mView.findViewById(R.id.single_user_image);

            Picasso.with(ctx).load(targetsPic).placeholder(R.drawable.defaultavatar).into(targetPicture);
        }




    }
}
