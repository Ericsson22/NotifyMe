package com.example.notifyme;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private ListTaskAdapter listTaskAdapter;
    private ListView listView;
    private TaskDatabase taskDatabase;
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        setupUI();
        initListeners();
        initDB();
        initAdapter();

        //TODO: initiate adapter (array adapter enough? or custom adapter?)
    }

    private void setupUI() {
        fab = findViewById(R.id.floating_add_button);
        navigationView = findViewById(R.id.nav_view);
        listView = findViewById(R.id.list_view);

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

    private void changeToAddActivity(){
        Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addIntent);
    }

    private void initAdapter(){
        listTaskAdapter = new ListTaskAdapter(MainActivity.this, tasks);
        listView.setAdapter(listTaskAdapter);
    }

    private void initDB(){
        /*taskDatabase = Room.databaseBuilder(getApplicationContext(),TaskDatabase.class,
                Constants.DATABASE_NAME).allowMainThreadQueries().build();*/

        tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setTaskId(0);
        task.setTaskTitle("Säftl");
        task.setTaskDescription("asd");
        task.setDueDate(new Date(2018, 8,8,8,8,8));
        task.setPriority(1);
        task.setSolved(false);
        task.setReminderId(2);
        tasks.add(task);

        //tasks = taskDatabase.daoAccess().getTasks();

        String info = "";

        for(Task taskitem : tasks){
            int id = taskitem.getTaskId();
            String title = taskitem.getTaskTitle();
            String description = taskitem.getTaskDescription();
            int reminderId = taskitem.getReminderId();
            Date dueDate = taskitem.getDueDate();
            int priority = taskitem.getPriority();
            boolean solved = taskitem.getTaskState();

            info = "ID: " + id + " Title: " + title + " Description: " + description + " ReminderID: " + reminderId + " DueDate: " + dueDate + " Priority: " + priority + " Solved: " + solved;
            System.out.println("" + info);
        }

        //TODO: filter for activated tasks, "delete" solved tasks
    }

    @Override
    public void onBackPressed() {
        //if back button is pressed while drawer is open, the drawer is closed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } //if not, it changes to the last activity
        else {
            //TODO: maybe add an automatic saving function when a task title exists (here?)
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    //for the action bar in itself; the second add button and the smaller menu
    public boolean onOptionsItemSelected(MenuItem item) {
        //depending on where on the menu you click (which button you click), you get different actions
        switch(item.getItemId()){
            case R.id.add_button:
                //same as floating action button, changes activities to add a new entry
                changeToAddActivity();
                return true;
                //maybe only one add button?
        }
        return super.onOptionsItemSelected(item);
    }*/

    //TODO kann weg wenn alle if´s ausgefüllt sind
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //das is für die Navigationsseite Links an der Seite
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class); //Angabe von derzeitiger Seite und Zielseite
                startActivity(settingsIntent);
                break;

            case R.id.solved_tasks:
                Intent solvedTasksIntent = new Intent(MainActivity.this, SolvedTasksActivity.class); //Angabe von derzeitiger Seite und Zielseite
                startActivity(solvedTasksIntent);
                break;

            case R.id.nav_slideshow:
                break;

            case R.id.nav_manage:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_send:
                break;

            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
