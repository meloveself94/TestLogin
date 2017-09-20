package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Soul on 9/7/2017.
 */

public class StartQues9 extends AppCompatActivity {

    Information objInfo;

    EditText p9EditBox1;
    Button p9Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques9);

        objInfo = (Information) getIntent().getSerializableExtra("PROVIDE");

        p9EditBox1 = (EditText) findViewById(R.id.p9EditBox1);
        p9Next = (Button) findViewById(R.id.p9Next);

        p9Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p9EditBox1.getText().toString().trim();

                objInfo.setP10ExtraKnowledge(box1);
                Intent p9Intent = new Intent(StartQues9.this , StartQues10.class);
                p9Intent.putExtra("KNOWLEDGE" , objInfo);
                startActivity(p9Intent);

            }
        });






    }
}
