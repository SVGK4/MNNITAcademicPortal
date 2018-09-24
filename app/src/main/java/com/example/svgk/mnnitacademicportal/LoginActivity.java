package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    Log.i("hash", "hash is" + user_pass);
                } catch (Exception e) {
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
    public void processFinished(String userJSON) {
        try {

            JSONObject baseJsonResponse = new JSONObject(userJSON);

            // Extract the JSONArray associated with the key called "server_response",
            // which represents a list of features (or users).
            JSONArray userArray = baseJsonResponse.getJSONArray("server_response");

            JSONObject currentUser = userArray.getJSONObject(0);

            String reg_no = currentUser.getString("regd_no");
            String name = currentUser.getString("name");
            String branch = currentUser.getString("branch");
            String mail_id = currentUser.getString("email");
            String db = currentUser.getString("dob");
            String contact = currentUser.getString("contact");
            String gender = currentUser.getString("gender");
            String address = currentUser.getString("address");
            String user_pass = currentUser.getString("user_pass");
            String semester = currentUser.getString("semester");
            String status = currentUser.getString("status");
            if (status.equals("Approved")) {
                User user = new User(name, reg_no, user_pass, mail_id, contact, db, branch, semester, gender, address);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the user JSON results", e);
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

}
