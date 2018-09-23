package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    private TextView forgotPassword, registerBtn;
    private Button loginButton;
    private EditText username, userpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotPassword = findViewById(R.id.forgot);
        loginButton = findViewById(R.id.sign_in);
        username = findViewById(R.id.user_name);
        userpass = findViewById(R.id.user_pass);
        registerBtn = findViewById(R.id.registerBtn);


        //When Forgot Password is clicked
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(forgotIntent);
            }
        });

        //When sign in is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString();
                String user_pass = userpass.getText().toString();
                try {
                    MessageDigest md = null;
                    md = MessageDigest.getInstance("SHA256");
                    md.reset();
                    md.update(user_pass.getBytes("UTF-8"));
                    byte hashCode[] = md.digest();
                    StringBuilder generatedOutput = new StringBuilder();
                    for (int index = 0; index < hashCode.length; index++) {
                        String hex = Integer.toHexString(0xFF & hashCode[index]);
                        if (hex.length() == 1) {
                            generatedOutput.append('0');
                        }
                        generatedOutput.append(hex);

                    }
                    user_pass = generatedOutput.toString();
                    Log.i("hash","hash is" + user_pass);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                String method = "login";
                BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                backgroundTask.delegate = LoginActivity.this;
                backgroundTask.execute(method, user_name, user_pass);
            }
        });

        //when register is clicked
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void processFinished(boolean result) {
        if (result) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
