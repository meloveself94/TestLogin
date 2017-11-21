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

/**
 * Created by Soul on 10/23/2017.
 */

public class StartQues2Half  extends AppCompatActivity {

    Information objInfo;
    private Spinner spinner1;
    String item;
    private Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques2half);

        objInfo = (Information) getIntent().getSerializableExtra("LANGUAGE");

        mButtonNext = (Button) findViewById(R.id.p2halfbutton1);

        spinner1 = (Spinner) findViewById(R.id.p2halfspinner);


        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(StartQues2Half.this , android.R.layout.simple_list_item_1
                                                , getResources().getStringArray(R.array.spinnertype));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item.equals("Select Type")) {

                    Toast.makeText(StartQues2Half.this , "Please Select A Type To Your Preference", Toast.LENGTH_SHORT).show();
                }
                else {

                    objInfo.setP2HalfType(item);


                    Intent p2halfIntent = new Intent(StartQues2Half.this , StartQues3.class);
                    p2halfIntent.putExtra("TOURISM" , objInfo);
                    startActivity(p2halfIntent);


                }


            }
        });


    }
}
