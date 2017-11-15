package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
    private RelativeLayout p4Relative;

    Information objInfo;






    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques4);

        objInfo = (Information) getIntent().getSerializableExtra("NOTHING1");



        p4EditBox1 = (EditText) findViewById(R.id.p4editBox1);
        p4EditBox2 = (EditText) findViewById(R.id.p4editBox2);

        p4Spinner1 = (Spinner) findViewById(R.id.p4Spinner1);


        //Here starts Spinner 1 start Time.//

        ArrayAdapter<String> p4MyAdapter = new ArrayAdapter<String>(StartQues4.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.timezonefrom));

        p4Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 p4Item = parent.getItemAtPosition(position).toString();


           }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        p4MyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p4Spinner1.setAdapter(p4MyAdapter);

        //Here ends Spinner 1 start Time.//





        p4Spinner2 = (Spinner) findViewById(R.id.p4Spinner2);

        //Here starts Spinner end Time.//

        ArrayAdapter<String> p4MyAdapter2 = new ArrayAdapter<String>(StartQues4.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.timezoneto));

        p4Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                p4Item2 = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p4MyAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p4Spinner2.setAdapter(p4MyAdapter2);

        //Here ends Spinner end Time.//



        //Insert Textbox Here.




        //Insert Button Onclick here


        p4next = (Button) findViewById(R.id.p4button1);

        p4next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String mTitle = p4EditBox1.getText().toString();
                String mTagline = p4EditBox2.getText().toString();


                if (TextUtils.isEmpty(mTitle)) {

                    p4EditBox1.setError("Please Enter A Title For Your Trip");
                    return;
                }
                else if (TextUtils.isEmpty(mTagline)) {

                    p4EditBox2.setError("Please Enter A Tagline For Your Trip");
                    return;
                }
                else if (p4Item.equals("Start Time")) {
                    Toast.makeText(StartQues4.this , "Please Select A Suitable Time to Start Your Trip", Toast.LENGTH_SHORT).show();
                }

                else if (p4Item2.equals("End Time")) {
                    Toast.makeText(StartQues4.this , "Please Select A Suitable Time to End Your Trip", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Intent to following page is allowed by passing the selected spinners
                    //and entered Title and Tagline.

                    objInfo.setP5Title(mTitle);
                    objInfo.setP5Tagline(mTagline);
                    objInfo.setP5StartTime(p4Item);
                    objInfo.setP5EndTime(p4Item2);




                    Intent p4Intent = new Intent(StartQues4.this, StartQues5.class);
                    p4Intent.putExtra("TIME" , objInfo);


                    startActivity(p4Intent);
                    finish();


                }


            }
        });

        p4Relative = (RelativeLayout) findViewById(R.id.p4RelativeLayout);

        p4Relative.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });











    }

}
