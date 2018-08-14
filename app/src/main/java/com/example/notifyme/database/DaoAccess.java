package com.example.notifyme.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.notifyme.Constants;
import com.example.notifyme.database.Task;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertTask(Task task);

    @Query("select * from " + Constants.DATABASE_NAME)
    public List<Task> getTasks();

    @Delete
    public void deleteTask(Task task);

    @Query("SELECT * FROM taskDB WHERE taskId = :taskId")
    public Task getTask(int taskId);

    @Update
    public void updateTask(Task task);
}