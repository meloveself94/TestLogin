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

/**
 * Created by Soul on 9/6/2017.
 */

public class StartQues7 extends AppCompatActivity {

    Information objInfo;
    EditText p7EditBox1;
    EditText p7EditBox2;
    EditText p7EditBox3;
    EditText p7EditBox4;
    EditText p7EditBox5;
    EditText p7EditBox6;
    Spinner p7Spinner1;
    Button p7Next;
    String p7Item;
    GridItem gtem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quest7);


        objInfo = (Information) getIntent().getSerializableExtra("ACTIVITY_PLACE");

        p7EditBox1 = (EditText) findViewById(R.id.p7EditBox1);
        p7EditBox2 = (EditText) findViewById(R.id.p7EditBox2);
        p7EditBox3 = (EditText) findViewById(R.id.p7EditBox3);
        p7EditBox4 = (EditText) findViewById(R.id.p7EditBox4);
        p7EditBox5 = (EditText) findViewById(R.id.p7EditBox5);
        p7EditBox6 = (EditText) findViewById(R.id.p7EditBox6);
        p7Next = (Button) findViewById(R.id.p7Next);

        p7Spinner1 = (Spinner) findViewById(R.id.p7Spinner1);

        final ArrayAdapter<String> p7Adapter = new ArrayAdapter<String>(StartQues7.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.countries_array));

        p7Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                p7Item = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p7Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p7Spinner1.setAdapter(p7Adapter);






        p7Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String box1 = p7EditBox1.getText().toString().trim();
                 String box2 = p7EditBox2.getText().toString().trim();
                 String box3 = p7EditBox3.getText().toString().trim();
                 String box4 = p7EditBox4.getText().toString().trim();
                 String box5 = p7EditBox5.getText().toString().trim();
                 String box6 = p7EditBox6.getText().toString().trim();

                objInfo.setP8Country(p7Item);
                objInfo.setP8LocationName(box1);
                objInfo.setP8City(p7Item);
                objInfo.setP8StreetAddress(box2);
                objInfo.setP8UnitNo(box3);
                objInfo.setP8City(box4);
                objInfo.setP8State(box5);
                objInfo.setP8ZipCode(box6);

                gtem.setCountry(p7Item);

                Intent p7Intent = new Intent(StartQues7.this , StartQues8.class);
                p7Intent.putExtra("MEETUP" , objInfo);
                startActivity(p7Intent);
                finish();

            }
        });








    }

}