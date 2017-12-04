package com.example.android.testlogin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Soul on 11/25/2017.
 */

public class YourHostAdapter extends RecyclerView.Adapter<YourHostAdapter.ViewHolder> {

    private ArrayList<OwnHostTripItem> ownHostTripList;
    private Context context;
    private Activity parentActivity;

    public YourHostAdapter(ArrayList<OwnHostTripItem> ownHostTripList, Context context , Activity parentActivity) {
        this.ownHostTripList = ownHostTripList;
        this.context = context;
        this.parentActivity = parentActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.host_trip_item, parent , false);

        final ViewHolder holder = new ViewHolder(v);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final OwnHostTripItem ownHostTripListy = ownHostTripList.get(position);

        holder.mTripTitle.setText(ownHostTripListy.getTitle());
        holder.mTripCountry.setText(ownHostTripListy.getCountry());
        holder.mTripPrice.setText("Price: $ " + ownHostTripListy.getPricePerGuest());
        holder.mTripSize.setText("Group Size: " + ownHostTripListy.getMaxGroupSize());

        //For Image view
        Picasso.with(context).load(ownHostTripListy.getThumbPhoto()).placeholder(R.drawable.placeholder_image)
                .into(holder.mTripImage);

        //For Delete Button to delete post
        holder.mTripDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String postKey1 = ownHostTripListy.getPostKey();

                 FirebaseUser mCurrentUser;
                 final String current_userId;

                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                current_userId = mCurrentUser.getUid();



                //Alert Dialog Popup Here...////
                AlertDialog.Builder confirmation = new AlertDialog.Builder(parentActivity);
                String txt = "Are You Sure You Want To Delete This Created Trip?\nYou Would"  +
                        "Need to Re-Create Your Post After Deleting";

                confirmation.setMessage(txt)
                        .setCancelable(false)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


            DatabaseReference meRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy")
                                        .child(postKey1);
                                        meRef.removeValue();

        Query queryRef = FirebaseDatabase.getInstance().getReference().child("users").child(current_userId).child("postKeys")
                         .orderByChild("postId").equalTo(postKey1);
                    queryRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            final String finallyGetKey = dataSnapshot.getKey();

                            DatabaseReference postRef = FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(current_userId)
                                    .child("postKeys").child(finallyGetKey);
                            postRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(parentActivity , "Deleted: " + finallyGetKey, Toast.LENGTH_SHORT).show();
                                }
                            });

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
                            })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = confirmation.create();
                alert.setTitle("Delete Current Trip");
                alert.show();




            }
        });


        //For whole view to be clicked
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String postKey = ownHostTripListy.getPostKey();
                Intent tripProfileIntent = new Intent(view.getContext(), EachTripActivity.class);
                tripProfileIntent.putExtra("pushKey", postKey);
                view.getContext().startActivity(tripProfileIntent);


            }
        });

    }

    @Override
    public int getItemCount() {

        if (ownHostTripList != null) {

            return ownHostTripList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView mTripTitle;
        public TextView mTripCountry;
        public TextView mTripPrice;
        public TextView mTripSize;
        public ImageView mTripImage;
        public Button mTripDeleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mTripTitle = (TextView) mView.findViewById(R.id.host_trip_title);
            mTripCountry = (TextView) mView.findViewById(R.id.host_trip_country);
            mTripPrice = (TextView) mView.findViewById(R.id.host_trip_price);
            mTripSize = (TextView) mView.findViewById(R.id.host_trip_size);
            mTripDeleteBtn = (Button) mView.findViewById(R.id.host_trip_delete_btn);

            mTripImage = (ImageView) mView.findViewById(R.id.host_trip_image);

        }
    }
}
