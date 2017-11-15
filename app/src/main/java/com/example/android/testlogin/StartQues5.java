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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;

/**
 * Created by Soul on 9/1/2017.
 */

public class StartQues5 extends AppCompatActivity {

    private static final int GALLERY_PIC = 1;

    Button p5Button1;
    Button p5Button2;
    ImageView p5Image;
    Button p5Next;
    private final int RESULT_LOAD_IMAGE = 0;
    Information objInfo;
    StorageReference mStorageReference;
    private static final String DATABASE_PATH = "images";
    Uri downloadUri;
    private byte[] thumb_byte;


    private ProgressDialog mProgressDialog;

    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ques5);





        p5Button1 = (Button) findViewById(R.id.p5button1);
        p5Button2 = (Button) findViewById(R.id.p5button2);
        p5Image = (ImageView) findViewById(R.id.p5image);
        p5Next = (Button) findViewById(R.id.p5next);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();


        objInfo = (Information) getIntent().getSerializableExtra("TIME");

        p5Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        p5Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(StartQues5.this , "Successfully Uploaded Image" , Toast.LENGTH_SHORT).show();


            }
        });


        p5Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (!"default".equals(p5Image.getTag())) {

                   Intent p5Intent = new Intent(StartQues5.this, StartQues6.class);
                   p5Intent.putExtra("IMAGE", objInfo);
                   startActivity(p5Intent);
               }
                else {
                   Toast.makeText(StartQues5.this , "Please Select A Photo For Your Trip", Toast.LENGTH_SHORT).show();


                }


            }
        });


    }

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
                mProgressDialog = new ProgressDialog(StartQues5.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Wait While We Upload & Process Your Image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();

                Picasso.with(StartQues5.this).load(resultUri).placeholder(R.drawable.placeholder_image).into(p5Image);

                p5Image.setTag("not default");



                //For bitmap usage
                File thumb_filePath = new File(resultUri.getPath());

                //Store firebase filepath using uid as name for profile picture
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = currentUser.getUid();

                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(70)
                            .compressToBitmap(thumb_filePath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , baos);
                    thumb_byte = baos.toByteArray();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Try & catch done here


                //Path for image
                StorageReference filePath = mStorage.child("trip_images").child(userUid).child(UUID.randomUUID().toString() + ".jpg");
                //Path for thumbnail
                final StorageReference thumbFilePath = mStorage.child("trip_images").child("thumb").child(userUid).child(UUID.randomUUID().toString() + ".jpg");

                //Upload Main Image back to StartQues5
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            //This string saves the URL of the image stored in Firebase Storage.
                            final String downloadLink = task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumbFilePath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                String thumb_DownloadLink = thumb_task.getResult().getDownloadUrl().toString();

                                    if (thumb_task.isSuccessful()) {


                                        objInfo.setP6Photo(downloadLink);
                                        objInfo.setP6ThumbPhoto(thumb_DownloadLink);

                                        mProgressDialog.dismiss();

                                    }



                                }
                            });
                        }

                    }
                });











            }


        }

    }
}
