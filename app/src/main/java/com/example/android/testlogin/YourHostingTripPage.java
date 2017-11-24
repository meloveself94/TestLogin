package com.example.android.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Soul on 11/21/2017.
 */

public class YourHostingTripPage extends AppCompatActivity {


    private RecyclerView mOwnTripList;

    private DatabaseReference mTripRef;

    private DatabaseReference mPostIdRef;

    private DatabaseReference mLastRef;

    private TextView mText;

    private String hello;

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

        mTripRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy");

        mPostIdRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUid).child("fuckId");



        mText = (TextView) findViewById(R.id.hosting_trip_text);

        mPostIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    PostId postId = snapshot.getValue(PostId.class);
                     hello = postId.getPostId();
                    Toast.makeText(YourHostingTripPage.this, hello , Toast.LENGTH_LONG).show();
                    Log.d("Hello soidjosajdo aisos" , hello);



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    /*@Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<OwnHostTripItem , OwnTripViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<OwnHostTripItem, OwnTripViewHolder>(
                    OwnHostTripItem.class,
                    R.layout.host_trip_item,
                    OwnTripViewHolder.class,
                    mTripRef
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
