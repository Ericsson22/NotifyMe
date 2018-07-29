package com.example.manue.notifyme;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        setupUI();
        initListeners();

        /*initDB();
        doesn't work right now; when uncommented, the app crashes */

        //TODO: initiate adapter (array adapter enough? or custom adapter?)
    }

    private void setupUI() {
        fab = findViewById(R.id.add_button2);
        navigationView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
    }

    private void initListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when button is pressed, you change activities to add a new entry
                changeToAddActivity();
            }
        });
        //checks for right swipes and button clicks?
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initDB(){
        //TODO: avoid crashes, add an adapter
        taskDatabase = Room.databaseBuilder(getApplicationContext(),TaskDatabase.class,
                Constants.DATABASE_NAME).fallbackToDestructiveMigration().build();
        //TODO: filter for activated tasks, "delete" solved tasks
    }


    @Override
    public void onBackPressed() {
        //if back button is pressed while drawer is open, the drawer is closed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } //if not, it changes to the last activity
        else {
            //TODO: maybe add an automatic saving function when a task title exists
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflates the menu; adds items to the action bar if it is seen
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //for the action bar in itself; the second add button and the smaller menu
    public boolean onOptionsItemSelected(MenuItem item) {
        //depending on where on the menu you click (which button you click), you get different actions
        switch(item.getItemId()){
            case R.id.add_button:
                //same as floating action button, changes activities to add a new entry
                changeToAddActivity();
                return true;
                //TODO: maybe only one add button? we'll see
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeToAddActivity(){
        Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addIntent);
    }

    //TODO: kann weg wenn alle if´s ausgefüllt sind
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //das is für die Navigationsseite links an der Seite, vlt. noch mit ner SwitchCase
    public boolean onNavigationItemSelected(MenuItem item) {
        // handle navigation view item clicks here
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            //Angabe von derzeitiger Seite und Zielseite
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if (id == R.id.solved_tasks) {
            //Angabe von derzeitiger Seite und Zielseite
            Intent solvedTasksIntent = new Intent(MainActivity.this, SolvedTasksActivity.class);
            startActivity(solvedTasksIntent);
            return true;
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
