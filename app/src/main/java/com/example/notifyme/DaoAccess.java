package com.example.notifyme;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface DaoAccess {
    @Insert
    public void insertTask(Task task);
}
