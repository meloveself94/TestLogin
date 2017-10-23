package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by Zote's on 8/29/2017.
 */

public class RegistrationPage extends AppCompatActivity {

    private CircleImageView mProfilePic;
    private EditText userRegister;
    private EditText pwRegister;
    private EditText pwConfirm;
    private EditText emailRegister;
    private Button mRegistration;

    private static final int GALLERY_PIC = 1;
    private ProgressDialog mProgressDialog;
    private byte[] thumb_byte;
    private String downloadLink;
    private String thumb_DownloadLink;
    private Uri resultUri;

    private FirebaseAuth mAuth2;
    private FirebaseUser currentFirebaseUser;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private DatabaseReference pushRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        mProfilePic = (CircleImageView) findViewById(R.id.userProfilePic);
        userRegister = (EditText) findViewById(R.id.userText1);
        pwRegister = (EditText) findViewById(R.id.userPw1);
        pwConfirm = (EditText) findViewById(R.id.confirmPw);
        emailRegister = (EditText) findViewById(R.id.emailReg);
        mRegistration = (Button) findViewById(R.id.register);
        mAuth2 = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mStorage = FirebaseStorage.getInstance().getReference();


        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent to open gallery apps for selection.
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, GALLERY_PIC);



            }
        });

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

                            if(mProfilePic.getDrawable()==getResources().getDrawable(R.drawable.addme)) {

                                HashMap<String , String> dataMap = new HashMap<String, String>();
                                dataMap.put("displayName" , mUsername);
                                dataMap.put("password" , mPassword);
                                dataMap.put("email" , mEmail);
                                dataMap.put("userPic" , "default");
                                dataMap.put("userThumbPic" , "default");



                                //Set all these data to the root of the Database

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String hello = user.getUid();

                                pushRef = mDatabase.child(hello);
                                pushRef.setValue(dataMap);



                                Toast.makeText(RegistrationPage.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                                startActivity(intent);

                            } else {



                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                final String hello = user.getUid();

                                //Path for image
                                StorageReference filePath = mStorage.child("profile_images").child(hello + ".jpg");
                                //Path for thumbnail
                                final StorageReference thumbFilePath = mStorage.child("profile_images").child("thumb").child(hello+ ".jpg");

                                //Upload Main Image back to StartQues5
                                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                        if (task.isSuccessful()) {
                                            //This string saves the URL of the image stored in Firebase Storage.
                                            downloadLink = task.getResult().getDownloadUrl().toString();

                                            UploadTask uploadTask = thumbFilePath.putBytes(thumb_byte);
                                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                                    thumb_DownloadLink = thumb_task.getResult().getDownloadUrl().toString();

                                                    if (thumb_task.isSuccessful()) {


                                                        HashMap<String , String> dataMap = new HashMap<String, String>();
                                                        dataMap.put("displayName" , mUsername);
                                                        dataMap.put("password" , mPassword);
                                                        dataMap.put("email" , mEmail);
                                                        dataMap.put("userPic" , downloadLink);
                                                        dataMap.put("userThumbPic" , thumb_DownloadLink);



                                                        //Set all these data to the root of the Database


                                                        pushRef = mDatabase.child(hello);
                                                        pushRef.setValue(dataMap);




                                                        Toast.makeText(RegistrationPage.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                                                        startActivity(intent);



                                                    }


                                                }
                                            });
                                        }

                                    }
                                }); // Path Image ends here





                            }



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

    //Here is where the image thingy starts

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //That means no error
        if (requestCode == GALLERY_PIC && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();



            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(imageUri)
                    .setAspectRatio(1 , 1)
                    .setMinCropWindowSize(500 , 500)
                    .start(this);


        }

        //This is where crop activity returns the cropped image.
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                //Start Progress Dialog here.
                mProgressDialog = new ProgressDialog(RegistrationPage.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Wait While We Upload & Process Your Image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                resultUri = result.getUri();

                Picasso.with(RegistrationPage.this).load(resultUri).into(mProfilePic);

                mProgressDialog.dismiss();



                //For bitmap usage
                File thumb_filePath = new File(resultUri.getPath());



                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(100)
                            .setMaxHeight(100)
                            .setQuality(70)
                            .compressToBitmap(thumb_filePath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , baos);
                    thumb_byte = baos.toByteArray();



                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Try & catch done here












            }


        }

    }

}
