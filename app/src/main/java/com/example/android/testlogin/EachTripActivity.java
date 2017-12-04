package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Soul on 10/15/2017.
 */

public class EachTripActivity extends AppCompatActivity {


    //Here are all on screen items//.
    private TextView mTitle;
    private TextView mHostName;
    private TextView mActivity;
    private TextView mTagline;
    private TextView mPlace;
    private TextView mTimeStart;
    private TextView mTimeEnd;
    private TextView mPrice;
    private TextView mGuestMax;
    private TextView mGuestCount;
    private TextView mLocation;
    private TextView mCountry;
    private TextView mStreetAdress;
    private TextView mCity;
    private TextView mState;
    private TextView mZipCode;
    private TextView mProvide;
    private TextView mGuestKnow;
    private TextView mBasicRequirements;
    private TextView mExtraRequirements;
    private TextView mDetailUnderstanding;
    private TextView mPacking;
    private TextView mBio;
    private ImageView mTripPic;
    private CircleImageView mProfilePic;
    private Button mAddButton;
    private Button mDecreaseButton;
    private Button mEditTrip;
    private ImageView mChatIcon;
    private ImageView mBackButton;
    private int mGuestNum = 0;
    //On screen item ends here//..

    //Here is all to pass intent
    private String profile_guest_max;
    String profile_host_name;
    String profile_host_id;
    String profile_small_circle_host_pic;
    String profile_trip_title;
    String profile_trip_tagline;
    String profile_activity;
    String profile_place;
    String profile_time_start;
    String profile_time_end;
    String profile_price;
    String profile_location;
    String profile_street;
    String profile_city;
    String profile_state;
    String profile_zip;
    String profile_country;
    String profile_provide;
    String profile_guest_know;
    String profile_basic_requirements;
    String profile_extra_requirements;
    String profile_detail_context;
    String profile_packing;
    String profile_bio;
    //// Passing intent variable ends here


    //Here all for database queries.//
    private DatabaseReference mRef;
    private DatabaseReference mBasicRef;
    private DatabaseReference mHostRef;
    private DatabaseReference mUserRef;
    private FirebaseUser mCurrenUser;
    //Database queries variables ends here..//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.each_trip);

        final String random_key_pushed = getIntent().getStringExtra("pushKey");

        mCurrenUser= FirebaseAuth.getInstance().getCurrentUser();
        final String currentUid = mCurrenUser.getUid();


        mEditTrip = (Button) findViewById(R.id.trip_profile_edit_btn);
        mEditTrip.setVisibility(View.GONE);

