package com.example.notifyme;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListTaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> tasks;
    private TextView taskTitle, dueDate, description;


    //this is the adapter for the ListViews (MainActivity & SolvedTasksActivity)
    // we probably need another one for the AddActivity

    public ListTaskAdapter(Context context, List<Task> tasks){
        super(context, R.layout.list_view_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v==null){
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_item, null);
        }

        taskTitle = v.findViewById(R.id.list_title);
        description = v.findViewById(R.id.list_description);
        dueDate = v.findViewById(R.id.list_due_date);

        Task task = tasks.get(position);
        // this is for when we want to change the background color depending on due date and priority
        // v.setBackgroundResource(getColorForPriority(task.getPriority()));
        taskTitle.setText(task.getTaskTitle());
        description.setText("\"" + task.getTaskDescription() + "\"");
        Date date = task.getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy   HH:mm");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(date);
        dueDate.setText(strDate);

        return v;
    }

    //copied this out of Übung 07, change as needed
    private int getColorForPriority(int priority) {

        if (priority == 0) {
            return android.R.color.holo_green_dark;
        } else if (priority == 1) {
            return android.R.color.holo_green_light;
        } else if (priority == 2) {
            return android.R.color.holo_red_light;
        } else if (priority == 3) {
            return android.R.color.holo_red_dark;
        } else {
            return android.R.color.darker_gray;
        }
    }

}
