package com.example.android.testlogin;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference pushRef;

    private GoogleSignInButton mGoogleBtn;

    private LoginButton mfacebookBtn;

    private Button mEmailLoginBtn;

    private Button mReplaceFbBtn;

    private Button mReplaceGoogleBtn;

    private EditText enterUsername;

    private EditText enterPassword;

    private TextView mSignUp;

    CallbackManager mCallBackManager;

    private static final int RC_SIGN_IN = 0;


    private GoogleApiClient mGoogleApiClient;

    private static final String TAG = "Main_Activity";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        if(getIntent().hasExtra("logout")){
            LoginManager.getInstance().logOut();
        }
        mEmailLoginBtn = (Button) findViewById(R.id.emailLogin);

        enterUsername = (EditText) findViewById(R.id.userText);

        enterPassword = (EditText) findViewById(R.id.userPw);

        mSignUp = (TextView) findViewById(R.id.signUpText);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mReplaceFbBtn = (Button) findViewById(R.id.replace_fb_btn);

        mReplaceGoogleBtn = (Button) findViewById(R.id.custom_google_btn);

        mGoogleBtn = (GoogleSignInButton) findViewById(R.id.googleLoginss);

        mfacebookBtn = (LoginButton) findViewById(R.id.facebookLogin);



        mAuth = FirebaseAuth.getInstance();

        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
/*
                Account account = getAccount(AccountManager.get(getApplicationContext()));
                String accountName = account.name;
                String fullName = accountName.substring(0,accountName.lastIndexOf("@"));*/

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(i);
            }
        });


        mEmailLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog =  ProgressDialog.show(MainActivity.this, "Please Wait...", "Processing...", true);

                mAuth.signInWithEmailAndPassword(enterUsername.getText().toString() , enterPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this , "Login Successful" , Toast.LENGTH_SHORT).show();
                                    Intent ok = new Intent(MainActivity.this, OverviewPage.class);
                                    ok.putExtra("User_Profile", mAuth.getCurrentUser().getEmail());
                                    startActivity(ok);

                                }
                                else  {
                                    Log.e("ERROR LOGGING IN!!!!!", task.getException().toString());
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mfacebookBtn.setVisibility(View.INVISIBLE);
        mGoogleBtn.setVisibility(View.INVISIBLE);

        mReplaceFbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.replace_fb_btn) {
                    mfacebookBtn.performClick();
                }
            }
        });

        mReplaceGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.custom_google_btn) {
                    mGoogleBtn.performClick();
                }
            }
        });

        mCallBackManager = CallbackManager.Factory.create();
        mfacebookBtn.setReadPermissions("email", "public_profile");
        mfacebookBtn.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            if (firebaseAuth.getCurrentUser() != null) {
                Intent intent = new Intent(MainActivity.this, OverviewPage.class);

                startActivity(intent);
                finish();

            }


            }
        };



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(MainActivity.this, "Error Signing in client", Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        LoginManager.getInstance().logOut();


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }




    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Pass the activity result back to the Facebook SDK
        mCallBackManager.onActivityResult(requestCode, resultCode, data);




        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                String personName = account.getDisplayName().toString();
                String personGivenName = account.getGivenName().toString();
                String personFamilyName = account.getFamilyName().toString();
                String personEmail = account.getEmail().toString();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();

                HashMap<String , String> dataMap = new HashMap<String, String>();
                dataMap.put("displayName" , personName);
                dataMap.put("email" , personEmail);
                dataMap.put("userPic" , String.valueOf(personPhoto));

                //Set all these data to the root of the Database

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //String hello = user.getUid();

               // pushRef = mDatabase.child(hello);
                //pushRef.setValue(dataMap);

            } else {

                Toast.makeText(MainActivity.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    public static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }



}
