package com.codepath.simpletodo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    public void onAddItem(View v){

        TaskBeingEdited taskBeingEdited = new TaskBeingEdited();
        taskBeingEdited.setPosition(-1);

        SimpleTask task = new SimpleTask();
        taskBeingEdited.setSimpleTask(task);

        FragmentManager fragmentManager = getSupportFragmentManager();
        TaskEditorFragment editDialogFragment = TaskEditorFragment.newInstance(MainActivity.this, taskBeingEdited);
        editDialogFragment.show(fragmentManager,"TAG");
    }



    private void setupListViewListener(){
        Log.i("AAAA", "here");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

              taskAdapter.removeTask(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AAAA", "here2");

                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
                TaskBeingEdited taskBeingEdited = new TaskBeingEdited();
                taskBeingEdited.setPosition(position);

                taskBeingEdited.setSimpleTask((SimpleTask)taskAdapter.getItem(position));

                FragmentManager fragmentManager = getSupportFragmentManager();
                TaskEditorFragment editDialogFragment = TaskEditorFragment.newInstance(MainActivity.this, taskBeingEdited);
                editDialogFragment.show(fragmentManager,"TAG");
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//         if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
//
//             String editedItem = data.getExtras().getString(MainActivity.ITEM);
//             int position = data.getExtras().getInt(MainActivity.POSITION);
//             taskAdapter.updateTask(position,editedItem);
//
//         }
//      }

    @Override
    public void onComplete(TaskBeingEdited taskBeingEdited) {

        SimpleTask simpleTask =  taskBeingEdited.getSimpleTask();
        if (taskBeingEdited.isEditCancelled()){
            return;
        }
        String editedItem = simpleTask.getTitle();
        int position = taskBeingEdited.getPosition();
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
}
