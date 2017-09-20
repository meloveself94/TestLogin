package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Soul on 9/8/2017.
 */

public class StartQues12 extends AppCompatActivity {

    Information objInfo;
    EditText p12EditBox1;
    EditText p12EditBox2;
    Button p12Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques12);

        objInfo = (Information) getIntent().getSerializableExtra("BIO");

        p12EditBox1 = (EditText) findViewById(R.id.p12EditBox1);
        p12EditBox2 = (EditText) findViewById(R.id.p12EditBox2);

        p12Next = (Button) findViewById(R.id.p12Next);

        p12Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p12EditBox1.getText().toString().trim();
                String box2 = p12EditBox2.getText().toString().trim();

                objInfo.setP13Requirement(box1);
                objInfo.setP13AddRequirement(box2);

                Intent p12Intent = new Intent(StartQues12.this , StartQues13.class);
                p12Intent.putExtra("REQUIREMENT" , objInfo);
                startActivity(p12Intent);
                finish();


            }
        });


    }
}
