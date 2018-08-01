package com.example.notifyme;

import android.annotation.SuppressLint;
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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;


public class AddActivity extends AppCompatActivity {

    private Spinner reminderSpinner;
    private Spinner prioritySpinner;
    private Button saveButton, datePickerButton, timePickerButton;
    private EditText titleInput, descriptionInput;
    private TaskDatabase taskDatabase;


    private int datePickerYear, datePickerMonth, datePickerDay;
    private int timePickerHour, timePickerMinute;

    static final int DIALOG_ID_DATE = 0;
    static final int DIALOG_ID_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCurrentDate();
        setupUI();
        //TODO: find mistake, stop app from crashing as soon as clicked on the floating add button
        //when both methods are commented, app doesn't crash --> mistake must be with database
        initDB();
        initListener();
    }

    private void setupUI() {
        saveButton = findViewById(R.id.save_button);
        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.input_description);

        reminderSpinner = findViewById(R.id.spinner_reminder);
        initNotificationSpinner(reminderSpinner, R.array.reminder_array);
        prioritySpinner=findViewById(R.id.spinner_prioriy);
        initPrioritySpinner(prioritySpinner, R.array.priority_array);

        showDatePicker();
        showTimePicker();
    }

    private void initDB() {
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class,
                Constants.DATABASE_NAME).fallbackToDestructiveMigration().build();
    }

    private void initListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when button is clicked, the new entry is saved
                saveNewEntry();
            }
        });
    }

    private void saveNewEntry() {
        final String title = titleInput.getText().toString();
        final String description = descriptionInput.getText().toString();

        //TODO: change later!
        final int reminderId = 0;
        final Date taskFinished = new Date(2018, 7, 30);
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
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
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

    private void initNotificationSpinner(Spinner spinner, int arrayID) {

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

    private void initPrioritySpinner(Spinner spinner, int arrayID) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                AddActivity.this, arrayID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

    //Youtube Android for Beginners 27
    public void showDatePicker() {
        datePickerButton = findViewById(R.id.button_date_picker);
        datePickerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID_DATE);
                    }
                });
    }

    private void getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        datePickerYear = calendar.get(Calendar.YEAR);
        datePickerMonth = calendar.get(Calendar.MONTH);
        datePickerDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected DatePickerDialog.OnDateSetListener datePickerListner = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            datePickerYear = year;
            datePickerMonth = month + 1;
            datePickerDay = dayOfMonth;
            dateWriteInNorm();
        }
    };

    //timePicker Dialog youtube for Beginner
    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timePickerHour = hourOfDay;
            timePickerMinute = minute;
            timeWriteInNorm();
        }
    };

    public void showTimePicker() {
        timePickerButton = findViewById(R.id.button_time_picker);
        timePickerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID_TIME);
                    }
                }
        );
    }
    //Wandelt die Beschriftung des Buttons mit der Zeit in das Format 00:00 Uhr um
    private void timeWriteInNorm (){
        if(timePickerMinute<10&&timePickerHour<10) {
            timePickerButton.setText("0" + timePickerHour + ":" + "0" + timePickerMinute +" "+ "Uhr");
        }else if(timePickerMinute<10&&timePickerHour>=10){
            timePickerButton.setText(timePickerHour + ":" + "0" + timePickerMinute + " "+"Uhr");
        }else if (timePickerMinute>=10&&timePickerHour<10){
            timePickerButton.setText( "0" +timePickerHour + ":" + timePickerMinute + " "+"Uhr");
        }else {
            timePickerButton.setText(timePickerHour + ":" + timePickerMinute + " "+ "Uhr");
        }
    }
    //Wandelt die Beschriftung des Buttons mit der Datum in das Format 00.00.0000 um
    private void dateWriteInNorm (){
        if(datePickerMonth<10&&datePickerDay<10) {
            datePickerButton.setText("0" + datePickerDay + "." + "0" + datePickerMonth +"."+ datePickerYear);
        }else if(datePickerMonth<10&&datePickerDay>=10){
            datePickerButton.setText(datePickerDay + "." + "0" + datePickerMonth + "."+datePickerYear);
        }else if (datePickerMonth>=10&&datePickerDay<10){
            datePickerButton.setText( "0" +datePickerDay + "." + datePickerMonth+ "."+datePickerYear);
        }else {
            datePickerButton.setText(timePickerHour + ":" + timePickerMinute + " "+ "Uhr");
        }
    }


    //Gemeinsames Abrufen von TimePickern und DatePickern
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID_TIME: {
                return new TimePickerDialog(this, timePickerListener, timePickerHour, timePickerMinute, true);
            }
            case DIALOG_ID_DATE: {
                return new DatePickerDialog(this, datePickerListner, datePickerYear, datePickerMonth, datePickerDay);
            }
        }
        return null;
    }
}
