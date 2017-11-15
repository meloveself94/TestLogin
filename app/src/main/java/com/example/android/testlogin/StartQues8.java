package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by Soul on 9/7/2017.
 */

public class StartQues8 extends AppCompatActivity {

    EditText p8Editbox1;
    Button p8Next;
    Information objInfo;
    private RelativeLayout p8RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques8);


        objInfo = (Information) getIntent().getSerializableExtra("MEETUP");

        p8Editbox1 = (EditText) findViewById(R.id.p8EditBox1);
        p8Next = (Button) findViewById(R.id.p8Next);
        p8RelativeLayout = (RelativeLayout) findViewById(R.id.p8RelativeLayout);


        p8Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p8Editbox1.getText().toString();

                if (TextUtils.isEmpty(box1)) {
                    p8Editbox1.setError("Please Let Others Know What You'll Provide");
                }
                else {

                    objInfo.setP9Provide(box1);

                    Intent p8Intent = new Intent(StartQues8.this, StartQues9.class);
                    p8Intent.putExtra("PROVIDE" , objInfo);
                    startActivity(p8Intent);

                }


            }
        });

        p8RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


    }

}
