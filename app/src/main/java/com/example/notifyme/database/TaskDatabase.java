package com.example.notifyme.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.notifyme.Converters;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TaskDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
