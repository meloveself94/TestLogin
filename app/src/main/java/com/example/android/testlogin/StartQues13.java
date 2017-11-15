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
 * Created by Soul on 9/8/2017.
 */

public class StartQues13 extends AppCompatActivity {

    Information objInfo;
    Spinner p13Spinner1;
    Spinner p13Spinner2;
    EditText p13EditBox1;
    Button p13Next;
    String p13item1;
    String p13item2;
    private RelativeLayout p13RelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques13);

        objInfo = (Information) getIntent().getSerializableExtra("REQUIREMENT");




        p13Spinner1 = (Spinner) findViewById(R.id.p13Spinner1);
        p13Spinner2 = (Spinner) findViewById(R.id.p13Spinner2);
        p13Next = (Button) findViewById(R.id.p13Next);
        p13EditBox1 = (EditText) findViewById(R.id.p13EditBox1);
        p13RelativeLayout = (RelativeLayout) findViewById(R.id.p13RelativeLayout);



        //FOR SPINNER 1.

        final ArrayAdapter<String> p13Adapter1 = new ArrayAdapter<String>(StartQues13.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.guestspinner));

        p13Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                p13item1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p13Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p13Spinner1.setAdapter(p13Adapter1);

        //END SPINNER 1.




        //FOR SPINNER 2.


        final ArrayAdapter<String> p13Adapter2 = new ArrayAdapter<String>(StartQues13.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.daysprepare));

        p13Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                p13item2 = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        p13Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p13Spinner2.setAdapter(p13Adapter2);

        //END SPINNER 2.

        p13RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });




        p13Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p13EditBox1.getText().toString();
                int price = 0;

               // int box1 = Integer.parseInt(p13EditBox1.getText().toString());

                if (p13item1.equals("Select Number of Guest")) {
               Toast.makeText(StartQues13.this , "Please Select A Suitable Amount of Guest That Could be Accomodated"
                       , Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(box1)) {
                    p13EditBox1.setError("Please Let Others Know What is The Price of Your Trip Per Person");

                }
                else if (p13item2.equals("Time Required to Prepare")) {
                Toast.makeText(StartQues13.this , "Please Select A Suitable Amount of Time Required for You to be Prepared"
                      , Toast.LENGTH_SHORT).show();

                }
                else {
                    price = Integer.parseInt(box1);


                    objInfo.setP14NoOfGuest(p13item1);
                    objInfo.setP14TimeRequired(p13item2);
                    objInfo.setP14EnterPrice(price);


                    Intent p13Intent = new Intent(StartQues13.this , StartQues14.class);
                    p13Intent.putExtra("PRICE" , objInfo);
                    startActivity(p13Intent);
                }



            }
        });







    }
}
