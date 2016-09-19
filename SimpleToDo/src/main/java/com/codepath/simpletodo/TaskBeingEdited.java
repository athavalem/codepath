package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Created by madhavathavale on 9/11/16.
 */
public class TaskBeingEdited implements Serializable{


    private SimpleTask simpleTask;

    private int position;

    private  boolean isEditCancelled;

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
}
