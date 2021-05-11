
package com.urealaden.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignOutOptions;
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


    void logoutCognito(){
        Amplify.Auth.signOut(
                AuthSignOutOptions.builder()
                        .globalSignOut(true)
                        .build(),
                () -> {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"Signed out successfully", Toast.LENGTH_LONG).show();
                },
                error -> Log.e(TAG, "logoutCognito: unable to sign out" + error.toString() )
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }



        String username = preferences.getString("username",null);
        TextView usernameTextView = findViewById(R.id.textViewUsername);
        if(username != null){
            usernameTextView.setText("Welcome back " + username + "!");
            usernameTextView.setVisibility(View.VISIBLE);
        }else{
            usernameTextView.setVisibility(View.INVISIBLE);
        }

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response ->{
                    for(Task t: response.getData()){
                        tasks.add(t);
                    }

                },
                response -> Log.i(TAG, "retrievingTasks: " + response.toString())
        );

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this,Login.class));
        });
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            logoutCognito();
        });

        Button taskButton = findViewById(R.id.taskButton);
        taskButton.setOnClickListener(view -> {
            Intent goToNewTaskPageIntent = new Intent(MainActivity.this, AddTask.class);
            startActivity(goToNewTaskPageIntent);
        });
        Button allTasksButton = findViewById(R.id.registerButton);
        allTasksButton.setOnClickListener(view ->{
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this, AllTasks.class);
            startActivity(goToAllTaskPageIntent);
        });
        Button signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(view ->{
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this, Register.class);
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
                    allTeams.clear();
                    for(Team t: r.getData()){
                        allTeams.add(t);
                    }
                },
                r -> {}
        );

    }
}
