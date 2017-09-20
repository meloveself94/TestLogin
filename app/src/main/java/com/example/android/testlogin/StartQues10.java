package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Soul on 9/7/2017.
 */

public class StartQues10 extends AppCompatActivity {

    Information objInfo;
    Button p10Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques10);

        objInfo = (Information) getIntent().getSerializableExtra("KNOWLEDGE");

        p10Next = (Button) findViewById(R.id.p10Next);

        p10Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p10Intent = new Intent(StartQues10.this , StartQues11.class);
                p10Intent.putExtra("NOTHING2" , objInfo);
                startActivity(p10Intent);


            }
        });





    }

}
