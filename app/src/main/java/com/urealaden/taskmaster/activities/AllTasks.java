package com.urealaden.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.urealaden.taskmaster.R;
import com.urealaden.taskmaster.adapters.TaskAdapter;
import com.urealaden.taskmaster.adapters.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.security.auth.login.LoginException;

public class AllTasks extends AppCompatActivity implements TaskAdapter.OnClickable {

    public String TAG = "urealaden.all";

    public List<Task> tasks = new ArrayList<>();
    Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor preferenceEditor = preferences.edit();

        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setAdapter(new TaskAdapter(tasks,this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainThreadHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    StringJoiner sj = new StringJoiner(", ");
                    for (Task t : tasks) {
                        sj.add(t.getName());
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task t : response.getData()) {
                        tasks.add(t);
                    }
                    mainThreadHandler.sendEmptyMessage(1);
                },
                response -> Log.d(TAG, "retrieving tasks: " + response.toString())
        );


        Button backButton = findViewById(R.id.backButon);
        backButton.setOnClickListener(view -> {
            Intent goBackToMain = new Intent(AllTasks.this, MainActivity.class);
            AllTasks.this.startActivity(goBackToMain);
            startActivity(goBackToMain);
        });

    }
    
    public void handleClickOnTask(TaskAdapter.TaskViewHolder vh) {
        Log.i(TAG, "handleClickOnTask: Clicked on task");
        Intent intent = new Intent(AllTasks.this, TaskDetail.class);
        intent.putExtra("taskId", vh.task.getId());
        intent.putExtra("name", vh.task.getName());
        intent.putExtra("description", vh.task.getDescription());
        AllTasks.this.startActivity(intent);
        startActivity(intent);
    }

}
