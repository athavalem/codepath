package com.codepath.simpletodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madhavathavale on 9/14/16.
 */
public class SimpleTaskAdapter extends BaseAdapter {
    public interface OnItemClickListener {
        void onItemClick(int position, boolean isLongClick);
    }

    private List<SimpleTask> tasks = new ArrayList<SimpleTask>();
    OnItemClickListener onItemClickListener;

    private final Context mContext;

    private TaskDBManager dbManager;


    private static final String TAG = "SIMPLE-TO-DO";

    public SimpleTaskAdapter(MainActivity context) {

        mContext = context;
        dbManager = new TaskDBManager(context);
        tasks = dbManager.getAllTasks();
        onItemClickListener = context;

    }

    public List<SimpleTask> getTasks() {

        return tasks;
    }


    // Add a SimpleTask to the adapter
    // Notify observers that the data set has changed

    public void addTask(SimpleTask task) {

        tasks.add(task);
        dbManager.addTask(task);
        notifyDataSetChanged();

    }

    public void removeTask(int position) {

        SimpleTask task = tasks.get(position);

        dbManager.deleteTask(task);
        tasks.remove(position);
        notifyDataSetChanged();

    }

    public void updateTask(int position, SimpleTask task) {


        dbManager.updateTask(task);
        tasks.set(position, task);
        notifyDataSetChanged();

    }

    // Clears the list adapter of all items.

    public void clear() {

        tasks.clear();

        notifyDataSetChanged();

    }

    // Returns the number of SimpleTasks

    @Override
    public int getCount() {

        return tasks.size();

    }

    // Retrieve the number of SimpleTasks

    @Override
    public Object getItem(int pos) {

        return tasks.get(pos);

    }

    // Get the ID for the SimpleTask
    // In this case it's just the position

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final SimpleTask simpleTask = tasks.get(position);


        RelativeLayout taskLayout = null;

        LayoutInflater inflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        taskLayout = (RelativeLayout) inflater.inflate(R.layout.simple_task, null);

        TextView titleView = (TextView) taskLayout.findViewById(R.id.txtTaskTitle);

        Log.i(TAG, simpleTask.toString());

        titleView.setText(simpleTask.getTitle());

        final CheckBox chkStatus = (CheckBox) taskLayout.findViewById(R.id.chkStatus);

        chkStatus.setChecked(simpleTask.isStatusComplete());

        final CheckBox chkPriority = (CheckBox) taskLayout.findViewById(R.id.chkPriority);

        chkPriority.setChecked(simpleTask.isPriorityHigh());


        taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Short click");
                onItemClickListener.onItemClick(position,false);

            }
        });
        taskLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "Long Click");
                Log.i(TAG, Integer.toString(position));
                onItemClickListener.onItemClick(position,true);
                return true;
            }
        });

        chkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                chkStatus.setChecked(isChecked);
                simpleTask.setStatusComplete(isChecked);
                dbManager.updateTask(simpleTask);


            }
        });

        chkPriority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkPriority.setChecked(isChecked);
                simpleTask.setPriorityHigh(isChecked);
                dbManager.updateTask(simpleTask);

            }
        });



        TextView dateView = (TextView) taskLayout.findViewById(R.id.txtDate);

        dateView.setText(SimpleTask.FORMAT.format(simpleTask.getDate()));
        return taskLayout;

    }
}
