package com.example.notifyme;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Task {

    //taskId created automatically and ongoing(no number twice)
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int taskId;
    @ColumnInfo(name = "Titel")
    private String taskTitle;
    @ColumnInfo(name = "Beschreibung")
    private String taskDescription;
    //spinner gives back an id; e.g. 1 for "1 hour before", 2 for "24 hours before" etc.
    @ColumnInfo(name = "ErinnerungsID")
    private int reminderId;

    //Date and Time when Task is finished and put into solved Tasks
    @ColumnInfo(name = "Ende der Erinnerung")
    private Date taskFinished;

    //priority of this Task
    @ColumnInfo(name = "Priorität")
    private int priority;
    //differentiates between solved and unsolved; tasks are shown in different activities
    @ColumnInfo(name = "Abgeschlossen")
    private boolean solved;

    public Task(int taskId, String taskTitle, String taskDescription, int reminderId, Date taskFinished, int priority, boolean solved){
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.reminderId = reminderId;
        this.taskFinished = taskFinished;
        this.priority = priority;
        this.solved = solved;

        //addEntryInDatabase(this)?;
    }


    public void setTaskId(@NonNull int taskId) {
        this.taskId = taskId;
    }

    public void setTaskTitle(@NonNull String taskTitle){
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }

    public void setReminderId(@NonNull int reminderId){
        this.reminderId = reminderId;
    }

    public boolean setTaskState(@NonNull boolean solved){
        if(solved){
            return true;
        } else {
            return false;
        }
    }

    public Date getTaskFinished() {
        return taskFinished;
    }

    public int getPriority() {
        return priority;
    }

    public void setTaskFinished(Date taskFinished) {
        this.taskFinished = taskFinished;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }




}
