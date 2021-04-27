
package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();


        String username = preferences.getString("username",null);
        if(username != null){
            ((TextView) findViewById(R.id.textViewUsername)).setText(username + "'s tasks");
        }


        Button taskButton = findViewById(R.id.taskButton);
        taskButton.setOnClickListener(view -> {
            Intent goToNewTaskPageIntent = new Intent(MainActivity.this,AddTask.class);
            MainActivity.this.startActivity(goToNewTaskPageIntent);
            startActivity(goToNewTaskPageIntent);
        });
        Button allTasksButton = findViewById(R.id.allTaskButton);
        allTasksButton.setOnClickListener(view ->{
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this,AllTasks.class);
            MainActivity.this.startActivity(goToAllTaskPageIntent);
            startActivity(goToAllTaskPageIntent);
        });
        Button carTaskButton = findViewById(R.id.carTaskButton);
        carTaskButton.setOnClickListener(view ->{

            String carTask = carTaskButton.getText().toString();
            preferenceEditor.putString("task",carTask);
            preferenceEditor.apply();

            Intent goToTaskDetailPageIntent = new Intent(MainActivity.this,TaskDetail.class);
            startActivity(goToTaskDetailPageIntent);
        });

        Button trashButton = findViewById(R.id.trashTaskButton);
        trashButton.setOnClickListener(view ->{

            String trashTask = trashButton.getText().toString();
            preferenceEditor.putString("task",trashTask);
            preferenceEditor.apply();

            Intent goToTaskDetailPageIntent = new Intent(MainActivity.this, TaskDetail.class);
            startActivity(goToTaskDetailPageIntent);
        });
        Button homeWorkButton = findViewById(R.id.homeWorkTaskButton);
        homeWorkButton.setOnClickListener(view ->{

            String homeWorkTask = homeWorkButton.getText().toString();
            preferenceEditor.putString("task",homeWorkTask);
            preferenceEditor.apply();

            Intent goToTaskDetailPageIntent = new Intent(MainActivity.this, TaskDetail.class);
            startActivity(goToTaskDetailPageIntent);
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v ->{
            Intent goToSettingsPageIntent = new Intent(MainActivity.this,Settings.class);
            startActivity(goToSettingsPageIntent);
        });
    }
}
