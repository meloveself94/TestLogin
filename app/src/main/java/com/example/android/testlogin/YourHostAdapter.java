package com.example.android.testlogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Soul on 11/25/2017.
 */

public class YourHostAdapter extends RecyclerView.Adapter<YourHostAdapter.ViewHolder> {

    private ArrayList<OwnHostTripItem> ownHostTripList;
    private Context context;

    public YourHostAdapter(ArrayList<OwnHostTripItem> ownHostTripList, Context context) {
        this.ownHostTripList = ownHostTripList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.host_trip_item, parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OwnHostTripItem ownHostTripListy = ownHostTripList.get(position);

        holder.mTripTitle.setText(ownHostTripListy.getTitle());
        holder.mTripCountry.setText(ownHostTripListy.getCountry());
        holder.mTripPrice.setText("Price: $ " + ownHostTripListy.getPricePerGuest());
        holder.mTripSize.setText("Group Size: " + ownHostTripListy.getMaxGroupSize());

        //For Image view
        Picasso.with(context).load(ownHostTripListy.getThumbPhoto()).placeholder(R.drawable.placeholder_image)
                .into(holder.mTripImage);

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

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mTripTitle = (TextView) mView.findViewById(R.id.host_trip_title);
            mTripCountry = (TextView) mView.findViewById(R.id.host_trip_country);
            mTripPrice = (TextView) mView.findViewById(R.id.host_trip_price);
            mTripSize = (TextView) mView.findViewById(R.id.host_trip_size);

            mTripImage = (ImageView) mView.findViewById(R.id.host_trip_image);

        }
    }
}
