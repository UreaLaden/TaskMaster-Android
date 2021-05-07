package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.urealaden.taskmaster.R;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String task = preferences.getString("task",null);

        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");

        ((TextView)findViewById(R.id.taskDetailsName)).setText(name);
        ((TextView)findViewById(R.id.taskDetailsDescription)).setText(description);
    }
}

