package com.urealaden.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        findViewById(R.id.saveUsernameButton).setOnClickListener(v ->{
            String username = ((TextView) findViewById(R.id.usernameView)).getText().toString();

            preferencesEditor.putString("username",username);
            preferencesEditor.apply();

            Intent goToMainPageIntent = new Intent(Settings.this,MainActivity.class);
            Settings.this.startActivity(goToMainPageIntent);
            startActivity(goToMainPageIntent);
        });
    }
}