package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Soul on 9/8/2017.
 */

public class StartQues15 extends AppCompatActivity {

    Information objInfo;
    CheckBox p15CheckBox1;
    CheckBox p15CheckBox2;
    DatabaseReference mDatabase;
    FirebaseUser mCurrentUser;



    Button p15Submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques15);

        objInfo = (Information) getIntent().getSerializableExtra("PACKING");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = mCurrentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("comPostsCopy").child(currentUid);

        p15CheckBox1 = (CheckBox) findViewById(R.id.p15CheckBox1);
        p15CheckBox2 = (CheckBox) findViewById(R.id.p15CheckBox2);

        p15Submit = (Button) findViewById(R.id.p15Submit);



        p15Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(p15CheckBox1.isChecked()&& p15CheckBox2.isChecked()){

                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    String hello = mUser.getUid();

                    final ProgressDialog progressDialog = new ProgressDialog(StartQues15.this);
                    progressDialog.setTitle("Registering Companion");
                    progressDialog.setMessage("Please wait.. Finalizing trip");
                    progressDialog.setCancelable(false);

                    //Store all these registered information into database for later retrieve.

                    HashMap<String , String> dataMap = new HashMap<String, String>();
                    dataMap.put("language" , objInfo.getP2Language());
                    dataMap.put("experienceTitle" , objInfo.getP5Title());
                    dataMap.put("startTime" , objInfo.getP5StartTime());
                    dataMap.put("endTime" , objInfo.getP5EndTime());
                    dataMap.put("tripTagline" , objInfo.getP5Tagline());
                    dataMap.put("photo" , objInfo.getP6Photo());
                    dataMap.put("thumbPhoto" , objInfo.getP6ThumbPhoto());
                    dataMap.put("activity " , objInfo.getP7Activity());
                    dataMap.put("place" , objInfo.getP7Place());
                    dataMap.put("locationName" , objInfo.getP8LocationName());
                    dataMap.put("country" , objInfo.getP8Country());
                    dataMap.put("streetAddress" , objInfo.getP8StreetAddress());
                    dataMap.put("unitNo" , objInfo.getP8UnitNo());
                    dataMap.put("city" , objInfo.getP8City());
                    dataMap.put("state" , objInfo.getP8State());
                    dataMap.put("zipCode" , objInfo.getP8ZipCode());
                    dataMap.put("provide" , objInfo.getP9Provide());
                    dataMap.put("extraKnowledge" , objInfo.getP10ExtraKnowledge());
                    dataMap.put("bio" , objInfo.getP12Bio());
                    dataMap.put("basicRequirement" , objInfo.getP13Requirement());
                    dataMap.put("additionalRequirement" , objInfo.getP13AddRequirement());
                    dataMap.put("guestNumber" , objInfo.getP14NoOfGuest());
                    dataMap.put("price" , String.valueOf(objInfo.getP14EnterPrice()));
                    dataMap.put("requiredTime" , objInfo.getP14TimeRequired());
                    dataMap.put("guestContext" , objInfo.getP15GuestContext());
                    dataMap.put("packingList" , objInfo.getP15PackingList());
                    dataMap.put("hostUID" , hello);





                    //Set all these data to the root of the Database





                    mDatabase.setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();


                                Intent p15Intent = new Intent(StartQues15.this , OverviewPage.class);
                                p15Intent.putExtra("MAIN",objInfo);
                                startActivity(p15Intent);
                                finish();

                            }

                            else {
                                Toast.makeText(StartQues15.this , "Something went wrong, Please try again",Toast.LENGTH_SHORT).show();



                            }

                        }
                    });
















                } else {
                    Toast.makeText(StartQues15.this , "Checkbox not checked", Toast.LENGTH_SHORT).show();
                }




            }
        });






    }


}
