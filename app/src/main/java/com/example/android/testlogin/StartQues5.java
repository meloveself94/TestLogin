package com.example.android.testlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Soul on 9/1/2017.
 */

public class StartQues5 extends AppCompatActivity {

    Button p5Button1;
    Button p5Button2;
    ImageView p5Image;
    Button p5Next;
    private final int RESULT_LOAD_IMAGE = 0;
    Information objInfo;
    StorageReference mStorageReference;
    GridItem gItem;
    private static final String DATABASE_PATH = "images";
    Uri downloadUri;

    Uri picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques5);

        p5Button1 = (Button) findViewById(R.id.p5button1);
        p5Button2 = (Button) findViewById(R.id.p5button2);
        p5Image = (ImageView) findViewById(R.id.p5image);
        p5Next = (Button) findViewById(R.id.p5next);
        mStorageReference = FirebaseStorage.getInstance().getReference();

        objInfo =  (Information) getIntent().getSerializableExtra("TIME");

        p5Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , RESULT_LOAD_IMAGE);






            }
        });


        p5Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        p5Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent p5Intent = new Intent(StartQues5.this , StartQues6.class);

                objInfo.setP6Photo(downloadUri.toString());
                gItem.setmImageView(downloadUri.toString());



                p5Intent.putExtra("IMAGE" , objInfo);
                startActivity(p5Intent);
                finish();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == RESULT_LOAD_IMAGE) {

                //Get Uri

                picturePath = data.getData();




                //Convert a URI to a sream.

                InputStream openInputStream;
                try {

                     openInputStream = getContentResolver().openInputStream(picturePath);


                    //Convert a Stream to a Bitmap Factory
                    //Decode stream from Bitmap Factory to Bitmap Object.
                    //Open the image as Bitmap. Put it into ImageView.
                    Bitmap plantPicture = BitmapFactory.decodeStream(openInputStream);


                     Bitmap resizedBitmap = Bitmap.createScaledBitmap(plantPicture,1280,960,true);


                    //Convert a Bitmap Factory to a Bitmap
                    //Show the bitmap through our imageView.
                    p5Image.setImageBitmap(resizedBitmap);







                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    private void uploadImage(){

        if (picturePath != null)
        {
            final ProgressDialog mProgressDialog = new ProgressDialog(StartQues5.this);
            mProgressDialog.setTitle("Uploading...");
            mProgressDialog.show();

            StorageReference ref = mStorageReference.child("images" + UUID.randomUUID().toString());
            ref.putFile(picturePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();

                    downloadUri = taskSnapshot.getDownloadUrl();


                    Toast.makeText(StartQues5.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    mProgressDialog.dismiss();
                    Toast.makeText(StartQues5.this, "Something Went Wrong, Please Try Again ", Toast.LENGTH_SHORT).show();

                }
            })

              .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                      double progress = (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());

                      mProgressDialog.setMessage("Uploading " + (int) progress + "%");

                  }
              });

        }

    }
}
