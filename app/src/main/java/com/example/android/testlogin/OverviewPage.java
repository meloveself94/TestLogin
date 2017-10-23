package com.example.android.testlogin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Zote's on 8/28/2017.
 */

public class OverviewPage extends AppCompatActivity  {


    private GridAdapter adapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarToggle;
    private Toolbar mToolbar;
    ImageView chatImage;

    Button companionBtn;
    private RecyclerView mTripList;
    private FirebaseAuth mAuth1;
    private FirebaseAuth.AuthStateListener mAuthListener1;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        //For the recycler view//
        mTripList = (RecyclerView) findViewById(R.id.my_recycler_view);
        //mTripList.setHasFixedSize(true);
        mTripList.setLayoutManager(new LinearLayoutManager(this));


        //For Buttons to work
        chatImage = (ImageView) findViewById(R.id.chatImage);
        companionBtn = (Button) findViewById(R.id.companionBtn);

        //For Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = mCurrentUser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("comPostsCopy");
        mAuth1 = FirebaseAuth.getInstance();


        mAuthListener1 = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(OverviewPage.this , MainActivity.class));
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
                Intent chatIntent = new Intent(OverviewPage.this , ChatPage.class);
                startActivity(chatIntent);
            }
        });








        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open , R.string.close);

        mDrawerLayout.addDrawerListener(mActionBarToggle);
        mActionBarToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    } //-------OnCreate ends here // -------



    @Override
    protected void onStart() {
        super.onStart();

        //Listens to auth state wheter is logged in or not first thing.
        mAuth1.addAuthStateListener(mAuthListener1);


        FirebaseRecyclerAdapter<CardItem , TripsViewHolder> firebaseRecyclerAdapter
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
                viewHolder.setPrice("Price: $" +trips.getPricePerGuest());
                viewHolder.setUserImage(trips.getThumbPhoto(), getApplicationContext());

                final String key = getRef(position).getKey();

                //Set the whole view to be clickable to the trip profile page.
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent tripProfileIntent = new Intent(OverviewPage.this , EachTripActivity.class);
                        tripProfileIntent.putExtra("pushKey" , key);
                        startActivity(tripProfileIntent);


                    }
                });

            }
        };



        mTripList.setAdapter(firebaseRecyclerAdapter);



    }

    public static class TripsViewHolder extends RecyclerView.ViewHolder {

        //View used by firebase adapter.
        //Also made to set for onclicklistener for the recycler view items.
        View mView;

        public TripsViewHolder(View itemView) {
            super(itemView);

            //Set the view used by firebase adapter to itemview.
            mView = itemView;
        }

        public void setUserImage(String thumbPhoto , Context ctx) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.sign_out_menu) {

            mAuth1.signOut();

        }

        if (item.getItemId() == R.id.account_settings) {

            Intent accountSettingsIntent =new Intent(OverviewPage.this , AccountSettings.class);
            startActivity(accountSettingsIntent);
        }

        if(mActionBarToggle.onOptionsItemSelected(item)){
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater1 = getMenuInflater();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        inflater1.inflate(R.menu.main_menu , menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);




                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



}
