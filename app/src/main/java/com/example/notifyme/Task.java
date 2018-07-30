package com.example.notifyme;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Task {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    //taskId created automatically (hopefully)
    private int taskId;

    private String taskTitle;
    private String taskDescription;
    //spinner gives back an id; e.g. 1 for "1 hour before", 2 for "24 hours before" etc.
    private int reminderId;
    //differentiates between solved and unsolved; tasks are shown in different activities
    private boolean solved;

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
    
    public Task(){

    }

}
