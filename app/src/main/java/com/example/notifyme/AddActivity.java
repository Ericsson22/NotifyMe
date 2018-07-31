package com.example.notifyme;

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
import android.widget.Toast;
import java.util.Date;


public class AddActivity extends AppCompatActivity{

    private Spinner reminderSpinner;
    private Button saveButton;
    private EditText titleInput, descriptionInput;
    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupUI();
        //TODO: find mistake, stop app from crashing as soon as clicked on the floating add button
        //when both methods are commented, app doesn't crash --> mistake must be with database
        initDB();
        initListener();
    }

    private void setupUI(){
        saveButton = findViewById(R.id.save_button);
        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.input_description);

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

        //TODO: change later!
        final int reminderId = 0;
        final Date taskFinished = new Date(2018,07,30);
        final int priority = 1;
        final boolean solved = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Task newTask = new Task();
                newTask.setTaskTitle(title);
                newTask.setTaskDescription(description);
                newTask.setReminderId(reminderId);
                newTask.setTaskFinished(taskFinished);
                newTask.setPriority(priority);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                AddActivity.this, arrayID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //TODO: initiate spinner (or the alternative)
        // Benötigten Listener Implementieren und die Methoden überschreiben
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            //hier kommt die weiterverarbeitung der versch. Werte rein
            public void onItemSelected(AdapterView<?> adapterView, View v,
                                       int position, long arg3) {
                //dieser Toast wird aufgerufen, sobald man über den floating + button auf die AddActivity kommt?!
                //Grund: beim Öffnen wird eine Auswahl ("nein") automatisch ausgewählt
                //Und wenn man im Spinner etwas anklickt
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Hallo, 123 check, leider sag ich schon hallo, wenn du die Activity öffnest", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //already in initListener!
    /*private void initButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Durch diesen Klick sollten die Werte als neues Task Objekt gespeichert und in die Datenbank übertragen werden",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    String titleInputString = title.getText().toString();
                    String descriptionInputString = description.getText().toString();

                    //int reminderId = aus Spinner
                    //Date dateTaskFinished = Date aus Spinner, vllt noch in ne bestimmte Form gebracht..
                    //int priority = aus Spinner? oder neue Auswahlmöglichkeit
                    boolean solved = false;
                    //wird wohl keiner auf die Idee kommen, sich ne neue Erinnerung für was bereits abgeschlossenes zu machen, oder?
                    //falls doch, sollten wir das vielleicht als Fehler hier abfangen...

                    //Task task = new Task(id, titleInputString, descriptionInputString, reminderId, dateTaskFinished, priority, solved);
                            //id muss immer weiter hoch zählen, darf nie 2 mal den selben wert geben, also höchsten wert irgendwo abspeichern -> ++
                            //
                }
            });
        }*/

}

