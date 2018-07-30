package com.example.notifyme;

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

    private Spinner spinnerRemeberTime;
    private Button addButton;
    private EditText title;
    private EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private void setupUI(){
        spinnerRemeberTime = findViewById(R.id.spinner_remember);
        initSpinner(spinnerRemeberTime,R.array.remember_array);

        title = findViewById(R.id.input_title);
        description = findViewById(R.id.input_description);

        addButton = findViewById(R.id.button_to_add);
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
        private void initButton(){
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

}

