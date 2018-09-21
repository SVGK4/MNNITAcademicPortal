package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPassword;
    private Button loginButton;
    private EditText username,userpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotPassword = findViewById(R.id.forgot);
        loginButton = findViewById(R.id.sign_in);
        username = findViewById(R.id.user_name);
        userpass = findViewById(R.id.user_pass);

        //When Forgot Password is clicked
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(forgotIntent);
            }
        });

        //When sign in is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString();
                String user_pass = userpass.getText().toString();
                String method = "login";
                BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                backgroundTask.execute(method,user_name,user_pass);
            }
        });
    }
}
