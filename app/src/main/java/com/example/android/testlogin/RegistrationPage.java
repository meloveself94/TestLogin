package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Zote's on 8/29/2017.
 */

public class RegistrationPage extends AppCompatActivity {

    private EditText userRegister;
    private EditText pwRegister;
    private EditText pwConfirm;
    private EditText emailRegister;
    private Button mRegistration;
    private FirebaseAuth mAuth2;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference pushRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        userRegister = (EditText) findViewById(R.id.userText1);
        pwRegister = (EditText) findViewById(R.id.userPw1);
        pwConfirm = (EditText) findViewById(R.id.confirmPw);
        emailRegister = (EditText) findViewById(R.id.emailReg);
        mRegistration = (Button) findViewById(R.id.register);
        mAuth2 = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String mUsername = userRegister.getText().toString().trim();
               final String mPassword = pwRegister.getText().toString().trim();
               final String mEmail = emailRegister.getText().toString().trim();
                final String mReconPw = pwConfirm.getText().toString().trim();

                if (!mUsername.equals("")&& !mPassword.equals("")&& !mReconPw.equals("") && !mEmail.equals("")){



                if(mPassword.equals(mReconPw)) {




                final ProgressDialog progressDialog = ProgressDialog.show(RegistrationPage.this, "Please wait...", "Processing", true);
                mAuth2.createUserWithEmailAndPassword(emailRegister.getText().toString(),pwRegister.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){

                            //Store all these registered information into database for later retrieve.


                            HashMap<String , String> dataMap = new HashMap<String, String>();
                            dataMap.put("username" , mUsername);
                            dataMap.put("password" , mPassword);
                            dataMap.put("email" , mEmail);

                            //Set all these data to the root of the Database

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String hello = user.getUid();

                            pushRef = mDatabase.child(hello);
                            pushRef.setValue(dataMap);




                            Toast.makeText(RegistrationPage.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Log.e("ERROR OCCURED!!!", task.getException().toString());
                            Toast.makeText(RegistrationPage.this , task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                });

                } else {
                    Toast.makeText(RegistrationPage.this, "Password not match", Toast.LENGTH_SHORT).show();
                }



                } else {
                    Toast.makeText(RegistrationPage.this , "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }







            }
        });


    }

}
