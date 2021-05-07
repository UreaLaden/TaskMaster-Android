package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Team;
import com.urealaden.taskmaster.R;

import java.util.ArrayList;

import static com.urealaden.taskmaster.activities.MainActivity.allTeams;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        String team = preferences.getString("team",null);
        int count = 0;
        ArrayList<String> nameArr = new ArrayList<>();
        for(Team t:allTeams){
            nameArr.add(t.getName());
        }
        Spinner teamSpinner = findViewById(R.id.settingsPageSpinnerTeam);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_fragment,R.id.spinnerTextView,nameArr);
        teamSpinner.setAdapter(adapter);

        if(team != null){

            for(String names: nameArr){
                if(team.contains(names)){
                    break;
                }
                count++;
            }
            teamSpinner.setSelection(count);
        }

        findViewById(R.id.saveUsernameButton).setOnClickListener(v ->{
            String username = ((TextView) findViewById(R.id.usernameView)).getText().toString();
            String chosenTeam = ((TextView) findViewById(R.id.spinnerTextView)).getText().toString();

            preferencesEditor.putString("team",chosenTeam);
            preferencesEditor.putString("username",username);
            preferencesEditor.apply();

            Intent goToMainPageIntent = new Intent(Settings.this, MainActivity.class);
            Settings.this.startActivity(goToMainPageIntent);
            startActivity(goToMainPageIntent);
        });

    }
}