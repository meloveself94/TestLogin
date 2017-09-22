package com.example.android.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Zote's on 8/28/2017.
 */

public class OverviewPage extends AppCompatActivity  {

    private GridView lvPackage;
    private GridAdapter adapter;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarToggle;
    private Toolbar mToolbar;


    Button companionBtn;
    Button logoutBtn;
    private FirebaseAuth mAuth1;
    private FirebaseAuth.AuthStateListener mAuthListener1;
    private DatabaseReference mDatabaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        lvPackage = (GridView) findViewById(R.id.grid);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        companionBtn = (Button) findViewById(R.id.companionBtn);
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




        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth1.signOut();



            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open , R.string.close);

        mDrawerLayout.addDrawerListener(mActionBarToggle);
        mActionBarToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        //Add sample data for list;
        //We can get from Database and Webservice here.



        final ArrayList<GridItem> elements = new ArrayList<GridItem>();




        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                elements.clear();

                //Loop Through Children
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    String photoUrl = (String) ds.child("photo").getValue();
                    String title = (String) ds.child("experienceTitle").getValue();
                    String country = (String) ds.child("country").getValue();
                    String price = (String) ds.child("price").getValue();

                    GridItem mGriditem = new GridItem(photoUrl,title,country,price);
                    elements.add(mGriditem);



                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Initialize Adapter

        adapter = new GridAdapter(this, elements );
        lvPackage.setAdapter(adapter);


        lvPackage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
                Toast.makeText(OverviewPage.this, "Not yet", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();

        mAuth1.addAuthStateListener(mAuthListener1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarToggle.onOptionsItemSelected(item)){
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

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
