package com.example.notifyme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.notifyme.MainActivity.fragmentManager;

public class EditTaskFragment extends Fragment implements View.OnClickListener {

    private Button editSaveButton;
    public static FragmentManager fragmentManager;


    public EditTaskFragment() {

    }

    //aufruf des Fragments
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        //das schließen und das darauffolgende aufploppen der Main funktioniert irgendwie noch nicht
        editSaveButton = (Button) view.findViewById(R.id.save_edit_button);
        editSaveButton.setOnClickListener(this);

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
        EditText titleEditText = (EditText) getView().findViewById(R.id.edit_input_title);
        EditText textEditText = (EditText) getView().findViewById(R.id.edit_input_description);
        String title = titleEditText.getText().toString();

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

    @Override
    public void onDetach(){
        MainActivity.fragmentManager.beginTransaction().detach(EditTaskFragment.this).commit();
        super.onDetach();

    }

    @Override
    public void onStop(){

        super.onStop();
    }
}



