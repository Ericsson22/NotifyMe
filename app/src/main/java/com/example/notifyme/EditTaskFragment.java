package com.example.notifyme;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notifyme.database.Task;
import com.example.notifyme.database.TaskDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.example.notifyme.Constants.HIGHEST_PRIORITY;
import static com.example.notifyme.Constants.NO_REMINDER;

public class EditTaskFragment extends Fragment implements View.OnClickListener {
    
    public static FragmentManager fragmentManager;

    private TaskDatabase taskDatabase;

    private Button editButton, datePickerButton, timePickerButton;
    private EditText titleInput, descriptionInput;

    private int taskPriority;
    private int reminderId;

    private int taskId;

    private Date dueDate;


    public EditTaskFragment() {

    }

    //aufruf des Fragments
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        taskId = getArguments().getInt("taskId");
        String titleTaskText = getArguments().getString("titleTaskText");
        String descriptionTaskText = getArguments().getString("descriptionTaskText");
        int reminderInt = getArguments().getInt("reminderInt");
        int priorityInt = getArguments().getInt("priorityInt");
        String dueDateString = getArguments().getString("dueDateString");

       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            dueDate = simpleDateFormat.parse(dueDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        //das schließen und das darauffolgende aufploppen der Main funktioniert irgendwie noch nicht

        titleInput = view.findViewById(R.id.edit_input_title);
        titleInput.setText(titleTaskText);
        descriptionInput = view.findViewById(R.id.edit_input_description);
        descriptionInput.setText(descriptionTaskText);

        Spinner reminderSpinner = view.findViewById(R.id.edit_spinner_reminder);
        initReminderSpinner(reminderSpinner, R.array.reminder_array);
        reminderSpinner.setSelection(reminderInt);

        Spinner prioritySpinner = view.findViewById(R.id.edit_spinner_prioriy);
        initPrioritySpinner(prioritySpinner, R.array.priority_array);
        prioritySpinner.setSelection(priorityInt);

        //String title = titleEditText.getText().toString();
        editButton = (Button) view.findViewById(R.id.save_edit_button);
        editButton.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_edit_button:
                updateOrSaveEntry(view);
                break;
            default:
                break;
        }
    }
    public void updateOrSaveEntry(View view) {
        long status;

        taskDatabase = Room.databaseBuilder(getContext(), TaskDatabase.class,
                Constants.DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        Task task = taskDatabase.daoAccess().getTask(taskId);
        task.setTaskTitle(titleInput.getText().toString());
        task.setTaskDescription(descriptionInput.getText().toString());
        task.setReminderId(getReminderId());
        task.setPriority(getTaskPriority());
        //task.setDueDate(dueDate);

        //boolean solved = task.isSolved();

        taskDatabase.daoAccess().updateTask(task);

        //Das ist zum beenden des Fragments, einfach n if-Schleife mit den jeweiligen BEdingungen für die DB dazuschreiben
        Toast.makeText(getActivity(), "Abgespeichert", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
        Intent backToHome = new Intent(getActivity(), MainActivity.class);
        startActivity(backToHome);
        return;

        //Die gesamte Methode hier ist aus der App "Notetaker" aus dem Android kurs. In der App wird auch eine Datenbank verwendet!!
        //Hätte es schon gemacht aber kenn mich mit der DB nicht aus. mNote ist in dem Fall die DB

/*        String text = textEditText.getText().toString();
        mNote.setText(text);
        mNote.setTitle(title);
        if (newNote) {
            status = new NoteDB(getActivity()).insertEntry(text, title);
            mNote.setId((int) status);
            if (status == -1) {
                Toast.makeText(getActivity(), "Error inserting the note!", Toast.LENGTH_SHORT).show();
            } else if (status >= 0) {
                newNote = false;
                Toast.makeText(getActivity(), "Note inserted!", Toast.LENGTH_SHORT).show();
            }
            // Update the current entry
        } else {
            status = new NoteDB(getActivity()).updateEntry(mNote.getId(), text, title);
            if (status == -1) {
                Toast.makeText(getActivity(), "Error inserting the note!", Toast.LENGTH_SHORT).show();
            } else if (status >= 0) {
                Toast.makeText(getActivity(), "Note updated!", Toast.LENGTH_SHORT).show();
            }
        }
        if (status >= 0) {
            createNotification(title, text);
        }*/
    }

    private void initReminderSpinner(Spinner spinner, int arrayID) {

        // sets the adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), arrayID,
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
                getContext(), arrayID,
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



    @Override
    public void onDetach(){
        MainActivity.fragmentManager.beginTransaction().detach(EditTaskFragment.this).commit();
        super.onDetach();

    }

    @Override
    public void onStop(){

        super.onStop();
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
}



