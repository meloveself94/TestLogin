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
 * Created by Soul on 9/8/2017.
 */

public class StartQues12 extends AppCompatActivity {

    Information objInfo;
    EditText p12EditBox1;
    EditText p12EditBox2;
    Button p12Next;
    private RelativeLayout p12RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques12);

        objInfo = (Information) getIntent().getSerializableExtra("BIO");

        p12EditBox1 = (EditText) findViewById(R.id.p12EditBox1);
        p12EditBox2 = (EditText) findViewById(R.id.p12EditBox2);

        p12Next = (Button) findViewById(R.id.p12Next);

        p12RelativeLayout = (RelativeLayout) findViewById(R.id.p12RelativeLayout);

        p12Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p12EditBox1.getText().toString().trim();
                String box2 = p12EditBox2.getText().toString().trim();

                if (TextUtils.isEmpty(box1)) {
                    p12EditBox1.setError("Please Let Others Know if Any Requirement Is Needed");
                    return;
                }
                else if (TextUtils.isEmpty(box2)) {
                    p12EditBox2.setError("Please Let Others Know If There Are Any Additional Requirements");
                    return;
                }

                else {

                    objInfo.setP13Requirement(box1);
                    objInfo.setP13AddRequirement(box2);

                    Intent p12Intent = new Intent(StartQues12.this , StartQues13.class);
                    p12Intent.putExtra("REQUIREMENT" , objInfo);
                    startActivity(p12Intent);

                }
            }
        });

        p12RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


    }
}
