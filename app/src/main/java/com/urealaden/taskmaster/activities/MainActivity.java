
package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.urealaden.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "urealadenTask.main";
    public List<Task> tasks = new ArrayList<>();
    public static List<Team> allTeams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }

        String username = preferences.getString("username",null);
        if(username != null){
            ((TextView) findViewById(R.id.textViewUsername)).setText("Welcome back " + username + "!");
        }

//        Team team = Team.builder()
//                .name("Yellow")
//                .build();
//
//
//        Amplify.API.mutate(
//                ModelMutation.create(team),
//                    r -> {
//                        Log.i(TAG, "onCreate: created team");
//                    },
//                    r -> {}
//                    );
//

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response ->{
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

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v ->{
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettingsPageIntent);
        });

        Amplify.API.query(
                ModelQuery.list(Team.class),
                r -> {
                    for(Team t: r.getData()){
                        allTeams.add(t);
                    }
                },
                r -> {}
        );

    }
}
