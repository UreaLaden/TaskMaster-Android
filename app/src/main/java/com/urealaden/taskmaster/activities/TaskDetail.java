package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String task = preferences.getString("task",null);
        ((TextView)findViewById(R.id.taskDetailView)).setText(task);
    }
}