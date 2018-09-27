package com.example.svgk.mnnitacademicportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
    private String user_name, user_pass;
    private ProgressDialog progressDialog;
    private Switch adminToggle;
    private TextInputLayout userHint;
    private static String admin;
    protected static boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotPassword = findViewById(R.id.forgot);
        loginButton = findViewById(R.id.sign_in);
        username = findViewById(R.id.user_name);
        userpass = findViewById(R.id.user_pass);
        registerBtn = findViewById(R.id.registerBtn);
        adminToggle = findViewById(R.id.switch2);
        userHint = findViewById(R.id.userTextInputLayout);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        user_name = preferences.getString("user_name", "");
        user_pass = preferences.getString("user_pass", "");

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!user_name.equals("") && !user_pass.equals("")) {
            if (isConnected) {
                confirmLogin();
            }
        }

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
                if(isConnected) {
                    user_name = username.getText().toString();
                    setAdmin(user_name);
                    user_pass = userpass.getText().toString();
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
                    confirmLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Please Check your Connection", Toast.LENGTH_SHORT).show();
                }
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

        adminToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    isAdmin  = true;
                    username.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    userHint.setHint("Admin Username");
                }else {
                    isAdmin = false;
                    username.setInputType(InputType.TYPE_CLASS_NUMBER);
                    userHint.setHint("Registration Number");
                }
            }
        });

    }

    protected void confirmLogin(){
        progressDialog = ProgressDialog.show(LoginActivity.this,"Checking credentials","Please wait...",false,false);
        String method ;
        if(isAdmin) {
            method  = "admin_login";
        }else {
            method = "login";
        }
        BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
        backgroundTask.delegate = LoginActivity.this;
        backgroundTask.execute(method, user_name, user_pass);
    }

    @Override
    public void processFinished(String userJSON) {
        if (isAdmin && userJSON.equalsIgnoreCase("true ")) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            try {
                JSONObject baseJsonResponse = new JSONObject(userJSON);

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
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
            Context context = getApplicationContext();
            SharedPreferences preferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user_name",user_name);
            editor.putString("user_pass",user_pass);
            editor.apply();
            progressDialog.dismiss();
        }
    }

    protected static String getAdmin() {
        return admin;
    }

    private static void setAdmin(String user_name) {
        admin = user_name;
    }

}
