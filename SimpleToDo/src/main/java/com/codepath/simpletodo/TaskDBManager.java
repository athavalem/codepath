package com.codepath.simpletodo;

/**
 * Created by madhavathavale on 9/14/16.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class TaskDBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "taskManager";


    private static final String TABLE_TASKS = "tasks";

    private static final String TAG = "TaskDBManager";


    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATE = "date";

    public TaskDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
            + KEY_PRIORITY + " TEXT," +   KEY_STATUS + " TEXT," + KEY_DATE + " TEXT" + " )";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create tables again
        onCreate(db);
    }




    void addTask(SimpleTask task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_PRIORITY, Boolean.toString(task.isPriorityHigh()));
        values.put(KEY_STATUS, Boolean.toString(task.isStatusComplete()));
        //values.put(KEY_DATE, SimpleTask.FORMAT.format(task.getDate()));
        values.put(KEY_DATE, SimpleTask.FORMAT.format(task.getDate()));

        db.insert(TABLE_TASKS, null, values);
        Log.i(TAG,task.toString());
        db.close(); // Closing database connection
    }


    SimpleTask getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_ID,
                KEY_TITLE, KEY_PRIORITY, KEY_DATE }, KEY_ID + "=?",
            new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SimpleTask task = new SimpleTask(Integer.parseInt(cursor.getString(0)),
            cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getString(4));

        cursor.close();
        db.close();
        return task;
    }


    public List<SimpleTask> getAllTasks() {
        List<SimpleTask> taskList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SimpleTask task = new SimpleTask();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                task.setPriority(cursor.getString(2).equals("true")? 1: 0);
                task.setStatus(cursor.getString(3).equals("true")? 1: 0);
                task.setDate(cursor.getString(4));
                taskList.add(task);
                Log.i("ABCD",task.toString());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }


    public int updateTask(SimpleTask task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_PRIORITY, Boolean.toString(task.isPriorityHigh()));
        values.put(KEY_STATUS, Boolean.toString(task.isStatusComplete()));
        values.put(KEY_DATE, SimpleTask.FORMAT.format(task.getDate()));

        // updating row
        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
            new String[] { String.valueOf(task.getId()) });
    }


    public void deleteTask(SimpleTask task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
            new String[] { String.valueOf(task.getId()) });
        db.close();
    }



    public int getTaskCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
