package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.urealaden.taskmaster.R;

import java.util.ArrayList;

import static com.urealaden.taskmaster.activities.MainActivity.allTeams;

public class AddTask extends AppCompatActivity {

    public String TAG = "urealaden.addTask";
    Team selectedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitButton = findViewById(R.id.submitButton);
        Spinner spinner = findViewById(R.id.spinnerTeam);
        ArrayList<String> nameArr = new ArrayList<>();
        for(Team t:allTeams){
            nameArr.add(t.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_fragment,R.id.spinnerTextView,nameArr);
        spinner.setAdapter(adapter);

        submitButton.setOnClickListener( v-> {
            String title = ((EditText) findViewById(R.id.taskTitle)).getText().toString();
            String description = ((EditText) findViewById(R.id.taskDescription)).getText().toString();
            String team = ((TextView) findViewById(R.id.spinnerTextView)).getText().toString();

            for(Team t: allTeams){
                if(team.contains(t.getName())){
                    selectedTeam = t;
                }
            }

           Task task = Task.builder()
                   .name(title)
                   .description(description)
                   .team(selectedTeam)
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