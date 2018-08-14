//FROM: https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28

package com.example.notifyme;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notifyme.database.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.notifyme.Constants.CASUAL;
import static com.example.notifyme.Constants.HIGHEST_PRIORITY;
import static com.example.notifyme.Constants.NOT_AS_URGENT;
import static com.example.notifyme.Constants.URGENT;
import static com.example.notifyme.Constants.VERY_URGENT;


class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.TaskViewHolder> {
    List<Task> tasks;

    private View itemView;

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title, date, description, priority, taskID;

        public TaskViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            description = (TextView) view.findViewById(R.id.description);
            //priority = (TextView) view.findViewById(R.id.priority);
            taskID = (TextView) view.findViewById(R.id.taskID);
        }
    }

    public RecyclerListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        int priority = task.getPriority();
        itemView.setBackgroundResource(getColorForPriority(priority));
        holder.title.setText(task.getTaskTitle());
        holder.description.setText(task.getTaskDescription());

        Date dueDate = task.getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String strDate = dateFormat.format(dueDate);

        holder.date.setText(strDate + " Uhr");
        holder.taskID.setText("TaskID: " + task.getTaskId());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //no idea where to put this yet
    private int getColorForPriority(int priority) {
        if (priority == HIGHEST_PRIORITY) {
            return android.R.color.holo_red_light;
        } else if (priority == VERY_URGENT) {
            return android.R.color.holo_orange_dark;
        } else if (priority == URGENT) {
            return android.R.color.holo_orange_light;
        } else if (priority == NOT_AS_URGENT) {
            return android.R.color.white;
        } else {
            return android.R.color.darker_gray;
        }
    }

}