
package com.urealaden.taskmaster.activities;

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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.urealaden.taskmaster.AddTask;
import com.urealaden.taskmaster.AllTasks;
import com.urealaden.taskmaster.R;
import com.urealaden.taskmaster.Settings;
import com.urealaden.taskmaster.TaskDetail;
import com.urealaden.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.urealaden.taskmaster.fragments.TaskFragment;
import com.urealaden.taskmaster.models.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "urealadenTask.main";
    public List<Task> tasks = new ArrayList<>();

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


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response ->{
                    String x = "";
                    for(Task t: response.getData()){
                        tasks.add(t);
                    }
                },
                response -> Log.i(TAG, "retrievingTasks: " + response.toString())
        );

        Button taskButton = findViewById(R.id.taskButton);
        taskButton.setOnClickListener(view -> {
            Intent goToNewTaskPageIntent = new Intent(MainActivity.this, AddTask.class);
            MainActivity.this.startActivity(goToNewTaskPageIntent);
            startActivity(goToNewTaskPageIntent);
        });
        Button allTasksButton = findViewById(R.id.allTaskButton);
        allTasksButton.setOnClickListener(view ->{
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this, AllTasks.class);
            MainActivity.this.startActivity(goToAllTaskPageIntent);
            startActivity(goToAllTaskPageIntent);
        });
        Button carTaskButton = findViewById(R.id.carTaskButton);
        carTaskButton.setOnClickListener(view ->{

            String carTask = carTaskButton.getText().toString();
            preferenceEditor.putString("task",carTask);
            preferenceEditor.apply();

            Intent goToTaskDetailPageIntent = new Intent(MainActivity.this, TaskDetail.class);
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
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettingsPageIntent);
        });



    }
}
