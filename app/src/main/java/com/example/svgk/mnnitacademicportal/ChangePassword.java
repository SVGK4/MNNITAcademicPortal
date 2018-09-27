package com.example.svgk.mnnitacademicportal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;

public class ChangePassword extends AppCompatActivity implements BackgroundTask.BackroundResponse{

    String oldP;
    String setP;
    String confirmP;
    static boolean userAuthenticated;
    ProgressDialog progressDialog;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText oldPass = findViewById(R.id.oldPass);
        EditText setPass = findViewById(R.id.setPass);
        EditText confirmPass = findViewById(R.id.confirmPass);
        Button   changePass = findViewById(R.id.button);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldP = oldPass.getText().toString();
                setP = setPass.getText().toString();
                confirmP = confirmPass.getText().toString();

                String oldHash = getHash(oldP);
                if(setP.equals(oldP) && userAuthenticated) {
                    String newHash = getHash(confirmP);
                    confirmAuthentication(oldHash);
                }
            }
        });

    }

    private String getHash(String user_pass) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_pass;

    }


    protected void confirmAuthentication(String user_pass){
        progressDialog = ProgressDialog.show(ChangePassword.this,"Checking credentials","Please wait...",false,false);
        String method ;
        if(LoginActivity.isAdmin) {
            user = LoginActivity.getAdmin();
            method  = "admin_login";
            BackgroundTask backgroundTask = new BackgroundTask(ChangePassword.this);
            backgroundTask.delegate = this;
            backgroundTask.execute(method, user , user_pass);
        }else {
            user = User.getRegdNo();
            method = "login";
            BackgroundTask backgroundTask = new BackgroundTask(ChangePassword.this);
            backgroundTask.execute(method, user, user_pass);
        }
    }

    protected static void setResult(boolean result) {
        if(result) {
            userAuthenticated = true;
        }else {
            userAuthenticated = false;
        }
    }

    @Override
    public void processFinished(String result) {
        if(userAuthenticated) {
                BackgroundTask backgroundTask = new BackgroundTask(ChangePassword.this);
                backgroundTask.delegate = this;
                backgroundTask.execute("change_password", user, setP, Boolean.valueOf(LoginActivity.isAdmin).toString());
        }
    }
}
