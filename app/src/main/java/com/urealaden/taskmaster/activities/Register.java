package com.urealaden.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.urealaden.taskmaster.R;

public class Register extends AppCompatActivity {

    void signupConfirmation(){
        (findViewById(R.id.registerButton)).setOnClickListener(v->{
            String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
            Amplify.Auth.signUp(
                    username,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(),username)
                            .build(),
                    r ->{
                        Intent intent = new Intent(Register.this,LoginConfirmation.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    },
                    r ->{
                        Log.i("CognitoSignup: ", "signupConfirmation: Failed");
                    }
            );
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupConfirmation();
    }
}