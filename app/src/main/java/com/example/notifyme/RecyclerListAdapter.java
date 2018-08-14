//FROM: https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28

package com.example.notifyme;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.TaskViewHolder> {
    List<Task> tasks;

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title, date, description, rating, taskID;

        public TaskViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            description = (TextView) view.findViewById(R.id.description);
            //rating = (TextView) view.findViewById(R.id.rating);
            taskID = (TextView) view.findViewById(R.id.taskID);
        }
    }

    public RecyclerListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTaskTitle());
        holder.date.setText(task.getTaskDescription());

        Date dueDate = task.getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String strDate = dateFormat.format(dueDate);

        holder.description.setText(strDate + " Uhr");
        holder.taskID.setText("TaskID: " + task.getTaskId());

    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

}