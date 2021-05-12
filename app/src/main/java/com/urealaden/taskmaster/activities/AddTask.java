package com.urealaden.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.urealaden.taskmaster.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.urealaden.taskmaster.activities.MainActivity.allTeams;

public class AddTask extends AppCompatActivity {

    static int GET_IMAGE_CODE = 97;
    public String TAG = "urealaden.signOut";
    Team selectedTeam;
    Handler handler;
    List<Task> totalTasks = new ArrayList<>();
    File fileToUpload;
    int imageNum = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitButton = findViewById(R.id.submitButton);
        Spinner spinner = findViewById(R.id.spinnerTeam);
        ArrayList<String> nameArr = new ArrayList<>();

        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what == 5){
                    ((TextView) findViewById(R.id.taskAmount)).setText("" + totalTasks.size());
                }
            }
        };

        for(Team t:allTeams){
            nameArr.add(t.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_fragment,R.id.spinnerTextView,nameArr);
        spinner.setAdapter(adapter);

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task t : response.getData()) {
                        totalTasks.add(t);
                    }
                    handler.sendEmptyMessage(5);
                },
                response -> Log.d(TAG, "retrieving tasks: " + response.toString())
        );

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
//                        Log.i(TAG, "onCreate: successfully added task ");
                    },
                    response ->{
//                        Log.i(TAG, "onCreate: failed to add task");
                    }
            );



            Intent intent = new Intent(AddTask.this, MainActivity.class);
            AddTask.this.startActivity(intent);
            startActivity(intent);

            saveFileToS3(fileToUpload,task.getId());
        });

        Button addFileButton = findViewById(R.id.addFileButton);
        addFileButton.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(intent,GET_IMAGE_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageNum += 1;
        if(requestCode == GET_IMAGE_CODE){
            //retrieve the file and upload it
            fileToUpload = new File(getApplicationContext().getFilesDir(),"image-" + imageNum);
            try{
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inputStream, new FileOutputStream(fileToUpload));
                ImageView imageView = findViewById(R.id.addTaskImage);
                imageView.setImageBitmap(BitmapFactory.decodeFile(fileToUpload.getPath()));

            }catch(IOException e){
                Log.e(TAG, "Failed to upload image successfully: " + e.toString());
            }
        }
    }

    void saveFileToS3(File file, String filename){
        Amplify.Storage.uploadFile(
                filename,
                file,
                r->{},
                r ->{}
        );
    }
}