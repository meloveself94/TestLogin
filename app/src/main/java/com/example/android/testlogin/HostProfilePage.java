package com.example.android.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Soul on 11/28/2017.
 */

public class HostProfilePage extends AppCompatActivity {

    private TextView hostName;
    private TextView hostCountry;
    private TextView hostGender;
    private TextView hostOccupation;

    private RoundedImageView hostPic;

    private DatabaseReference mRef;
    private DatabaseReference mFindRef;

    private  String host_value_id;

    private String giveHostName;
    private String giveHostCountry;
    private String giveHostGender;
    private String giveHostOccupation;
    private String giveHostPic;

    private String defaultString = "default";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_profile_page);

        hostName = (TextView) findViewById(R.id.view_name);
        hostCountry = (TextView) findViewById(R.id.view_country);
        hostGender = (TextView) findViewById(R.id.view_gender);
        hostOccupation = (TextView) findViewById(R.id.view_occupation);
        hostPic = (RoundedImageView) findViewById(R.id.view_pic);

        final String random_key_pushed = getIntent().getStringExtra("pushKey");

        mFindRef = FirebaseDatabase.getInstance().getReference().child("comPostsCopy")
                    .child(random_key_pushed).child("hostUID");

        mFindRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                host_value_id = dataSnapshot.getValue().toString();

                mRef = FirebaseDatabase.getInstance().getReference().child("users").child(host_value_id);

                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Try to get hosts attributes is available//
                        try {
                            giveHostCountry = dataSnapshot.child("userCountry").getValue().toString();
                        }
                        catch (Exception e) {
                        }

                        try {
                            giveHostOccupation = dataSnapshot.child("occupation").getValue().toString();
                        }
                        catch (Exception e) {
                        }

                        try {
                            giveHostPic = dataSnapshot.child("userThumbPic").getValue().toString();
                        }
                        catch (Exception e){
                        }


                        giveHostGender = dataSnapshot.child("gender").getValue().toString();
                        giveHostName = dataSnapshot.child("displayName").getValue().toString();





                        //Set the textviews and imageview fields gotten from firebase database
                        // to the created fields from xml.

                        hostGender.setText("Gender: " + giveHostGender);
                        hostName.setText("Name: " + giveHostName);


                     //****//Try to set host occupation inside here **///
                        try {
                            //If host did not include occupation then set the line where host has
                            // not include their occupation.
                            if (giveHostOccupation.equals(defaultString)) {

                        //This inside if starts here.. this inside if statement check whether
                        //host is a male or female to put his / her in the no occupation statement
                                if (giveHostGender.equals("Male")) {
                              hostOccupation.setText("This host has not include his occupation");
                                }
                                else if (giveHostGender.equals("Female")) {
                              hostOccupation.setText("This host has not include her occupation");
                                }  //Inner if statement ends here//**
                            }
                            //This is the outer else where if occupation is not equals to default.
                            else {
                          hostOccupation.setText("Occupation: " + giveHostOccupation);
                            }

                        }catch (Exception e) {
                        }
                        //****//Try to set host occupation ends here **///

                        //****//Try to set host country inside here **///
                        try {
                            //If host did not include origin country then set the line where host has
                            // not include their origin country.
                            if (giveHostCountry.equals(defaultString)) {

                        //This inside if starts here.. this inside if statement check whether
                        //host is a male or female to put his / her in the no occupation statement
                                if (giveHostGender.equals("Male")) {
                               hostCountry.setText("This host has not include his origin country");
                                }
                                else if (giveHostGender.equals("Female")) {
                                hostCountry.setText("This host has not include her origin country");
                                } //Inner if statement ends here//**

                            }
                            else {
                                hostCountry.setText("Country of Origin: " + giveHostCountry);
                            }

                        }catch (Exception e) {
                        }
                        //****//Try to set host occupation ends here **///


                        //****//Try to set host picture inside here **///
                        try {
                            Picasso.with(HostProfilePage.this).load(giveHostPic)
                                    .placeholder(R.drawable.defaultavatar).into(hostPic);
                        }catch (Exception e) {
                         Toast.makeText(HostProfilePage.this, "Host did not set any profile picture"
                                 , Toast.LENGTH_SHORT).show();

                        }       //****//Try to set host picture ends here **///

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









    }  ////////************ ONCREATE ENDS HERE ****************//////////////



}
