package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by Soul on 10/2/2017.
 */

public class AccountSettings extends AppCompatActivity {

    private static final int GALLERY_PIC = 1;


    //For Firebase.
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;

    //Storage Reference
    private StorageReference mStorage;

    //For fields
    private CircleImageView mImage;
    private TextView displayName;
    private TextView userStatus;
    private Button chgImageBtn;
    private Button chgStatusBtn;
    private byte[] thumb_byte;


    //Progress
    private ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mImage = (CircleImageView) findViewById(R.id.account_image);
        displayName = (TextView) findViewById(R.id.display_name);
        userStatus = (TextView) findViewById(R.id.account_status);
        chgImageBtn = (Button) findViewById(R.id.change_image);
        chgStatusBtn = (Button) findViewById(R.id.change_status);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUid = mCurrentUser.getUid();

        //We want to work with the offline capabilities of this line here//***
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUid);
        //Therefore, we put keepSynced (for offline) to be true***-----
        mRef.keepSynced(true);
        mStorage = FirebaseStorage.getInstance().getReference();



        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("displayName").getValue().toString();
                 final String image = dataSnapshot.child("userPic").getValue().toString();
                final String thumb_image = dataSnapshot.child("userThumbPic").getValue().toString();

                displayName.setText(name);

                //This if statment is to set that if user did not set any profile image,
                //Then the default image of blank avatar will appear.
                if (!image.equals("default")) {



                    //Set for    =  Image and put image online if available

                    Picasso.with(AccountSettings.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.defaultavatar).resize(400 , 400).into(mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                            //Do nothing if successfully load image offline. Just let it load offline for best.

                        }

                        @Override
                        public void onError() {

                            //But if image is not successfully loaded offline, then load it online.
                            Picasso.with(AccountSettings.this).load(image).placeholder(R.drawable.defaultavatar).resize(400 , 400).into(mImage);

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        chgImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent to open gallery apps for selection.
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, GALLERY_PIC);


            }
        });
    }


    //Override onActivityResult method in your activity to get crop result
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
                mProgressDialog = new ProgressDialog(AccountSettings.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Wait While We Upload & Process Your Image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();



                Uri resultUri = result.getUri();

                //For bitmap usage
                File thumb_filePath = new File(resultUri.getPath());


                //Store firebase filepath using uid as name for profile picture.
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = currentUser.getUid();



                try {
                    Bitmap thumb_bitmap = new Compressor(this)
                            .setMaxWidth(100)
                            .setMaxHeight(100)
                            .setQuality(70)
                            .compressToBitmap(thumb_filePath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    thumb_byte = baos.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Try catch for illegal exception done here//



                //Path for image
                StorageReference filePath = mStorage.child("profile_images").child(userUid + ".jpg");
                //Path for thumbnail
                final StorageReference thumbFilePath = mStorage.child("profile_images").child("thumb").child(userUid + ".jpg");


                //Upload Main Image to AccountSettings.
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                        if (task.isSuccessful()) {
                            //This string saves the URL of the image stored in Firebase Storage.
                            @SuppressWarnings("VisibleForTests") final String downloadLink =
                                    task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumbFilePath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    @SuppressWarnings("VisibleForTests")String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();

                                    if (thumb_task.isSuccessful()) {

                                        Map update_hashMap = new HashMap();
                                        update_hashMap.put("userPic" ,downloadLink);
                                        update_hashMap.put("userThumbPic", thumb_downloadUrl);


                                        mRef.updateChildren(update_hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                        Toast.makeText(AccountSettings.this , "Successfully Uploaded" , Toast.LENGTH_SHORT).show();
                                                        mProgressDialog.dismiss();
                                                    }
                                                });


                                    }else {
                                        Toast.makeText(AccountSettings.this , "Error Uploading Thumbnail" , Toast.LENGTH_SHORT).show();
                                        mProgressDialog.dismiss();

                                    }

                                }
                            });



                        }
                        else {
                            Toast.makeText(AccountSettings.this , "Error" , Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }

                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

}

