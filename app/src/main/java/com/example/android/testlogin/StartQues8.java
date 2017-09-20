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

public class StartQues8 extends AppCompatActivity {

    EditText p8Editbox1;
    Button p8Next;
    Information objInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques8);


        objInfo = (Information) getIntent().getSerializableExtra("MEETUP");

        p8Editbox1 = (EditText) findViewById(R.id.p8EditBox1);
        p8Next = (Button) findViewById(R.id.p8Next);


        p8Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p8Editbox1.getText().toString();

                objInfo.setP9Provide(box1);

                Intent p8Intent = new Intent(StartQues8.this, StartQues9.class);
                p8Intent.putExtra("PROVIDE" , objInfo);
                startActivity(p8Intent);
                finish();
            }
        });


    }

}
