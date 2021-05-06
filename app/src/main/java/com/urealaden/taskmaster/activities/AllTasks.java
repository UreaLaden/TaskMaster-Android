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

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class AllTasks extends AppCompatActivity {

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
        recyclerView.setAdapter(new TaskAdapter(tasks));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainThreadHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 1){
                    StringJoiner sj = new StringJoiner(", ");
                    for(Task t:tasks){
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
                response ->{
                    for(Task t: response.getData()){
                        tasks.add(t);
                    }
                    mainThreadHandler.sendEmptyMessage(1);
                },
                response -> Log.d(TAG, "retrieving tasks: " + response.toString())
        );


        Button backButton = findViewById(R.id.backButon);
        backButton.setOnClickListener(view ->{
            Intent goBackToMain = new Intent(AllTasks.this, MainActivity.class);
            AllTasks.this.startActivity(goBackToMain);
            startActivity(goBackToMain);
        });


        //RecyclerView Stuff
//        List<TaskItem> tasks = new ArrayList<>();
//        tasks.add(new TaskItem("Clean the gutters","Remove the dead birds from the gutters"));
//        tasks.add(new TaskItem("Empty the trash bins","Trash and recycle bins are overflowing."));
//        tasks.add(new TaskItem("Take kids to the park","Kids are losing their mind and need fresh air"));
//        tasks.add(new TaskItem("Wash the car","I can draw a sculpture using dust from the window"));
//        tasks.add(new TaskItem("Help Anima with homework","Second grade math is no joke"));
//        RecyclerView rv = findViewById(R.id.taskRecyclerView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskRecyclerViewAdapter(tasks));


    }
}
