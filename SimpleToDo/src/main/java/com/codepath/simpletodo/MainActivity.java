package com.codepath.simpletodo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  SimpleTaskAdapter.OnItemClickListener,TaskEditorFragment.OnFinishListener{

    ArrayList<String> items;

    ListView lvItems;



    SimpleTaskAdapter taskAdapter;
    public static String ITEM = "ITEM";
    public static String POSITION = "POSITION";
    public static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);

        taskAdapter = new SimpleTaskAdapter(this);

        lvItems.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();


    }

    public void addItem(){

        TaskBeingEdited taskBeingEdited = new TaskBeingEdited();
        taskBeingEdited.setPosition(-1);

        SimpleTask task = new SimpleTask();
        taskBeingEdited.setSimpleTask(task);

        FragmentManager fragmentManager = getSupportFragmentManager();
        TaskEditorFragment editDialogFragment = TaskEditorFragment.newInstance(MainActivity.this, taskBeingEdited);
        editDialogFragment.show(fragmentManager,"TAG");
    }


    @Override
    public void onComplete(TaskBeingEdited taskBeingEdited) {

        SimpleTask simpleTask =  taskBeingEdited.getSimpleTask();
        if (taskBeingEdited.isEditCancelled()){
            return;
        }

        int position = taskBeingEdited.getPosition();

        if (taskBeingEdited.isTaskDeleted()){
            taskAdapter.removeTask(position);
            return;
        }

        if (position == -1){
            taskAdapter.addTask(simpleTask);

        }
        else {
            taskAdapter.updateTask(position, simpleTask);
        }


    }


    @Override
    public void onItemClick(int position, boolean isLongClick) {


        if (isLongClick) {
            taskAdapter.removeTask(position);
            return;
        }
        TaskBeingEdited taskBeingEdited = new TaskBeingEdited();
        taskBeingEdited.setPosition(position);

        taskBeingEdited.setSimpleTask((SimpleTask)taskAdapter.getItem(position));

        FragmentManager fragmentManager = getSupportFragmentManager();
        TaskEditorFragment editDialogFragment = TaskEditorFragment.newInstance(MainActivity.this, taskBeingEdited);
        editDialogFragment.show(fragmentManager,"TAG");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addItem();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
