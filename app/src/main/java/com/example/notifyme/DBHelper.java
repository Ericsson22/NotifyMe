package com.example.notifyme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.DhcpInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.notifyme.Constants.KEY_DESCRIPTION;
import static com.example.notifyme.Constants.KEY_DUE_DATE;
import static com.example.notifyme.Constants.KEY_ID;
import static com.example.notifyme.Constants.KEY_PRIORITY;
import static com.example.notifyme.Constants.KEY_SOLVED;
import static com.example.notifyme.Constants.KEY_TITLE;
import static com.example.notifyme.Constants.TABLE;

//https://stackoverflow.com/questions/23306135/display-data-from-database-using-base-adapter-and-listview
public class DBHelper extends SQLiteOpenHelper{

    Context context;

    public DBHelper(Context context){
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqDatabase){
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_DUE_DATE + " TEXT,"
                + KEY_PRIORITY + " INT" + ")"; //+ KEY_SOLVED + " BOOLEAN"
        sqDatabase.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqDatabase, int oldVersion, int newVersion){
        sqDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqDatabase);
    }

    //insert value
    public void addData(Context context, String taskTitle, String taskDescription,
                        String dueDate, int priority, boolean solved){
        SQLiteDatabase sqDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, taskTitle);
        values.put(KEY_DESCRIPTION,taskDescription);
        values.put(KEY_DUE_DATE, dueDate);
        values.put(KEY_PRIORITY, priority);
        //values.put(KEY_SOLVED, solved);
        sqDatabase.insert(TABLE, null, values);
        sqDatabase.close();
    }

    //delete query
    public void removeEntry(int id){
        String deleteEntry = "DELETE FROM " + TABLE + " where " + KEY_ID + " = " + id;
        SQLiteDatabase sqDatabase = this.getReadableDatabase();
        sqDatabase.execSQL(deleteEntry);
    }

    //get List
    public List<Task> getTaskList(){
        String selectQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Task> taskList = new ArrayList<>();
        Toast.makeText(context,"HI I'M A TOAST",Toast.LENGTH_SHORT).show();

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTaskId(Integer.parseInt(cursor.getString(0)));
                task.setTaskTitle(cursor.getString(1));
                task.setTaskDescription(cursor.getString(2));
                task.setDueDate(cursor.getString(3));
                task.setPriority(Integer.parseInt(cursor.getString(4)));
                //task.setTaskState(Boolean.parseBoolean(cursor.getString(5)));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

}
