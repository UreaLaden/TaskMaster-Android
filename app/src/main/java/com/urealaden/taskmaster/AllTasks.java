package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Button backButton = findViewById(R.id.backButon);
        backButton.setOnClickListener(view ->{
            Intent goBackToMain = new Intent(AllTasks.this,MainActivity.class);
            AllTasks.this.startActivity(goBackToMain);
            startActivity(goBackToMain);
        });
    }
}
