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

    //spinner gives back an id; e.g. 1 for "1 hour before", 2 for "24 hours before" etc.
    //spinner or date? delete if necessary
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

    public void setTaskFinished(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTaskState(@NonNull boolean solved){
        this.solved = solved;
    }

    @NonNull
    public int getTaskId() {
        return taskId;
    }

    public boolean isSolved() {
        return solved;
    }

    public String getTaskTitle(){
        return taskTitle;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public int getReminderId(){
        return reminderId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getTaskState(){
        return solved;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
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
