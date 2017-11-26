package com.example.android.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Soul on 11/21/2017.
 */

public class YourHostingTripPage extends AppCompatActivity {


    private RecyclerView mOwnTripList;

    private DatabaseReference mTripRef;

    private DatabaseReference mPostIdRef;

    private DatabaseReference mLastRef;

    private YourHostAdapter myAdapter;


    //private TextView mText;

    private String tripUniqueId;
    private ArrayList<OwnHostTripItem> comPostArrayList;
    private String currentUid;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosting_trip);

        mOwnTripList = (RecyclerView) findViewById(R.id.host_trip_list);
        mOwnTripList.setHasFixedSize(true);
        mOwnTripList.setLayoutManager(new LinearLayoutManager(this));



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = mCurrentUser.getUid();

        comPostArrayList = new ArrayList<>();

        myAdapter = new YourHostAdapter(comPostArrayList,getApplicationContext() );
        mOwnTripList.setAdapter(myAdapter);


        mTripRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy");
        //mText = (TextView) findViewById(R.id.hosting_trip_text);

        mPostIdRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUid).child("fuckId");

        mPostIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                comPostArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    PostId postId = snapshot.getValue(PostId.class);
                        tripUniqueId = postId.getPostId();
                    Log.d("$$$$$$$$$$$$$" , tripUniqueId);



            mLastRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy").child(tripUniqueId);

            mLastRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {


                OwnHostTripItem ownHostTripPost = dataSnapshot.getValue(OwnHostTripItem.class);
                comPostArrayList.add(ownHostTripPost);
                Log.d("%%%%%",comPostArrayList.get(0).getTitle());

                 myAdapter.notifyDataSetChanged();


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




    } // ** OnCreate Ends here**///


}
