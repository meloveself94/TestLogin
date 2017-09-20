package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Soul on 9/6/2017.
 */

public class StartQues6 extends AppCompatActivity {

    EditText p6EditText1;
    EditText p6EditText2;
    Button p6Next;
    Information objInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques6);




        objInfo = (Information) getIntent().getSerializableExtra("IMAGE");

        p6EditText1 = (EditText) findViewById(R.id.p6EditText1);
        p6EditText2 = (EditText) findViewById(R.id.p6EditText2);
        p6Next = (Button) findViewById(R.id.p6Next);




        p6Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String enterText1 = p6EditText1.getText().toString();
                String enterText2 = p6EditText2.getText().toString();

                objInfo.setP7Activity(enterText1);
                objInfo.setP7Place(enterText2);

                Intent p6Intent = new Intent(StartQues6.this , StartQues7.class);
                p6Intent.putExtra("ACTIVITY_PLACE" , objInfo);

                startActivity(p6Intent);
                finish();



            }
        });





    }

}
