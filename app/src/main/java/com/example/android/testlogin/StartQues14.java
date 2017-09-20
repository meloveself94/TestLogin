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

public class StartQues14 extends AppCompatActivity {

    Information objInfo;
    EditText p14EditBox1;
    EditText p14EditBox2;
    Button p14Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques14);

        objInfo = (Information) getIntent().getSerializableExtra("PRICE");

        p14EditBox1 = (EditText) findViewById(R.id.p14EditBox1);
        p14EditBox2 = (EditText) findViewById(R.id.p14EditBox2);

        p14Next = (Button) findViewById(R.id.p14Next);

        p14Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p14EditBox1.getText().toString().trim();
                String box2 = p14EditBox2.getText().toString().trim();

                objInfo.setP15GuestContext(box1);
                objInfo.setP15PackingList(box2);

                Intent p14Intent = new Intent(StartQues14.this , StartQues15.class);
                p14Intent.putExtra("PACKING" , objInfo);
                startActivity(p14Intent);
                finish();



            }
        });


    }
}
