package com.urealaden.taskmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.urealaden.taskmaster.R;


public class Login extends AppCompatActivity {

    String TAG = "urealaden.Login";
    Handler handler;

    void loginCognito(){
        findViewById(R.id.loginPageButton).setOnClickListener(v->{
            String username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString();
            String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
            Amplify.Auth.signIn(
                    username,
                    password,
                    r ->{
                        handler.sendEmptyMessage(1);
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    },
                    r -> handler.sendEmptyMessage(2)
            );
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String message = msg.what == 1 ? "Sign In Successful" : (msg.what == 2 ? "Sign in Failed" : "Something went wrong");
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        };

        loginCognito();
    }
}