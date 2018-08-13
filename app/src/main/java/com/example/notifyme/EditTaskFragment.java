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

import static com.example.notifyme.MainActivity.fragmentManager;

public class EditTaskFragment extends Fragment {

    private Button editSaveButton;



    public EditTaskFragment() {

    }

    //aufruf des Fragments
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        editSaveButton=view.findViewById(R.id.save_edit_button);
        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().remove(EditTaskFragment.this).commit();

                //das schließen und das darauffolgende aufploppen der Main funktioniert irgendwie noch nicht
            }
        });

        return view;
    }


}
