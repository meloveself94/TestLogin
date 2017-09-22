package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Soul on 9/1/2017.
 */

public class StartQues4 extends AppCompatActivity {

    Spinner p4Spinner1;
    Spinner p4Spinner2;
    Button p4next;
    EditText p4EditBox1;
    EditText p4EditBox2;
    String hello1;
    String p4Item;
    String p4Item2;
    DatabaseReference mDatabase;
    DatabaseReference returnDatabasep4;
    DatabaseReference return2Databasep4;
    DatabaseReference return4ButtonDatabase2;
    Information objInfo;
    GridItem gItem;





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques4);

        objInfo = (Information) getIntent().getSerializableExtra("NOTHING1");



        p4EditBox1 = (EditText) findViewById(R.id.p4editBox1);
        p4EditBox2 = (EditText) findViewById(R.id.p4editBox2);

        p4Spinner1 = (Spinner) findViewById(R.id.p4Spinner1);

        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        ArrayAdapter<String> p4MyAdapter = new ArrayAdapter<String>(StartQues4.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.timezonefrom));

        p4Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 p4Item = parent.getItemAtPosition(position).toString();

                //mDatabase.addValueEventListener(new ValueEventListener() {
                  // @Override
                   //public void onDataChange(DataSnapshot dataSnapshot) {

                       // showData(dataSnapshot);
                        //returnDatabasep4.setValue(p4Item);
                //    }

                   // @Override
                   // public void onCancelled(DatabaseError databaseError) {

                  //  }
                //});

           }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        p4MyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p4Spinner1.setAdapter(p4MyAdapter);





        p4Spinner2 = (Spinner) findViewById(R.id.p4Spinner2);

        ArrayAdapter<String> p4MyAdapter2 = new ArrayAdapter<String>(StartQues4.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.timezoneto));

        p4Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                p4Item2 = parent.getItemAtPosition(position).toString();

                //mDatabase.addValueEventListener(new ValueEventListener() {
                    //@Override
                    //public void onDataChange(DataSnapshot dataSnapshot) {

                        //showData2(dataSnapshot);
                        //return2Databasep4.setValue(p4Item2);
                  //  }

                    //@Override
                   // public void onCancelled(DatabaseError databaseError) {

                    //}
               // });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p4MyAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p4Spinner2.setAdapter(p4MyAdapter2);



        //Insert Textbox Here.




        //Insert Button Onclick here


        p4next = (Button) findViewById(R.id.p4button1);

        p4next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save two edittexts to database here.

                //final String mTitle = p4EditBox1.getText().toString().trim();
                //final String mTagline = p4EditBox2.getText().toString().trim();

                //HashMap<String , String> dataMap1 = new HashMap<String, String>();
                //dataMap1.put("Experience Title"  , mTitle);
               // dataMap1.put("Experience Tagline" , mTagline);


                //Set all these data to the respective user's database.
                //return4ButtonDatabase2.setValue(dataMap1);
                //Set.


                //Intent to following page.

                String mTitle = p4EditBox1.getText().toString();
                String mTagline = p4EditBox2.getText().toString();


                objInfo.setP5Title(mTitle);
                objInfo.setP5Tagline(mTagline);
                objInfo.setP5StartTime(p4Item);
                objInfo.setP5EndTime(p4Item2);

                // gItem.setmTitleHere(mTitle);






                Intent p4Intent = new Intent(StartQues4.this, StartQues5.class);
                p4Intent.putExtra("TIME" , objInfo);


                startActivity(p4Intent);
                finish();
            }
        });











    }
    //Method to show data for second spinner.
    //private void showData2(DataSnapshot dataSnapshot) {
        //for (DataSnapshot ds2 : dataSnapshot.getChildren()){
           // String get_Key2 = ds2.getKey();

            //return4ButtonDatabase2 = mDatabase.child(get_Key2);
            //return2Databasep4 = mDatabase.child(get_Key2).child("End Time");

        //}




    //}


    //Method to show data for first spinner
   // private void showData(DataSnapshot dataSnapshot) {
       // for (DataSnapshot ds : dataSnapshot.getChildren()){
           // String get_Key = ds.getKey();

            //returnDatabasep4 = mDatabase.child(get_Key).child("Start Time");
        //}
    //}
}