        mBasicRef = FirebaseDatabase.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy").child(random_key_pushed);
        mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUid);

        mHostName = (TextView) findViewById(R.id.trip_by_who);

        mTitle = (TextView) findViewById(R.id.trip_profile_title);
        mTagline = (TextView)findViewById(R.id.trip_profile_tagline);
        mHostName = (TextView) findViewById(R.id.trip_by_who);
        mActivity = (TextView) findViewById(R.id.trip_profile_activity);
        mPlace = (TextView) findViewById(R.id.trip_profile_place);
        mTimeStart = (TextView) findViewById(R.id.trip_profile_timeStart);
        mTimeEnd = (TextView) findViewById(R.id.trip_profile_timeEnd);
        mPrice = (TextView) findViewById(R.id.trip_profile_price);
        mGuestMax = (TextView) findViewById(R.id.trip_profile_guest_max);
        mGuestCount = (TextView) findViewById(R.id.profile_trip_guest_count);
        mLocation = (TextView) findViewById(R.id.trip_profile_meeting_location);
        mCountry = (TextView) findViewById(R.id.trip_profile_meeting_country);
        mCity = (TextView) findViewById(R.id.trip_profile_meeting_city);
        mStreetAdress = (TextView) findViewById(R.id.trip_profile_meeting_street);
        mZipCode = (TextView) findViewById(R.id.trip_profile_meeting_zip);
        mState = (TextView) findViewById(R.id.trip_profile_meeting_state);
        mProvide = (TextView) findViewById(R.id.trip_profile_provide);
        mGuestKnow = (TextView) findViewById(R.id.trip_profile_guest_know);
        mBasicRequirements = (TextView) findViewById(R.id.trip_profile_basic_requirements);
        mExtraRequirements = (TextView) findViewById(R.id.trip_profile_extra_requirements);
        mDetailUnderstanding = (TextView) findViewById(R.id.trip_profile_detail_information);
        mPacking = (TextView) findViewById(R.id.trip_profile_packing);
        mBio = (TextView) findViewById(R.id.trip_profile_bio);
        mTripPic = (ImageView) findViewById(R.id.trip_profile_image);
        mProfilePic = (CircleImageView) findViewById(R.id.trip_profile_circle_pic);
        mAddButton = (Button) findViewById(R.id.trip_profile_add_btn);
        mDecreaseButton = (Button) findViewById(R.id.trip_profile_minus_btn);
        mChatIcon = (ImageView) findViewById(R.id.chat_icon);
        mBackButton = (ImageView) findViewById(R.id.back_inside_imageview);



        //Here is to make the buttons of add and minus work.


        //This is where the add + button works onCreate.
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGuestNum++;

                if (mGuestNum >= Integer.valueOf(profile_guest_max)) {
                    mGuestNum = Integer.valueOf(profile_guest_max);
                }
                mGuestCount.setText(mGuestNum + " Guests");


            }
        });

        //This is where the minus - button works onCreate.
        mDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGuestNum = mGuestNum - 1;
                if (mGuestNum <= 0) {
                    mGuestNum = 0;
                }
                mGuestCount.setText(mGuestNum + " Guests");


            }
        });

        mChatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent contactHostIntent = new Intent(EachTripActivity.this , ChatActivity.class);
                contactHostIntent.putExtra("hostDisplayName" , profile_host_name);
                contactHostIntent.putExtra("hostUID" , profile_host_id);
                contactHostIntent.putExtra("hostPic" , profile_small_circle_host_pic);
                startActivity(contactHostIntent);
            }
        });

        mBackButton.bringToFront();
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(EachTripActivity.this , OverviewPage.class);
                startActivity(backIntent);
                finish();

            }
        });


        mEditTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent editTripIntent = new Intent(EachTripActivity.this , EditTripActivity.class);
             /*   //Passing all the intent required to displayed on edittext so that hosts don't have
                to refill everything again if those fields were once filled.*/
                editTripIntent.putExtra( "Title", profile_trip_title);
                editTripIntent.putExtra( "Tagline", profile_trip_tagline);
                editTripIntent.putExtra( "Activity",profile_activity);
                editTripIntent.putExtra( "Place", profile_place );
                editTripIntent.putExtra( "TimeStart", profile_time_start);
                editTripIntent.putExtra( "TimeEnd", profile_time_end);
                editTripIntent.putExtra( "Price", profile_price);
                editTripIntent.putExtra( "Location", profile_location);
                editTripIntent.putExtra( "Street", profile_street);
                editTripIntent.putExtra( "City",profile_city);
                editTripIntent.putExtra( "State", profile_state);
                editTripIntent.putExtra( "Zip", profile_zip);
                editTripIntent.putExtra( "Country", profile_country);
                editTripIntent.putExtra( "Provide", profile_provide);
                editTripIntent.putExtra( "ExtraInfo", profile_guest_know);
                editTripIntent.putExtra( "BasicReq", profile_basic_requirements);
                editTripIntent.putExtra( "AddiReq", profile_extra_requirements);
                editTripIntent.putExtra( "Context", profile_detail_context);
                editTripIntent.putExtra( "GroupSize" , profile_guest_max);
                editTripIntent.putExtra( "Bio", profile_bio);
                editTripIntent.putExtra( "Packing", profile_packing);
                editTripIntent.putExtra( "RandomPushKey" , random_key_pushed);
                startActivity(editTripIntent);


            }
        });         // Intent to edit trip page ends here..//




        //Here is to read the database stored files to bring out to the layout.
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 profile_trip_title = dataSnapshot.child("title").getValue().toString();
                 profile_trip_tagline = dataSnapshot.child("tagline").getValue().toString();
                 profile_activity = dataSnapshot.child("activity").getValue().toString();
                 profile_place = dataSnapshot.child("place").getValue().toString();
                 profile_time_start = dataSnapshot.child("startTime").getValue().toString();
                 profile_time_end = dataSnapshot.child("endTime").getValue().toString();
                 profile_price = dataSnapshot.child("pricePerGuest").getValue().toString();
                 profile_guest_max = dataSnapshot.child("maxGroupSize").getValue().toString();
                 profile_location = dataSnapshot.child("locationName").getValue().toString();
                 profile_street = dataSnapshot.child("streetAddress").getValue().toString();
                 profile_city = dataSnapshot.child("city").getValue().toString();
                 profile_state = dataSnapshot.child("state").getValue().toString();
                 profile_zip = dataSnapshot.child("zipCode").getValue().toString();
                 profile_country = dataSnapshot.child("country").getValue().toString();
                 profile_provide = dataSnapshot.child("itemProvide").getValue().toString();
                 profile_guest_know = dataSnapshot.child("extraInfo").getValue().toString();
                 profile_basic_requirements = dataSnapshot.child("basicReq").getValue().toString();
                 profile_extra_requirements = dataSnapshot.child("addiReq").getValue().toString();
                 profile_detail_context = dataSnapshot.child("activityContext").getValue().toString();
                 profile_packing = dataSnapshot.child("packingList").getValue().toString();
                 profile_bio = dataSnapshot.child("hostBio").getValue().toString();





                 profile_host_id = dataSnapshot.child("hostUID").getValue().toString();

                if (profile_host_id.equals(currentUid)) {

                    mEditTrip.setVisibility(View.VISIBLE);
                }



                mHostRef = FirebaseDatabase.getInstance().getReference().child("users").child(profile_host_id);

                mHostRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            profile_host_name = dataSnapshot.child("displayName").getValue().toString();
                            mHostName.setText(profile_host_name);
                        }
                        catch (Exception e) {

                        }

                        try {
                            profile_small_circle_host_pic = dataSnapshot.child("userThumbPic").getValue().toString();
                            Picasso.with(getApplicationContext()).load(profile_small_circle_host_pic)
                                    .placeholder(R.drawable.defaultavatar).into(mProfilePic);

                        }
                        catch (Exception e) {
                            Toast.makeText(EachTripActivity.this, "Host Did Not Set A Profile Pic",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mTitle.setText(profile_trip_title);
                mTagline.setText(profile_trip_tagline);
                mActivity.setText(profile_activity);
                mPlace.setText(profile_place);
                mTimeStart.setText(profile_time_start);
                mTimeEnd.setText(profile_time_end);
                mPrice.setText("Price per Guest: $"+profile_price);
                mGuestMax.setText("* " +profile_guest_max + " Maximum guests allowed *");
                mLocation.setText(profile_location);
                mStreetAdress.setText(profile_street);
                mCity.setText(profile_city);
                mState.setText(profile_state);
                mZipCode.setText(profile_zip);
                mCountry.setText(profile_country);
                mProvide.setText(profile_provide);
                mGuestKnow.setText(profile_guest_know);
                mBasicRequirements.setText(profile_basic_requirements);
                mExtraRequirements.setText(profile_extra_requirements);
                mDetailUnderstanding.setText(profile_detail_context);
                mPacking.setText(profile_packing);
                mBio.setText(profile_bio);



                //Set for    =  Image and put image online if available

                try {
                    final String profile_big_pic = dataSnapshot.child("imgUrls").getValue().toString();

                    Picasso.with(EachTripActivity.this).load(profile_big_pic).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.placeholder_image).resize(400 , 400).into(mTripPic, new Callback() {
                        @Override
                        public void onSuccess() {



                            //Do nothing if successfully load image offline. Just let it load offline for best.

                        }

                        @Override
                        public void onError() {

                            //But if image is not successfully loaded offline, then load it online.
                            Picasso.with(EachTripActivity.this).load(profile_big_pic).placeholder(R.drawable.placeholder_image)
                                    .resize(400 , 400).into(mTripPic);

                        }
                    });
                }
                catch (Exception e) {

                }
                mTripPic.setAdjustViewBounds(true);
                mTripPic.setScaleType(ImageView.ScaleType.CENTER_CROP);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // This is where firebase reading codes to layout ends

        //This is to set where profile pic is clickable for users to view host or others profile page.

        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent hostProfileIntent = new Intent(EachTripActivity.this , HostProfilePage.class);
                hostProfileIntent.putExtra("pushKey" , random_key_pushed);
                startActivity(hostProfileIntent);

            }
        });

        //This is to set where host name is clickable for users to view host or others profile page.

        mHostName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent hostProfileIntent = new Intent(EachTripActivity.this , HostProfilePage.class);
                hostProfileIntent.putExtra("pushKey" , random_key_pushed);
                startActivity(hostProfileIntent);

            }
        });

        //This is where clickable host profile pic and host name ends.


    }// This is where onCreate Ends.




}
