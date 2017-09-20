package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Soul on 8/31/2017.
 */

public class StartQues1 extends AppCompatActivity {

    Button page1Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques1);




        page1Next = (Button) findViewById(R.id.page1Next);

        page1Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent intent = new Intent(StartQues1.this, StartQues2.class);
                startActivity(intent);
                finish();
            }
        });


    }


}
