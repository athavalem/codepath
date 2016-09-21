package com.codepath.simpletodo;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by madhavathavale on 9/15/16.
 */

// Test for DB.
public class DataBaseTest extends AndroidTestCase {
    private TaskDBManager db;
    public RenamingDelegatingContext context;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = new RenamingDelegatingContext(getContext(), "test_");
        db = new TaskDBManager(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }


    //@Test
    public void testAddEntry(){

        Log.i("ABC", "ABCD");

    }

    //@Test
    public void testCreateDB(){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        assertTrue(sqlDB.isOpen());
        Log.i("ABC", "testCreateDB");

    }

    public void testInsert(){
        Date dt = new Date();
        SimpleTask task = new SimpleTask("ABC",true,false, dt);
        db.addTask(task);
        Log.i("ABC", "testInsert");

        List<SimpleTask> lst = db.getAllTasks();
        Log.i("ABC size", Integer.toString(lst.size()));
        for (SimpleTask simpleTask : lst){
            Log.i("ANC", simpleTask.toString());
        }
        Log.i("ABC", "testGet");

    }

}