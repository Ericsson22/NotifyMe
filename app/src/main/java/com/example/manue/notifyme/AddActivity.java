package com.example.manue.notifyme;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity{

    private Button saveButton;
    private Spinner reminderSpinner;
    private EditText titleInput, descriptionInput;
    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupUI();
        initDB();
        initListener();
    }

    private void setupUI(){
        saveButton = findViewById(R.id.save_button);
        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.description);

        reminderSpinner = findViewById(R.id.spinner_reminder);
        initSpinner(reminderSpinner,R.array.reminder_array);
    }

    private void initDB(){
        taskDatabase = Room.databaseBuilder(getApplicationContext(),TaskDatabase.class,
                Constants.DATABASE_NAME).fallbackToDestructiveMigration().build();
    }

    private void initListener(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when button is clicked, the new entry is saved
                saveNewEntry();
            }
        });
    }

    private void saveNewEntry(){
        final String title = titleInput.getText().toString();
        final String description = descriptionInput.getText().toString();

        //TODO: change when spinner is initiated!!
        final int reminderId = 0;
        //TODO: dependent on date, change when we know how to
        final boolean solved = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Task newTask = new Task();
                newTask.setTaskTitle(title);
                newTask.setTaskDescription(description);
                newTask.setReminderId(reminderId);
                newTask.setTaskState(solved);

                //add new task in database
                taskDatabase.daoAccess().insertTask(newTask);

                //when finished saving, change back to main activity
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);

                //TODO: notifyDataSetChanged when an adapter is initialised
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initSpinner(Spinner spinner, int arrayID) {

        // Adaptersetup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddActivity.this,
                arrayID, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //TODO: initiate spinner
        // Benötigten Listener Implementieren und die Methoden überschreiben
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v,
                                       int position, long arg3) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}

