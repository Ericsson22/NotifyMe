package com.example.notifyme;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertTask(Task task);

    @Query("select * from " + Constants.DATABASE_NAME)
    public List<Task> getTasks();
}
