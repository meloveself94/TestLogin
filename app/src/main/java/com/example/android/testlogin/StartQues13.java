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
    GridItem gItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques13);

        objInfo = (Information) getIntent().getSerializableExtra("REQUIREMENT");




        p13Spinner1 = (Spinner) findViewById(R.id.p13Spinner1);
        p13Spinner2 = (Spinner) findViewById(R.id.p13Spinner2);
        p13Next = (Button) findViewById(R.id.p13Next);
        p13EditBox1 = (EditText) findViewById(R.id.p13EditBox1);



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




        p13Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int box1 = Integer.parseInt(p13EditBox1.getText().toString());

                objInfo.setP14NoOfGuest(p13item1);
                objInfo.setP14TimeRequired(p13item2);
                objInfo.setP14EnterPrice(box1);

                //gItem.setmPriceHere(String.valueOf(box1));




                Intent p13Intent = new Intent(StartQues13.this , StartQues14.class);
                p13Intent.putExtra("PRICE" , objInfo);
                startActivity(p13Intent);
                finish();


            }
        });







    }
}
