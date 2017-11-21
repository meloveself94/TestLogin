package com.example.android.testlogin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soul on 10/23/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private String mChatTarget;
    private String mChatTargetId;
    private String mChatPic;
    private String mOwnUserName;
    private Toolbar mChatToolbar;

    private RecyclerView mMessageList;
    //private SwipeRefreshLayout mRefreshLayout;

    private TextView mTitleView;
    private CircleImageView mProfilePic;

    private ImageView mChatAddBtn;
    private ImageView mChatSendBtn;
    private EditText mChatType;

    private DatabaseReference mRef;
    private DatabaseReference mUserNameRef;
    private DatabaseReference messageRef;
    private DatabaseReference messageRevRef;
    private DatabaseReference mChattyRef;

    private FirebaseAuth mAuth;
    private String mCurrent_userId;

    private final List<ChatMessage> messageList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private ChatMessageAdapter mChatMessageAdapter;

    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;


    //New Solution for message to not load in a descending order.
    private int itemPosition = 0;

    private String mLastKey = "";
    private String mPrevKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        mChatToolbar = (Toolbar) findViewById(R.id.chat_app_bar);
        setSupportActionBar(mChatToolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        //This line below is to add the custom view to the action bar.
        actionBar.setDisplayShowCustomEnabled(true);

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrent_userId = mAuth.getCurrentUser().getUid();
        mUserNameRef = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrent_userId);


        mChatTarget = getIntent().getStringExtra("hostDisplayName");
        mChatTargetId = getIntent().getStringExtra("hostUID");
        mChatPic = getIntent().getStringExtra("hostPic");


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(actionBarView);

        //--Custom Action Bar Items starts here // --

        mTitleView = (TextView) findViewById(R.id.display_name_bar);
        mProfilePic = (CircleImageView) findViewById(R.id.custom_bar_image);

        mChatAddBtn = (ImageView) findViewById(R.id.photoPickerButton);
        mChatSendBtn = (ImageView) findViewById(R.id.sendButton);
        mChatType = (EditText) findViewById(R.id.messageEditText);

        mChatMessageAdapter = new ChatMessageAdapter(messageList);

        mMessageList = (RecyclerView) findViewById(R.id.messageListView);
        //mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_layout);
        mLinearLayout = new LinearLayoutManager(this);

        mMessageList.setHasFixedSize(true);
        mMessageList.setLayoutManager(mLinearLayout);

        mMessageList.setAdapter(mChatMessageAdapter);

        loadMessages();


        mTitleView.setText(mChatTarget);

        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();
            }
        });

        mUserNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mOwnUserName = dataSnapshot.child("displayName").getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //What is gonna happen when we pull the page to refresh to show more messages
 /*       mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //After pull to show more messages, mCurrentPage will increase to 2 * 10
                // and 3 * 10 and 4 * 10 and so on.
                // mCurrentPage++;

                //Item position go back to zero each time it loaded.
                // itemPosition = 0;

                //After refreshing page, load messages again.
                //Then Item position goes to position 1 (at top of screen) each time it is loaded.
                //And refresh again then go back zero and to 1.
                // loadMoreMessages();


            }
        });*/


    }

