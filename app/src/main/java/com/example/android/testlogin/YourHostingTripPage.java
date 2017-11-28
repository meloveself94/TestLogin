package com.example.android.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private Toolbar mHostOwnTripToolbar;


    //private TextView mText;

    private String tripUniqueId;
    private ArrayList<OwnHostTripItem> comPostArrayList;
    private String currentUid;
    private FirebaseUser mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosting_trip);

  /*     mHostOwnTripToolbar = (Toolbar) findViewById(R.id.own_host_trip_bar);;
     setSupportActionBar(mHostOwnTripToolbar);
     getSupportActionBar().setElevation(3);
     getSupportActionBar().setTitle("Your Hosting Trips");
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/



        mOwnTripList = (RecyclerView) findViewById(R.id.host_trip_list);
        mOwnTripList.setHasFixedSize(true);
        mOwnTripList.setLayoutManager(new LinearLayoutManager(this));



        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = mCurrentUser.getUid();

        comPostArrayList = new ArrayList<>();

        myAdapter = new YourHostAdapter(comPostArrayList,getApplicationContext() , this );
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
                ownHostTripPost.setPostKey(dataSnapshot.getKey());
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

   /* @Override
protected void onStart() {
    super.onStart();

    FirebaseRecyclerAdapter<OwnHostTripItem , OwnTripViewHolder> firebaseRecyclerAdapter
            = new FirebaseRecyclerAdapter<OwnHostTripItem, OwnTripViewHolder>(
                OwnHostTripItem.class,
                R.layout.host_trip_item,
                OwnTripViewHolder.class,
                mTripRef.orderByValue().equalTo(String.valueOf(comPostArrayList))
    ) {
        @Override
        protected void populateViewHolder(OwnTripViewHolder viewHolder, OwnHostTripItem model, int position) {
            viewHolder.setTitle(model.getTitle());
            viewHolder.setCountry(model.getCountry());
            viewHolder.setPrice(model.getPricePerGuest());
            viewHolder.setMaxGroupSize(model.getMaxGroupSize());
            viewHolder.setTripImage(model.getThumbPhoto() , getApplicationContext());


        }
    };

    mOwnTripList.setAdapter(firebaseRecyclerAdapter);
}

public static class OwnTripViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public OwnTripViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setTitle(String title) {

        TextView mTripTitle = mView.findViewById(R.id.host_trip_title);
        mTripTitle.setText(title);
    }

    public void setCountry(String country) {

        TextView mTripCountry = mView.findViewById(R.id.host_trip_country);
        mTripCountry.setText(country);
    }

    public void setPrice (String pricePerGuest) {

        TextView mTripPrice = mView.findViewById(R.id.host_trip_price);
        mTripPrice.setText(pricePerGuest);
    }

    public void setMaxGroupSize(String maxGroupSize) {

      TextView mTripSize  = mView.findViewById(R.id.host_trip_size);
        mTripSize.setText(maxGroupSize);

    }

    public void setTripImage (String thumbPhoto , Context ctx) {
        ImageView mTripImage = mView.findViewById(R.id.host_trip_image);

        Picasso.with(ctx).load(thumbPhoto).placeholder(R.drawable.placeholder_image).into(mTripImage);

    }


}*/
}

