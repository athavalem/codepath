package com.codepath.simpletodo;

import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by madhavathavale on 9/13/16.
 */
public class SimpleTask implements Serializable{

    public static final String ITEM_SEP = System.getProperty("line.separator");

    public  static final String TAG = "SIMPLETASK";

    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String PRIORITY = "priority";
    public final static String STATUS = "status";
    public final static String DATE = "date";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
        "yyyy-MM-dd", Locale.US);

    private String mTitle;

    private int id;

    private Date mDate;

    private boolean isStatusComplete;

    private boolean isPriorityHigh;

    SimpleTask() {

    }

    SimpleTask(String title, boolean priority, boolean status, Date date) {
        this.mTitle = title;
        this.isPriorityHigh = priority;
        this.isStatusComplete = status;
        this.mDate = date;
    }

    SimpleTask(int id, String title, int priority, int status, String date) {
        this.id = id;
        this.mTitle = title;
        this.isPriorityHigh = priority == 1 ?  true: false;
        this.isStatusComplete = status == 1 ?  true: false;
        try {
            this.mDate = SimpleTask.FORMAT.parse(date);
        } catch (ParseException e) {
            Log.i(SimpleTask.TAG,"can not parse date : " + date);
            e.printStackTrace();
        }
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isPriorityHigh() {
        return isPriorityHigh;
    }

    public void setPriorityHigh(boolean priorityHigh) {
        isPriorityHigh = priorityHigh;
    }

    public void setPriority(int priority) {
        this.isPriorityHigh = priority == 1 ?  true: false;
    }

    public boolean isStatusComplete() {
        return isStatusComplete;
    }

    public void setStatusComplete(boolean statusComplete) {
        isStatusComplete = statusComplete;
    }

    public void setStatus(int status) {
        this.isStatusComplete = status == 1 ?  true: false;
    }


    public Date getDate() {
        return mDate;
    }

    public String getDateAsString() {
        return mDate.toString();
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setDate(String date)

    {
        try {
            this.mDate = SimpleTask.FORMAT.parse(date);
        } catch (ParseException e) {
            Log.i(SimpleTask.TAG,"can not parse date : " + date);
            e.printStackTrace();
        }
    }



    public String toString() {
        return getId() + ITEM_SEP + mTitle + ITEM_SEP + Boolean.toString(isPriorityHigh) + ITEM_SEP + Boolean.toString(isStatusComplete) + ITEM_SEP
            + FORMAT.format(mDate);
    }

    public String toLog() {
        return "Title:" + mTitle + ITEM_SEP + "Priority:" + Boolean.toString(isPriorityHigh)
            + ITEM_SEP + "Status:" + Boolean.toString(isStatusComplete) + ITEM_SEP + "Date:"
            + FORMAT.format(mDate) + "\n";
    }
}