/*    private void loadMoreMessages() {
        DatabaseReference messageRef = mRef.child("messages").child(mCurrent_userId + "_" + mChatTargetId);

        //This is set up like this because when we pull refresh, initially 1*10 = 10 messages will
        // show up and when you pull it more times, mCurrentPage++ at mRefreshLayout will make mCurrentPage
        // to + 1 so that mCurrentPage become 2 * 20 and will load 20 item and pull and load 30 and so on.
        /*//* OrderByKey is used to prevent same messages from duplicating several time depending on how many time we refresh the page.
        /*//*Therefore, we need to then pass in the key of the last message of the chat to the endAt() method.
        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                String messageKey = dataSnapshot.getKey();


                if (!mPrevKey.equals(messageKey)) {
                    messageList.add(itemPosition++, chatMessage);
                } else {
                    mPrevKey = mLastKey;
                }

                //whenever we open up chat page, items are added to the recycler view
                //itemPosition++ is to put new item at downwards ascending from top whenever we load the page.

                if (itemPosition == 1) {
                    mLastKey = messageKey;
                }


                Log.d("TOTALKEYS", "Last Key " + mLastKey + "| Prev Key :" + mPrevKey + " | Message Key: " + messageKey);

                mChatMessageAdapter.notifyDataSetChanged();

                //sends us to the bottom of the page whenever the page is loaded.
                mMessageList.scrollToPosition(messageList.size() - 1);

                mRefreshLayout.setRefreshing(false);

                mLinearLayout.scrollToPositionWithOffset(10, 0);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }*/


    private void loadMessages() {

        mChattyRef = mRef.child("messages");

        mChattyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(mChatTargetId + "_" + mCurrent_userId)) {

                    mRef.child("messages").child(mChatTargetId + "_" + mCurrent_userId)
                            .addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                                ChatMessage chatterMessage = dataSnapshot.getValue(ChatMessage.class);

                                messageList.add(chatterMessage);
                                mChatMessageAdapter.notifyDataSetChanged();

                                mMessageList.scrollToPosition(messageList.size() - 1);


                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }

                else
                {

                  mRef.child("messages").child(mCurrent_userId + "_" + mChatTargetId)
                          .addChildEventListener(new ChildEventListener() {
                              @Override
                              public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                              ChatMessage chatterMessage = dataSnapshot.getValue(ChatMessage.class);

                              messageList.add(chatterMessage);
                              mChatMessageAdapter.notifyDataSetChanged();

                              mMessageList.scrollToPosition(messageList.size() - 1);



                              }

                              @Override
                              public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                              }

                              @Override
                              public void onChildRemoved(DataSnapshot dataSnapshot) {

                              }

                              @Override
                              public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError) {

                              }
                          });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage() {

        final String messageLai = mChatType.getText().toString();

        if (!TextUtils.isEmpty(messageLai)) {

            final DatabaseReference okayMesRef = mRef.child("messages");

            okayMesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(mChatTargetId + "_" + mCurrent_userId).exists()) {

                        DatabaseReference mChatGotTalkBefore = mRef.child("messages")
                                .child(mChatTargetId + "_" + mCurrent_userId).push();

                        String push_id = mChatGotTalkBefore.getKey();


                        Map<String , String> chatMap = new HashMap<String, String>();
                        chatMap.put("isUnread" , String.valueOf(false));
                        chatMap.put("name" , mOwnUserName );
                        chatMap.put("text" , messageLai);
                        chatMap.put("from" , mCurrent_userId);


                        mChatGotTalkBefore.setValue(chatMap);

                        okayMesRef.removeEventListener(this);

                        mChatType.setText("");

                        final DatabaseReference mConversationUserTargetMessage = mRef.child("userTargetMessage")
                                .child(mCurrent_userId).child(mChatTargetId);
                        final DatabaseReference mConversationHostTargetMessage = mRef.child("userTargetMessage")
                                .child(mChatTargetId).child(mCurrent_userId);

                        DatabaseReference mConversationOwnPic = mRef.child("users").child(mCurrent_userId);

                        mConversationOwnPic.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                final String userOwnPic = dataSnapshot.child("userThumbPic").getValue().toString();


                                HashMap<String , String > userChatMap = new HashMap<>();
                                userChatMap.put("name" , mChatTarget);
                                userChatMap.put("targetPic" , mChatPic);
                                userChatMap.put("lastMessage" , messageLai);


                                HashMap<String , String> targetChatMap = new HashMap<>();
                                targetChatMap.put("name" , mOwnUserName);
                                targetChatMap.put("targetPic" , userOwnPic );
                                targetChatMap.put("lastMessage" , messageLai);




                                mConversationUserTargetMessage.setValue(userChatMap);
                                mConversationHostTargetMessage.setValue(targetChatMap);






                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });







                    }

                    else {

                        DatabaseReference mChatNoTalkBeforeRef = mRef.child("messages")
                                .child(mCurrent_userId + "_" + mChatTargetId).push();
                        String push_id = mChatNoTalkBeforeRef.getKey();


                        Map<String , String> chatMap = new HashMap<String, String>();
                        chatMap.put("isUnread" , String.valueOf(false));
                        chatMap.put("name" , mOwnUserName );
                        chatMap.put("text" , messageLai);
                        chatMap.put("from" , mCurrent_userId);


                        mChatNoTalkBeforeRef.setValue(chatMap);

                        okayMesRef.removeEventListener(this);


                        mChatType.setText("");

                        final DatabaseReference mConversationUserTargetMessage = mRef.child("userTargetMessage")
                                .child(mCurrent_userId).child(mChatTargetId);
                        final DatabaseReference mConversationHostTargetMessage = mRef.child("userTargetMessage")
                                .child(mChatTargetId).child(mCurrent_userId);

                        DatabaseReference mConversationOwnPic = mRef.child("users").child(mCurrent_userId);

                        mConversationOwnPic.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                final String userOwnPic = dataSnapshot.child("userThumbPic").getValue().toString();


                                HashMap<String , String > userChatMap = new HashMap<>();
                                userChatMap.put("name" , mChatTarget);
                                userChatMap.put("targetPic" , mChatPic);
                                userChatMap.put("lastMessage" , messageLai);


                                HashMap<String , String> targetChatMap = new HashMap<>();
                                targetChatMap.put("name" , mOwnUserName);
                                targetChatMap.put("targetPic" , userOwnPic );
                                targetChatMap.put("lastMessage" , messageLai);




                                mConversationUserTargetMessage.setValue(userChatMap);
                                mConversationHostTargetMessage.setValue(targetChatMap);






                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });





                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });









        }
    }
}


