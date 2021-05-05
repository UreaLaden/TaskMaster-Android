package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.urealaden.taskmaster.activities.MainActivity;
import com.urealaden.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.urealaden.taskmaster.models.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    public String TAG = "urealaden.addTask";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener( v-> {
           String title = ((EditText) findViewById(R.id.taskTitle)).getText().toString();
           String description = ((EditText) findViewById(R.id.taskDescription)).getText().toString();

           Task task = Task.builder()
                   .name(title)
                   .description(description)
                   .build();

            Amplify.API.mutate(
                    ModelMutation.create(task),
                    response -> {
                        Log.i(TAG, "onCreate: successfully added task ");
                    },
                    response ->{
                        Log.i(TAG, "onCreate: failed to add task");
                    }
            );
            Intent intent = new Intent(AddTask.this, MainActivity.class);
            AddTask.this.startActivity(intent);
            startActivity(intent);
        });




    }
}