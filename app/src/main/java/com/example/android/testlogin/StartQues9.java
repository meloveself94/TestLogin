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

public class StartQues9 extends AppCompatActivity {

    Information objInfo;

    EditText p9EditBox1;
    Button p9Next;
    private RelativeLayout p9RelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques9);

        objInfo = (Information) getIntent().getSerializableExtra("PROVIDE");

        p9EditBox1 = (EditText) findViewById(R.id.p9EditBox1);
        p9Next = (Button) findViewById(R.id.p9Next);
        p9RelativeLayout = (RelativeLayout) findViewById(R.id.p9RelativeLayout);

        p9Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String box1 = p9EditBox1.getText().toString().trim();

                if (TextUtils.isEmpty(box1)) {
                    p9EditBox1.setError("Please Let Others Know What Should They Prepare on Their Own");
                }

                else {

                    objInfo.setP10ExtraKnowledge(box1);
                    Intent p9Intent = new Intent(StartQues9.this , StartQues10.class);
                    p9Intent.putExtra("KNOWLEDGE" , objInfo);
                    startActivity(p9Intent);

                }

            }
        });

        p9RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });






    }
}
