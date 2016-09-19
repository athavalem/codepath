package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editItem;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_old);

        String item = getIntent().getStringExtra(MainActivity.ITEM);
        position = getIntent().getIntExtra(MainActivity.POSITION,-1);
        editItem = (EditText)findViewById(R.id.editItem);

        editItem.setText(item);

    }


    public void onSave(View V){

        String editedItem = editItem.getText().toString();
        Intent data = new Intent();
        data.putExtra(MainActivity.ITEM,editedItem);
        data.putExtra(MainActivity.POSITION,position);
        setResult(RESULT_OK,data);
        finish();


    }

}
