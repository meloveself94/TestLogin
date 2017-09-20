package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Soul on 9/1/2017.
 */

public class StartQues3 extends AppCompatActivity {

    Button p3button2;
    Information objInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques3);

        p3button2 = (Button) findViewById(R.id.p3button2);

        objInfo =(Information) getIntent().getSerializableExtra("LANGUAGE");





        p3button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartQues3.this , StartQues4.class);
                intent.putExtra("NOTHING1", objInfo);
                startActivity(intent);
                finish();
            }
        });




    }
}
