package com.example.notifyme;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.notifyme.database.Task;
import com.example.notifyme.database.TaskDatabase;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.text.DecimalFormat;
import java.util.Date;

import static com.example.notifyme.Constants.HIGHEST_PRIORITY;
import static com.example.notifyme.Constants.NO_REMINDER;


public class AddActivity extends AppCompatActivity {

    private TaskDatabase taskDatabase;

    private Button saveButton, datePickerButton, timePickerButton;
    private EditText titleInput, descriptionInput;

    static final int DIALOG_ID_DATE = 0;
    static final int DIALOG_ID_TIME = 1;

    private int timePickerHour, timePickerMinute;

    private LocalDate notificationDate;
    private LocalTime notificationTime;
    private LocalDateTime notificationDateAndTime;

    private int taskPriority;
    private int reminderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
        initDB();
        initListener();
        initService();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setMessage(R.string.back_button_warning);
        builder.setPositiveButton(R.string.back_button_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(R.string.back_button_no, null);

        AlertDialog saveAlert = builder.create();
        saveAlert.show();
    }

    private void setupUI() {
        saveButton = findViewById(R.id.save_button);
        titleInput = findViewById(R.id.input_title);
        descriptionInput = findViewById(R.id.input_description);

        Spinner reminderSpinner = findViewById(R.id.spinner_reminder);
        initReminderSpinner(reminderSpinner, R.array.reminder_array);
        Spinner prioritySpinner = findViewById(R.id.spinner_prioriy);
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
                putNotificationDateAndTimeTogether();
                saveNewEntry();
                changeBackToMainActivity();
            }
        });
    }

    private void initService(){

    }

    private void saveNewEntry() {
        final String title = titleInput.getText().toString();
        final String description = descriptionInput.getText().toString();
        final int reminderId = getReminderId();
        final int priority = getTaskPriority();

        LocalDateTime localDueDate = getNotificationDateAndTime();
        final Date dueDate = localDueDate.toDate();
        final boolean solved = getTaskState(localDueDate);
        LocalDateTime localDateTime = new LocalDateTime(dueDate);


        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO: handle if entries are null.
                Task newTask = new Task();
                newTask.setTaskTitle(title);
                newTask.setTaskDescription(description);
                newTask.setReminderId(reminderId);
                newTask.setDueDate(dueDate);
                newTask.setPriority(priority);
                newTask.setSolved(solved);

                //add new task in database
                taskDatabase.daoAccess().insertTask(newTask);
            }
        }).start();
    }

    private void changeBackToMainActivity(){
        //when finished saving, change back to main activity
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
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

    private void initReminderSpinner(Spinner spinner, int arrayID) {

        // sets the adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                AddActivity.this, arrayID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // implements listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            //hier kommt die weiterverarbeitung der versch. Werte rein
            public void onItemSelected(AdapterView<?> adapterView, View v,
                                       int position, long arg3) {
                //sets the reminderID to the number of list position
                setReminderId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setReminderId(NO_REMINDER);
            }
        });
    }

    private void initPrioritySpinner(Spinner spinner, final int arrayID) {
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
                setTaskPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setTaskPriority(HIGHEST_PRIORITY);
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

    protected DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            LocalDate notificationDate = new LocalDate(year, month, dayOfMonth);
            setNotificationDate(notificationDate);
            DecimalFormat df = new DecimalFormat("00");
            datePickerButton.setText("" + df.format(dayOfMonth) + "." + df.format(month + 1)+ "." + df.format(year) );
        }
    };

    //timePicker Dialog youtube for Beginner
    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            LocalTime notificationTime = new LocalTime(hourOfDay, minute);
            setNotificationTime(notificationTime);
            DecimalFormat df = new DecimalFormat("00");
            timePickerButton.setText("" + df.format(hourOfDay) + ":" + df.format(minute));
        }
    };

    private void putNotificationDateAndTimeTogether() {
        notificationTime = getNotificationTime();
        notificationDate = getNotificationDate();
        notificationDateAndTime = new LocalDateTime(notificationDate.getYear(), notificationDate.getMonthOfYear() + 1,
                notificationDate.getDayOfMonth(), notificationTime.getHourOfDay(), notificationTime.getMinuteOfHour());
        setNotificationDateAndTime(notificationDateAndTime);
    }


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

    //Gemeinsames Abrufen von TimePickern und DatePickern
    @Override
    protected Dialog onCreateDialog(int id) {
        LocalDateTime currentDate = new LocalDateTime(LocalDateTime.now());
        int datePickerYear = currentDate.getYear();
        int datePickerMonth = currentDate.getMonthOfYear();
        int datePickerDay = currentDate.getDayOfMonth();

        switch (id) {
            case DIALOG_ID_TIME: {
                return new TimePickerDialog(this, timePickerListener, timePickerHour, timePickerMinute, true);
            }
            case DIALOG_ID_DATE: {
                return new DatePickerDialog(this, datePickerListener, datePickerYear, datePickerMonth - 1, datePickerDay);
            }
        }
        return null;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public LocalDateTime getNotificationDateAndTime() {
        return notificationDateAndTime;
    }

    public void setNotificationDateAndTime(LocalDateTime notificationDateAndTime) {
        this.notificationDateAndTime = notificationDateAndTime;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public boolean getTaskState(LocalDateTime taskFinished) {
        LocalDateTime currentDate = new LocalDateTime(LocalDateTime.now());
        //if year is the same and day of year is in the future, task isn't solved
        if((taskFinished.getYear() == currentDate.getYear()) &&
                (taskFinished.getDayOfYear() > currentDate.getDayOfYear())){
            return false;
        } //if year is in the future, task isn't solved either
        else if (taskFinished.getYear() > currentDate.getYear()){
            return false;
        }
        //if date is in the past, task is solved
        return true;
    }
}