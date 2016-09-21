package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Created by madhavathavale on 9/11/16.
 */

/////////////////
// Helper class for communication between fragment and main activity
//////////////

public class TaskBeingEdited implements Serializable{


    private SimpleTask simpleTask;

    private int position;

    private  boolean isEditCancelled;

    private  boolean isTaskDeleted;

    public SimpleTask getSimpleTask() {
        return simpleTask;
    }

    public void setSimpleTask(SimpleTask simpleTask) {
        this.simpleTask = simpleTask;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isEditCancelled() {
        return isEditCancelled;
    }

    public void setEditCancelled(boolean editCancelled) {
        isEditCancelled = editCancelled;
    }

    public boolean isTaskDeleted() {
        return isTaskDeleted;
    }

    public void setTaskDeleted(boolean taskDeleted) {
        isTaskDeleted = taskDeleted;
    }
}
