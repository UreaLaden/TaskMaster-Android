package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.urealaden.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.urealaden.taskmaster.models.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    public static String TAG = "urealaden.taskmaster.addTask";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicked the submit button");

                ((TextView) findViewById(R.id.submitTextView)).setText("Submit!");
//                String taskTitle = ((TextView) findViewById(R.id.taskTitle)).getText().toString();
//                String taskDescription = ((TextView) findViewById(R.id.taskTitle)).getText().toString();
                // TODO: Comes into play later
            }
        });

    }
}