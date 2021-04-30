package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.urealaden.taskmaster.activities.MainActivity;
import com.urealaden.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.urealaden.taskmaster.models.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        Button backButton = findViewById(R.id.backButon);
        backButton.setOnClickListener(view ->{
            Intent goBackToMain = new Intent(AllTasks.this, MainActivity.class);
            AllTasks.this.startActivity(goBackToMain);
            startActivity(goBackToMain);
        });
        //RecyclerView Stuff
        List<TaskItem> tasks = new ArrayList<>();
        tasks.add(new TaskItem("Clean the gutters","Remove the dead birds from the gutters"));
        tasks.add(new TaskItem("Empty the trash bins","Trash and recycle bins are overflowing."));
        tasks.add(new TaskItem("Take kids to the park","Kids are losing their mind and need fresh air"));
        tasks.add(new TaskItem("Wash the car","I can draw a sculpture using dust from the window"));
        tasks.add(new TaskItem("Help Anima with homework","Second grade math is no joke"));
        RecyclerView rv = findViewById(R.id.taskRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new TaskRecyclerViewAdapter(tasks));

        //TODO: Setting this click listener cause the app the crash but Why?
        Button taskDetailsButton = findViewById(R.id.taskDetailsButton);
//        taskDetailsButton.setOnClickListener(v ->{
//
//            String title = ((TextView) findViewById(R.id.taskFragmentTextView)).getText().toString();
//            preferenceEditor.putString("task",title);
//            preferenceEditor.apply();
//
//            Intent goToTaskDetailsPage = new Intent(AllTasks.this,TaskDetail.class);
//            AllTasks.this.startActivity(goToTaskDetailsPage);
//            startActivity(goToTaskDetailsPage);
//        });
    }
}
