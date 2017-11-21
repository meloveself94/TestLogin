package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soul on 11/17/2017.
 */

public class EditTripActivity extends AppCompatActivity {

    private Toolbar mEditTripToolbar;

    private Spinner mLanguageSpinner , mTypeSpinner , mStartTimeSpinner , mEndTimeSpinner
                    , mCountrySpinner , mGroupSizeSpinner  , mTimePrepareSpinner;

    private EditText mTitleBox , mTaglineBox , mActivityDoBox , mVisitPlaceBox , mLocationNameBox
                     , mStreetAddBox , mUnitNoBox , mCityBox, mStateBox, mZipBox , mProvideBox
                      , mExtraInfoBox , mBioBox , mHostReqBox, mHostAddiReqBox , mContextBox , mPackingListBox
                        , mPriceBox;

    private Button mSaveButton;

    //String for spinner to save new selection selected back to database
    String item_language;
    String item_type;
    String item_start_time;
    String item_end_time;
    String item_country;
    String item_size;
    String item_prepare;

    //Three strings that need to get from firebase database
    String mItemLanguage;
    String mItemType;
    String mItemPrepare;

    //The texts gotten from EachTripActivity intent to fill in fields that were once filled by hosts.
    String mTimeStartText;
    String mTimeEndText;
    String mTitleText;
    String mTaglineText;
    String mActivityText;
    String mVisitPlaceText;
    String mPriceText;
    String mLocationText;
    String mCountryText;
    String mStreetAddText;
    String mCityText;
    String mStateText;
    String mZipText;
    String mProvideText;
    String mExtraInfoText;
    String mBasicReqText;
    String mAddiReqText;
    String mContextText;
    String mBioText;
    String mGroupSize;
    String mPackingText;

    //The pushed key for comPostsCopy passed from EachTripActivity intent
    String random_key_pushed;

