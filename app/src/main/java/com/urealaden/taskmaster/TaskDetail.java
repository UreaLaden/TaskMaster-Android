package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String task = preferences.getString("task",null);
        ((TextView)findViewById(R.id.taskDetailView)).setText(task);
    }
}