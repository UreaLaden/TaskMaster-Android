
package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
