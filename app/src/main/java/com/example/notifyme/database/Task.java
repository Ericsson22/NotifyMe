package com.example.notifyme.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.notifyme.Constants;

import java.util.Date;

@Entity(tableName = Constants.DATABASE_NAME)
public class Task {

    //taskId created automatically and ongoing(no number twice)
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int taskId;

    @ColumnInfo(name = "Titel")
    private String taskTitle;

    @ColumnInfo(name = "Beschreibung")
    private String taskDescription;

    //spinner gives back an id; e.g. 0 for "Keine", 1 for "zum Ereigniszeitpunkt" etc.
    @ColumnInfo(name = "ErinnerungsID")
    private int reminderId;

    //Date and Time when Task is finished and put into solved Tasks
    @ColumnInfo(name = "Ende der Erinnerung")
    private Date dueDate;

    //priority of this Task
    @ColumnInfo(name = "Priorität")
    private int priority;

    //differentiates between solved and unsolved; tasks are shown in different activities
    @ColumnInfo(name = "Abgeschlossen")
    private boolean solved;


    public Task(){

    }

    //setter methods
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskTitle(@NonNull String taskTitle){
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setReminderId(int reminderId){
        this.reminderId = reminderId;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    //getter methods
    public int getTaskId() {
        return taskId;
    }

    public String getTaskTitle(){
        return taskTitle;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getReminderId(){
        return reminderId;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isSolved() {
        return solved;
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