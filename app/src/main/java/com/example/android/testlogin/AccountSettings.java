package com.example.android.testlogin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import com.polyak.iconswitch.IconSwitch;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by Soul on 10/2/2017.
 */

public class AccountSettings extends AppCompatActivity {

    private static final int GALLERY_PIC = 1;

    //For Calendar
    private EditText editDateBox;
    Calendar myCalendar = Calendar.getInstance();

    //For Country
    private EditText editCountryBox;

    //For Gender Selection
    private IconSwitch iconSwitch;
    private String whichOne;
    private String gimeFinal;
    private  String gotFinal;


    //For Firebase.
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;

    //Storage Reference
    private StorageReference mStorage;

    //For fields
    private CircleImageView mImage;
    private TextView displayName;
    private Button chgImageBtn;
    private EditText editOccupationBox;

    private AlertDialog.Builder builder;

    private Button accountSaveBtn;

    private byte[] thumb_byte;


    //Progress
    private ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mImage = (CircleImageView) findViewById(R.id.account_image);
        displayName = (TextView) findViewById(R.id.display_name);

        chgImageBtn = (Button) findViewById(R.id.change_image);

        iconSwitch = (IconSwitch) findViewById(R.id.sticky_switch);

        editCountryBox = (EditText) findViewById(R.id.view_profile_country);
        editOccupationBox = (EditText) findViewById(R.id.view_profile_occupation);
        accountSaveBtn = (Button) findViewById(R.id.account_save_button);

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

                String firstCountry = dataSnapshot.child("userCountry").getValue().toString();
                String firstOccu = dataSnapshot.child("occupation").getValue().toString();
                String firstDob = dataSnapshot.child("dateOfBirth").getValue().toString();

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

                if (firstCountry.equals("default")) {
                  editCountryBox.getText().clear();
                }
                else {
                  editCountryBox.setText(firstCountry);
                }
                if (firstDob.equals("default")) {
                  editDateBox.getText().clear();
                }
                else {
                  editDateBox.setText(firstDob);
                }
                 if (firstOccu.equals("default")) {
                  editOccupationBox.getText().clear();
                }
                else {
                  editOccupationBox.setText(firstOccu);
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

            //This is to check the default selection if host did not select or change any gender.//
               final String gotCheck = iconSwitch.getChecked().toString();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              String whichGender =  dataSnapshot.child("gender").getValue().toString();
                if (whichGender.equals("male")) {
                    iconSwitch.setChecked(IconSwitch.Checked.LEFT);
                }
                else if (whichGender.equals("female")) {
                    iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
                }
                else {
                    iconSwitch.setChecked(IconSwitch.Checked.LEFT);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if (gotCheck.equals("LEFT")){
            gotFinal = "male";
        }
        else if (gotCheck.equals("RIGHT")) {
            gotFinal = "female";
        }

        //This is to check if host clicked or changed to different gender.//
        iconSwitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                String isLeft = "LEFT";
                String isRight = "RIGHT";


                  whichOne = current.toString();
                if (whichOne.equals(isLeft)){
                    gimeFinal = "male";
                }
                else if (whichOne.equals(isRight)) {
                    gimeFinal = "female";
                }
            }
        });


        //For Calendar
        editDateBox = (EditText) findViewById(R.id.view_profile_date);
        editDateBox.setHintTextColor(0xFFFFFFFF);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
                myCalendar.set(Calendar.MONTH , monthOfYear);
                myCalendar.set(Calendar.YEAR , year);
                updateLabel();


            }
        };
        editDateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(AccountSettings.this , date , myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        /*stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {


            }
        });*/
        editOccupationBox.setHintTextColor(0xFFFFFFFF);

        //Trying ah
        editCountryBox.setHintTextColor(0xFFFFFFFF);
        editCountryBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(AccountSettings.this, "Select Country", new String[] { "Ok" },
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(which==-1)
                                    Log.d("Neha", "On button click");
                                Toast.makeText(AccountSettings.this, "Hello my friend" , Toast.LENGTH_LONG).show();
                                //Do your functionality here
                                ListView lw = ((AlertDialog)dialog).getListView();
                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                editCountryBox.setText((CharSequence) checkedItem);
                            }
                        });


            }
        }); //Try Done

        accountSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String account_dob = editDateBox.getText().toString().trim();
                String account_gender = gimeFinal;
                String account_gender_check = gotFinal;
                String account_occu = editOccupationBox.getText().toString().trim();
                String account_country = editCountryBox.getText().toString().trim();



                final Map<String , Object> dataMap = new HashMap<String, Object>();
                dataMap.put("dateOfBirth" , account_dob);
                if (account_gender != null && !account_gender.isEmpty()){
                    dataMap.put("gender" , account_gender);
                }
                else {
                    dataMap.put("gender" , account_gender_check);
                }

                dataMap.put("occupation" , account_occu);
                dataMap.put("userCountry", account_country);

                mRef.updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {


                            //Try to see whether any of the values updated are equal to "" or null.
                            //If yes then set their values to "default" and re-update children.
                            try {
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String letDefault = "default";
                            String equalSign = "";
                            String readCountry = (String) dataSnapshot.child("userCountry").getValue();
                            String readOccu = (String) dataSnapshot.child("occupation").getValue();
                            String readDate = (String) dataSnapshot.child("dateOfBirth").getValue();
                            if (readCountry.equals(equalSign) || readCountry.equals(null)) {

                                Map<String , Object> dutuMap = new HashMap<String, Object>();
                                dutuMap.put("userCountry" , letDefault);

                                mRef.updateChildren(dutuMap);
                            }

                            else if (readOccu.equals(equalSign) || readOccu.equals(null)) {
                                Map<String , Object> dotoMap = new HashMap<String, Object>();
                                dotoMap.put("occupation" , letDefault);

                                mRef.updateChildren(dotoMap);

                            } else if (readDate.equals(equalSign) || readDate.equals(null)) {
                                Map<String , Object> dotoMap = new HashMap<String, Object>();
                                dotoMap.put("dateOfBirth" , letDefault);

                                mRef.updateChildren(dotoMap);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                            }
                            catch (Exception e){

                            }

                //Try catch of trying to read any null or "" values in edittext fields ends here//**

                Toast.makeText(AccountSettings.this , "Changes Saved" , Toast.LENGTH_SHORT).show();
                Intent savedAccount = new Intent(AccountSettings.this , OverviewPage.class);
                startActivity(savedAccount);
                finish();

                        }
                        else {

                       Toast.makeText(AccountSettings.this , "Problem saving changes, Please try again"
                                    , Toast.LENGTH_SHORT).show();
                        }

                    }
                });





            }
        });







    }

    //Trying ah
    public void showDialog(Context context, String title, String[] btnText,
                           DialogInterface.OnClickListener listener) {

        String[] myStrings = getResources().getStringArray(R.array.countries_array);
        final CharSequence[] items = { "One", "Two" };

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setSingleChoiceItems(myStrings , -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                    }
                });
        builder.setPositiveButton(btnText[0], listener);

        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }
        builder.show();
    }
    //Try Done


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

         private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In your own order
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale.getDefault());

        editDateBox.setText(sdf.format(myCalendar.getTime()));

    }

}

