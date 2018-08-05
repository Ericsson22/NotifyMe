package com.example.notifyme;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.joda.time.LocalDateTime;

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

    //Date and Time when Task is finished and put into solved Tasks
    @ColumnInfo(name = "Erinnerung")
    private String dueDate;

    //priority of this Task
    @ColumnInfo(name = "Priorität")
    private int priority;

    //differentiates between solved and unsolved; tasks are shown in different activities
    //@ColumnInfo(name = "Abgeschlossen")
    //private boolean solved;

    public Task(){

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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /*public void setTaskState(@NonNull boolean solved){
        this.solved = solved;
    }*/

    @NonNull
    public int getTaskId() {
        return taskId;
    }

    /*public boolean isSolved() {
        return solved;
    }*/

    public String getTaskTitle(){
        return taskTitle;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

/*public Task(int taskId, String taskTitle, String taskDescription, int reminderId, Date taskFinished, int priority, boolean solved){
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.reminderId = reminderId;
        this.taskFinished = taskFinished;
        this.priority = priority;
        this.solved = solved;

        //addEntryInDatabase(this)?;
    }*/


}
