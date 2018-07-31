package com.example.notifyme;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Toast;
import java.util.Calendar;



public class AddActivity extends AppCompatActivity {

    private Spinner spinnerRemeberTime;
    private Button addButton;
    private EditText title;
    private EditText description;

    private Button datePickerButton;
    int year_x, month_x, day_x;
    static final int DIALOG_ID_DATE = 0;

    private Button timePickerButton;
    int hour_x, minute_x;
    static final int DIALOG_ID_TIME = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCurrentDate();
        showDialogOnDateButtonClicked();


        showTimePickerDialog();

        setupUI();
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

    private void setupUI() {
        spinnerRemeberTime = findViewById(R.id.spinner_remember);
        initSpinner(spinnerRemeberTime, R.array.remember_array);

        title = (EditText) findViewById(R.id.input_title);    //Hab ich das gemacht? ich glaub das muss man irgendwie casten oder?
        description = (EditText) findViewById(R.id.input_description);

        addButton = (Button) findViewById(R.id.button_to_add);
        initButton();
    }

    private void initSpinner(Spinner spinner, int arrayID) {
        // Adaptersetup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                AddActivity.this, arrayID,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        // Benötigten Listener Implementieren und die Methoden überschreiben
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override //hier kommt die weiterverarbeitung der versch. Werte rein
            public void onItemSelected(AdapterView<?> adapterView, View v,
                                       int position, long arg3) {
                //dieser Toast wird aufgerufen, sobald man über den floating + button auf die AddActivity kommt?!
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

    private void initButton() {
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
    }

    //Youtube Android for Beginners 27
    public void showDialogOnDateButtonClicked() {
        datePickerButton = (Button) findViewById(R.id.button_date_picker);
        datePickerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID_DATE);
                    }
                }
        );
    }

    private void getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID_TIME: {
                return new TimePickerDialog(this, timePickerListener, hour_x, minute_x, false);
            }
            case DIALOG_ID_DATE: {
                return new DatePickerDialog(this, datePickerListner, year_x, month_x, day_x);


            }

            }
        return null;
        }

        protected DatePickerDialog.OnDateSetListener datePickerListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_x = year;
                month_x = month + 1;
                day_x = dayOfMonth;
                //Toast.makeText(AddActivity.this, year_x + "/" + month_x + "/" + day_x, Toast.LENGTH_SHORT).show();
                datePickerButton.setText(day_x + "/" + month_x + "/" + year_x);
            }
        };

        //timePicker Dialog youtube for Beginner
    /*@Override
    protected Dialog onCreateDialog(int idt){
      if(idt==DIALOG_ID_TIME) {
          return new TimePickerDialog(AddActivity.this, timePickerListener, hour_x, minute_x,false);
      }else{
          return null;
    }*/

        protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour_x = hourOfDay;
                minute_x = minute;
                timePickerButton.setText(hour_x + ":" + minute_x);
            }
        };

        public void showTimePickerDialog () {
            timePickerButton = (Button) findViewById(R.id.button_time_picker);
            timePickerButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(DIALOG_ID_TIME);
                        }
                    }
            );
        }

    }

}