package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            }
        });
    }
}