package com.example.android.testlogin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Zote's on 8/28/2017.
 */

public class OverviewPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private GridAdapter adapter;


    private Toolbar mToolbar;
    ImageView chatImage;

    private int year_x;
    private int month_x;
    private int day_x;

    private int year_y;
    private int month_y;
    private int day_y;
    private int mm;

    private int saveDay;
    private int saveMonth;
    private int saveYear;

    Button companionBtn;
    private RecyclerView mTripList;
    private FirebaseAuth mAuth1;
    private FirebaseAuth.AuthStateListener mAuthListener1;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;

    private Calendar myCalender;

    //private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        mToolbar = findViewById(R.id.overview_action_barzz);
        setSupportActionBar(mToolbar);



        //For the recycler view//
        mTripList = findViewById(R.id.my_recycler_view);
        //mTripList.setHasFixedSize(true);
        mTripList.setLayoutManager(new LinearLayoutManager(this));


        //For Buttons to work
        chatImage = findViewById(R.id.overview_chat_image);
        companionBtn = findViewById(R.id.companion_btn);



        //For Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = mCurrentUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("comPostsCopy");

        mAuth1 = FirebaseAuth.getInstance();


        mAuthListener1 = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(OverviewPage.this, MainActivity.class));
                }

            }
        };


        companionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OverviewPage.this, StartQues1.class);
                startActivity(intent1);
            }
        });


        chatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(OverviewPage.this, ChatUsersActivity.class);
                startActivity(chatIntent);
            }
        });

         myCalender = Calendar.getInstance();


        EditText datePickEdit = findViewById(R.id.overviewEditBox1);
        datePickEdit.setHintTextColor(0xFFFFFFFF);
        datePickEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Calendar now = Calendar.getInstance();

                com.borax12.materialdaterangepicker.date.DatePickerDialog datePickerDialog
                        = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance
                        (OverviewPage.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.setStartTitle("Start Date");
                    datePickerDialog.setEndTitle("End Date");
                    datePickerDialog.setAccentColor(0xFFE65100);
                    datePickerDialog.setMinDate(Calendar.getInstance());


                //Maximum date setting part


                    now.add(Calendar.DAY_OF_MONTH, 180);
                    
                    datePickerDialog.setAutoHighlight(true);
                    datePickerDialog.setMaxDate(now);

                    datePickerDialog.dismissOnPause(true);
                    datePickerDialog.show(getFragmentManager(),"Datepickerdialog");


            }
        });









        /*mProgressBar = (ProgressBar) findViewById(R.id.custom_progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);

                }
            }, 4000);
        }*/


    } //-------OnCreate ends here // -------


    @Override
    protected void onStart() {
        super.onStart();

        //Listens to auth state wheter is logged in or not first thing.
        mAuth1.addAuthStateListener(mAuthListener1);


        FirebaseRecyclerAdapter<CardItem, TripsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<CardItem, TripsViewHolder>(

                CardItem.class,
                R.layout.single_card,
                TripsViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(TripsViewHolder viewHolder, CardItem trips, int position) {

                viewHolder.setTitle(trips.getTitle());
                viewHolder.setCountry(trips.getCountry());
                viewHolder.setPrice("Price: $" + trips.getPricePerGuest());
                viewHolder.setUserImage(trips.getThumbPhoto(), getApplicationContext());

                final String key = getRef(position).getKey();

                //Set the whole view to be clickable to the trip profile page.


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent tripProfileIntent = new Intent(OverviewPage.this, EachTripActivity.class);
                        tripProfileIntent.putExtra("pushKey", key);
                        startActivity(tripProfileIntent);


                    }
                });

            }

        };


        mTripList.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    public void onDateSet(com.borax12.materialdaterangepicker.date.DatePickerDialog view, int year,
           int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        monthOfYear = monthOfYear + 1;
        monthOfYearEnd = monthOfYearEnd + 1;




        EditText datePickEdit = findViewById(R.id.overviewEditBox1);
        datePickEdit.setText(String.format("%d/%d/%d ",dayOfMonth,monthOfYear,year)+
        String.format("— %d/%d/%d",dayOfMonthEnd,monthOfYearEnd,yearEnd));

    }

    public void onDateChanged(DatePicker view, int years, int monthOfYears, int dayOfMonths) {


    }




    public static class TripsViewHolder extends RecyclerView.ViewHolder {

        //View used by firebase adapter.
        //Also made to set for onClickListener for the recycler view items.
        View mView;

        public TripsViewHolder(View itemView) {
            super(itemView);

            //Set the view used by firebase adapter to itemview.
            mView = itemView;
        }

        public void setUserImage(String thumbPhoto, Context ctx) {
            ImageView tripImageView = mView.findViewById(R.id.trip_image);

            Picasso.with(ctx).load(thumbPhoto).placeholder(R.drawable.defaultavatar).into(tripImageView);
        }

        public void setTitle(String title) {

            TextView mTripTitle = mView.findViewById(R.id.trip_title);
            mTripTitle.setText(title);

        }

        public void setCountry(String country) {
            TextView mTripCountry = mView.findViewById(R.id.trip_country);
            mTripCountry.setText(country);
        }

        public void setPrice(String pricePerGuest) {
            TextView mTripPrice = mView.findViewById(R.id.trip_price);
            mTripPrice.setText(pricePerGuest);
        }


    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        EditText datePickEdit = findViewById(R.id.overviewEditBox1);
        datePickEdit.setText(sdf.format(myCalender.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.sign_out_menu) {

            mAuth1.signOut();

        }

        if (item.getItemId() == R.id.account_settings) {

            Intent accountSettingsIntent = new Intent(OverviewPage.this, AccountSettings.class);
            startActivity(accountSettingsIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater1 = getMenuInflater();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        inflater1.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setInputType(InputType. TYPE_TEXT_FLAG_CAP_WORDS );
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String formattedText = newText;


               if (newText.length()>=2) {

                    formattedText = newText.substring(0,1).toUpperCase()
                           + newText.substring(1).toLowerCase();

               }


                Query search = mDatabaseReference.orderByChild("country").startAt(formattedText).endAt(formattedText+"\uf8ff");

                Log.d("got search dou mou?" , "Got la");

                FirebaseRecyclerAdapter<CardItem, SearchViewHolder> firebaseRecyclerAdapter22
                        = new FirebaseRecyclerAdapter<CardItem, SearchViewHolder>(
                        CardItem.class,
                        R.layout.single_card,
                        SearchViewHolder.class,
                        search
                ) {
                    @Override
                    protected void populateViewHolder(final SearchViewHolder hiViewHolder, final CardItem model, int position) {

                        hiViewHolder.setPicture(model.getThumbPhoto() , getApplicationContext());
                        hiViewHolder.setTitle(model.getTitle());
                        hiViewHolder.setCountry(model.getCountry());
                        hiViewHolder.setPrice("Price: $" + model.getPricePerGuest());
                    }

                };

                mTripList.setAdapter(firebaseRecyclerAdapter22);


                return false;
            }
        });

        return true;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        View hiView;

        public SearchViewHolder(View itemView) {

            super(itemView);

            //Set the view used by firebase adapter to itemview.
            hiView = itemView;
        }

        public void setCountry(String country) {
            TextView mTripCountry = hiView.findViewById(R.id.trip_country);
            mTripCountry.setText(country);
        }
        public void setPicture(String thumbPhoto, Context ctx) {
            ImageView tripImageView = hiView.findViewById(R.id.trip_image);

            Picasso.with(ctx).load(thumbPhoto).placeholder(R.drawable.defaultavatar).into(tripImageView);
        }

        public void setTitle(String title) {

            TextView mTripTitle = hiView.findViewById(R.id.trip_title);
            mTripTitle.setText(title);

        }



        public void setPrice(String pricePerGuest) {
            TextView mTripPrice = hiView.findViewById(R.id.trip_price);
            mTripPrice.setText(pricePerGuest);
        }


    }
}
