package com.example.android.chatting;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener, Tab1.OnFragmentInteractionListener {

    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar topBar;
    private TabLayout main_tabs;
    private ViewPager viewPager;
    private ProjectAdapter projectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        topBar =  findViewById(R.id.main_page_toolbar);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Chatting");

        viewPager = findViewById(R.id.pagerTab);
        projectAdapter = new ProjectAdapter(getSupportFragmentManager(),3);

        viewPager.setAdapter(projectAdapter);

        main_tabs = findViewById(R.id.tabLayout);
        main_tabs.setupWithViewPager(viewPager);




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            homePage();
        }


    }

    private void homePage(){
        Intent intent = new Intent(MainActivity.this, WelcomePage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logOut){
            FirebaseAuth.getInstance().signOut();
            homePage();
        }else if (item.getItemId() == R.id.account_settings){
            Intent intent = new Intent(MainActivity.this,AccountSettings.class);
            startActivity(intent);
            finish();
        }

        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