    //Database Reference
    DatabaseReference spinnerRef;
    DatabaseReference saveRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_trip_activity);

        mEditTripToolbar = (Toolbar) findViewById(R.id.edit_trip_bar);
        setSupportActionBar(mEditTripToolbar);
        getSupportActionBar().setTitle("Edit Your Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //All on screen spinner variables declared here.//
        mLanguageSpinner = (Spinner) findViewById(R.id.edit_language_spinner);
        mTypeSpinner = (Spinner) findViewById(R.id.edit_trip_type_spinner);
        mStartTimeSpinner = (Spinner) findViewById(R.id.edit_trip_start_spinner);
        mEndTimeSpinner = (Spinner) findViewById(R.id.edit_trip_end_spinner);
        mCountrySpinner = (Spinner) findViewById(R.id.edit_trip_country_spinner);
        mGroupSizeSpinner = (Spinner) findViewById(R.id.edit_trip_size_spinner);
        mTimePrepareSpinner = (Spinner) findViewById(R.id.edit_trip_prepare_spinner);

        mSaveButton = (Button) findViewById(R.id.edit_trip_save_btn);

        //All on screen editText variables declared here.//
        mTitleBox = (EditText) findViewById(R.id.edit_title_box);
        mTaglineBox = (EditText) findViewById(R.id.edit_tagline_box);
        mActivityDoBox = (EditText) findViewById(R.id.edit_activity_box);
        mVisitPlaceBox = (EditText) findViewById(R.id.edit_place_box);
        mLocationNameBox = (EditText) findViewById(R.id.edit_location_name_box);
        mStreetAddBox = (EditText) findViewById(R.id.edit_trip_street_address_box);
        mUnitNoBox = (EditText) findViewById(R.id.edit_trip_unit_no_box);
        mCityBox = (EditText) findViewById(R.id.edit_trip_city_box);
        mStateBox = (EditText) findViewById(R.id.edit_trip_state_box);
        mZipBox = (EditText) findViewById(R.id.edit_trip_zip_box);
        mProvideBox = (EditText) findViewById(R.id.edit_trip_provide_box);
        mExtraInfoBox = (EditText) findViewById(R.id.edit_trip_extra_info_box);
        mBioBox = (EditText) findViewById(R.id.edit_trip_bio_box);
        mHostReqBox = (EditText) findViewById(R.id.edit_trip_basic_req_box);
        mHostAddiReqBox = (EditText) findViewById(R.id.edit_trip_extra_req_box);
        mContextBox = (EditText) findViewById(R.id.edit_trip_context_box);
        mPackingListBox = (EditText) findViewById(R.id.edit_trip_packing_list_box);
        mPriceBox = (EditText) findViewById(R.id.edit_trip_price_box);

        //Declaring onscreen variable ends here.//


       /* Get Intents here from EachTripActivity to fill in filled out fields by hosts so they don't
          have to repeat filling in from start again*/

        mTitleText = getIntent().getStringExtra("Title");
        mTaglineText = getIntent().getStringExtra("Tagline");
        mActivityText = getIntent().getStringExtra("Activity");
        mVisitPlaceText = getIntent().getStringExtra("Place");
        mTimeStartText = getIntent().getStringExtra("TimeStart");
        mTimeEndText = getIntent().getStringExtra("TimeEnd");
        mPriceText = getIntent().getStringExtra("Price");
        mLocationText = getIntent().getStringExtra("Location");
        mStreetAddText = getIntent().getStringExtra("Street");
        mCityText = getIntent().getStringExtra("City");
        mStateText = getIntent().getStringExtra("State");
        mZipText = getIntent().getStringExtra("Zip");
        mCountryText = getIntent().getStringExtra("Country");
        mProvideText = getIntent().getStringExtra("Provide");
        mExtraInfoText = getIntent().getStringExtra("ExtraInfo");
        mBasicReqText = getIntent().getStringExtra("BasicReq");
        mAddiReqText = getIntent().getStringExtra("AddiReq");
        mContextText = getIntent().getStringExtra("Context");
        mBioText = getIntent().getStringExtra("Bio");
        mGroupSize = getIntent().getStringExtra("GroupSize");
        mPackingText =getIntent().getStringExtra("Packing");

        random_key_pushed = getIntent().getStringExtra("RandomPushKey");



        //TODO - Read from database for some that couldn't be passed such as language, type & time prepare//
        spinnerRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy").child(random_key_pushed);
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mItemLanguage = dataSnapshot.child("language").getValue().toString();

                mItemType = dataSnapshot.child("type").getValue().toString();
                mItemPrepare = dataSnapshot.child("timePrepare").getValue().toString();


                //Language spinner//
                final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                        getResources().getStringArray(R.array.spinnernames));

                mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Selected item. to be passed as intent
                        item_language = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mLanguageSpinner.setAdapter(myAdapter);
                mLanguageSpinner.setSelection(myAdapter.getPosition(mItemLanguage));



                //Type spinner//
                final ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                        getResources ().getStringArray(R.array.spinnertype));

                mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Selected item. to be passed as intent
                        item_type = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTypeSpinner.setAdapter(myAdapter1);
                try {
                    mTypeSpinner.setSelection(myAdapter1.getPosition(mItemType));
                }
                catch (Exception e) {

                }

                //Time Prepare Spinner//
                final ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                        getResources ().getStringArray(R.array.daysprepare));

                mTimePrepareSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Selected item. to be passed as intent
                        item_prepare = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                myAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTimePrepareSpinner.setAdapter(myAdapter6);
                mTimePrepareSpinner.setSelection(myAdapter6.getPosition(mItemPrepare));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //All spinners instantiate right here so that selected spinners doesn't have to be selected again.//

                //Start Time Spinner//
        final ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                getResources ().getStringArray(R.array.timezonefrom));

        mStartTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Selected item. to be passed as intent
                item_start_time = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStartTimeSpinner.setAdapter(myAdapter2);
        mStartTimeSpinner.setSelection(myAdapter2.getPosition(mTimeStartText));




                                //End Time Spinner//
        final ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                getResources ().getStringArray(R.array.timezoneto));

        mEndTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Selected item. to be passed as intent
                item_end_time = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEndTimeSpinner.setAdapter(myAdapter3);
        mEndTimeSpinner.setSelection(myAdapter3.getPosition(mTimeEndText));



                                 //Country Spinner//
        final ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                getResources ().getStringArray(R.array.countries_array));

        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Selected item. to be passed as intent
                item_country = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrySpinner.setAdapter(myAdapter4);
        mCountrySpinner.setSelection(myAdapter4.getPosition(mCountryText));



                                //Group Size Spinner//
        final ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(EditTripActivity.this, R.layout.spinner_item,
                getResources ().getStringArray(R.array.guestspinner));

        mGroupSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Selected item. to be passed as intent
                item_size = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupSizeSpinner.setAdapter(myAdapter5);
        mGroupSizeSpinner.setSelection(myAdapter5.getPosition(mGroupSize));



        ///////////////************
        ////////////////////////////**********
        ////////////////////////////////////////********
        ///////////////////////////////////////////////////***** Spinner All Ends Here ****///////


        mTitleBox.setText(mTitleText);
        mTaglineBox.setText(mTaglineText);
        mActivityDoBox.setText(mActivityText);
        mVisitPlaceBox.setText(mVisitPlaceText);
        mLocationNameBox.setText(mLocationText);
        mStreetAddBox.setText(mStreetAddText);
        mCityBox.setText(mCityText);
        mStateBox.setText(mStateText);
        mZipBox.setText(mZipText);
        mProvideBox.setText(mProvideText);
        mExtraInfoBox.setText(mExtraInfoText);
        mHostReqBox.setText(mBasicReqText);
        mHostAddiReqBox.setText(mAddiReqText);
        mContextBox.setText(mContextText);
        mBioBox.setText(mBioText);
        mPackingListBox.setText(mPackingText);
        mPriceBox.setText(mPriceText);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(EditTripActivity.this);
                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Saving Trip Details");
                progressDialog.setCanceledOnTouchOutside(false);

                //Store all these registered information into database for later retrieve.

                Map<String , Object> dataMap = new HashMap<String, Object>();
                dataMap.put("title" , mTitleBox.getText().toString().trim());
                dataMap.put("tagline" , mTaglineBox.getText().toString().trim());
                dataMap.put("activity" , mActivityDoBox.getText().toString().trim());
                dataMap.put("place" , mVisitPlaceBox.getText().toString().trim());
                dataMap.put("locationName" , mLocationNameBox.getText().toString().trim());
                dataMap.put("streetAddress" , mStreetAddBox.getText().toString().trim());
                dataMap.put("city" , mCityBox.getText().toString().trim());
                dataMap.put("state" , mStateBox.getText().toString().trim());
                dataMap.put("zipCode" , mZipBox.getText().toString().trim());
                dataMap.put("itemProvide" , mProvideBox.getText().toString().trim());
                dataMap.put("extraInfo" , mExtraInfoBox.getText().toString().trim());
                dataMap.put("basicReq" , mHostReqBox.getText().toString().trim());
                dataMap.put("addiReq" , mHostAddiReqBox.getText().toString().trim());
                dataMap.put("activityContext" , mContextBox.getText().toString().trim());
                dataMap.put("hostBio" , mBioBox.getText().toString().trim());
                dataMap.put("packingList" , mPackingListBox.getText().toString().trim());
                dataMap.put("pricePerGuest" , mPriceBox.getText().toString().trim().trim());
                dataMap.put("language" , item_language);
                dataMap.put("type" , item_type);
                dataMap.put("timePrepare" , item_prepare);
                dataMap.put("maxGroupSize" , item_size);
                dataMap.put("country" , item_country);
                dataMap.put("startTime" , item_start_time);
                dataMap.put("endTime" , item_end_time);

                spinnerRef.updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();

                            Toast.makeText(EditTripActivity.this, "Trip Details Saved", Toast.LENGTH_SHORT).show();

                            Intent backToTripIntent = new Intent(EditTripActivity.this, EachTripActivity.class);
                            backToTripIntent.putExtra("pushKey" , random_key_pushed);
                            startActivity(backToTripIntent);
                            finish();
                        }

                    }
                });


            }
        });




    } ///*************ON CREATE ENDS HERE ****************//////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // your code here
        Intent check = new Intent(EditTripActivity.this, EachTripActivity.class);
        check.putExtra("pushKey", random_key_pushed);
        startActivity(check);

    }

}
