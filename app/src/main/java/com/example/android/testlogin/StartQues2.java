package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Soul on 9/1/2017.
 */

public class StartQues2 extends AppCompatActivity {

    Spinner spinner1;
    Button p2button1;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference returnDatabase;
    String key;
    String item;
    Information objInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques2);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        spinner1 = (Spinner) findViewById(R.id.p2Spinner1);


        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(StartQues2.this, android.R.layout.simple_list_item_1,
                getResources ().getStringArray(R.array.spinnernames));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //Selected item. to be passed as intent
                 item = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);


        p2button1 = (Button) findViewById(R.id.p2button1);


        p2button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.equals("Select Language")) {

                    Toast.makeText(StartQues2.this , "Please Select A Language To Your Preference", Toast.LENGTH_SHORT).show();
                }

                else {

                    Information objInfo = new Information();
                    objInfo.setP2Language(item);


                    Intent intent = new Intent(StartQues2.this , StartQues2Half.class);
                    intent.putExtra("LANGUAGE" , objInfo);


                    startActivity(intent);


                }



            }
        });


    }







}
