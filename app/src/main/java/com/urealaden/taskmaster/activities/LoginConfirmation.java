package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.urealaden.taskmaster.R;

public class LoginConfirmation extends AppCompatActivity {

    public static String TAG = "LoginConfirmation";
    void confirmLoggedIn(String username){
        Amplify.Auth.confirmSignUp(
                username,
                ((EditText) findViewById(R.id.editTextConfirmationCode))
                .getText().toString(),
                r-> {
                    startActivity(new Intent(LoginConfirmation.this,MainActivity.class));
                },
                r->{
                    Toast.makeText(getApplicationContext(),"Confirmation Code Invalid", Toast.LENGTH_LONG).show();
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirmation);
        String username = getIntent().getStringExtra("username");
        findViewById(R.id.validateAccountButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLoggedIn(username);
            }
        });
    }
}