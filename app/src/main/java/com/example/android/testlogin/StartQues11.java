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

public class StartQues11 extends AppCompatActivity {

    Information objInfo;
    EditText p11EditBox1;
    Button p11Next;
    private RelativeLayout p11RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques11);

        objInfo = (Information)getIntent().getSerializableExtra("NOTHING2");

        p11EditBox1 = (EditText) findViewById(R.id.p11EditBox1);

        p11Next = (Button) findViewById(R.id.p11Next);

        p11RelativeLayout = (RelativeLayout) findViewById(R.id.p11RelativeLayout);

        p11Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p11EditBox1.getText().toString().trim();

                if (TextUtils.isEmpty(box1)) {
                    p11EditBox1.setError("Please Let Others Know Something About Yourself");
                }

                else {

                    objInfo.setP12Bio(box1);

                    Intent p11Intent = new Intent(StartQues11.this , StartQues12.class);
                    p11Intent.putExtra("BIO" , objInfo);
                    startActivity(p11Intent);

                }

            }
        });

        p11RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });



    }

}


