package com.example.notifyme;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private RecyclerListAdapter recyclerListAdapter;
    private TaskDatabase taskDatabase;
    List<Task> tasks;

    public static FragmentManager fragmentManager;

    private Button editSaveButton;

    RecyclerView recyclerView;
    private SwipeController swipeController;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        setupUI();
        initListeners();
        initDB();
        initAdapter();
        initItemTouchHelper();

    }

    private void setupUI() {
        floatingActionButton = findViewById(R.id.floating_add_button);
        navigationView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_list_View);
    }

    private void initListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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

    private void changeToAddActivity() {
        Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addIntent);
    }

    private void initAdapter() {
        recyclerListAdapter = new RecyclerListAdapter(tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerListAdapter);
    }

    private void initDB() {
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class,
                Constants.DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        tasks = taskDatabase.daoAccess().getTasks();

        String info = "";

        for (Task taskItem : tasks) {
            int id = taskItem.getTaskId();
            String title = taskItem.getTaskTitle();
            String description = taskItem.getTaskDescription();
            int reminderId = taskItem.getReminderId();
            Date dueDate = taskItem.getDueDate();
            int priority = taskItem.getPriority();
            boolean solved = taskItem.getTaskState();
        }

        //TODO: filter for activated tasks, "delete" solved tasks
    }

    private void initItemTouchHelper() {
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

                Task task = recyclerListAdapter.tasks.get(position);
                taskDatabase.daoAccess().deleteTask(task);

                recyclerListAdapter.tasks.remove(position);
                recyclerListAdapter.notifyItemRemoved(position);
                recyclerListAdapter.notifyItemRangeChanged(position, recyclerListAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Task task = recyclerListAdapter.tasks.get(position);
                FragmentManager fragmentManager=getSupportFragmentManager();
                if(findViewById(R.id.fragment_container)!=null) {

                    final FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    final EditTaskFragment editTaskFragment=new EditTaskFragment();

                    fragmentTransaction.isAddToBackStackAllowed();
                    fragmentTransaction.add(R.id.fragment_container,editTaskFragment, null);
                    fragmentTransaction.commit();
                }
            }
        });
        itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
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

        switch (item.getItemId()) {
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
