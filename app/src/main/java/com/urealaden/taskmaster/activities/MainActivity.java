
package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.urealaden.taskmaster.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "TaskMaster.main";
    public List<Task> tasks = new ArrayList<>();
    public static List<Team> allTeams = new ArrayList<>();


//  STORAGE STUFF

    private void uploadFile(String key) {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "TestFile");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("I sure hope this works this time!!!");
            writer.close();
        } catch (Exception exception) {
            Log.e(TAG, "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                key,
                exampleFile,
                result -> Log.i(TAG, "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
        );

    }

    private void downloadFile(String key) {
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir() + "/download.txt"),
                result -> Log.i(TAG, "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e(TAG, "Download Failure", error)
        );

        Amplify.Storage.getUrl(
                key,
                result -> Log.i(TAG, "Successfully generated: " + result.getUrl()),
                error -> Log.e(TAG, "URL generation failure", error)
        );
    }
//


    void logoutCognito() {
        Amplify.Auth.signOut(
                AuthSignOutOptions.builder()
                        .globalSignOut(true)
                        .build(),
                () -> {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "Signed out successfully", Toast.LENGTH_LONG).show();
                },
                error -> Log.d(TAG, "logoutCognito: unable to sign out" + error.toString())
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
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.d(TAG, "Could not initialize Amplify ", e);
        }

//        uploadFile("ExampleKey");
//            downloadFile("ExampleKey");

        String username = preferences.getString("username", null);
        TextView usernameTextView = findViewById(R.id.textViewUsername);
        if (username != null) {
            usernameTextView.setText("Welcome back " + username + "!");
            usernameTextView.setVisibility(View.VISIBLE);
        } else {
            usernameTextView.setVisibility(View.INVISIBLE);
        }

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    Log.i(TAG, "Response: " + response.toString());
                    for (Task t : response.getData()) {
                        tasks.add(t);
                    }

                },
                response -> Log.i(TAG, "retrievingTasks: " + response.toString())
        );

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Login.class));
        });
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {   logoutCognito();   });

        Button taskButton = findViewById(R.id.taskButton);
        taskButton.setOnClickListener(view -> {
            Intent goToNewTaskPageIntent = new Intent(MainActivity.this, AddTask.class);
            startActivity(goToNewTaskPageIntent);
        });
        Button allTasksButton = findViewById(R.id.registerButton);
        allTasksButton.setOnClickListener(view -> {
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this, AllTasks.class);
            startActivity(goToAllTaskPageIntent);
        });
        Button signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(view -> {
            Intent goToAllTaskPageIntent = new Intent(MainActivity.this, Register.class);
            startActivity(goToAllTaskPageIntent);
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            Intent goToSettingsPageIntent = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettingsPageIntent);
        });

        Amplify.API.query(
                ModelQuery.list(Team.class),
                r -> {
                    allTeams.clear();
                    for (Team t : r.getData()) {
                        allTeams.add(t);
                    }
                },
                r -> {
                }
        );

    }
}
