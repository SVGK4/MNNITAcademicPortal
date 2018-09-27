package com.example.svgk.mnnitacademicportal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;

public class ChangePassword extends AppCompatActivity {

    String oldP;
    String setP;
    String confirmP;
    static boolean userAuthenticated;
    static String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText oldPass = findViewById(R.id.getOld);
        EditText setPass = findViewById(R.id.setPass);
        EditText confirmPass = findViewById(R.id.confirmPass);
        Button changePass = findViewById(R.id.change_password);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
                String pawdHash = preferences.getString("user_pass","");
                oldP = oldPass.getText().toString();
                setP = setPass.getText().toString();
                confirmP = confirmPass.getText().toString();
                String enteredHash = getHash(oldP);

                if(pawdHash.equals(enteredHash) && setP.equals(confirmP)){
                    String newPawdHash = getHash(confirmP);
                    String method = "change_password";
                    BackgroundTask backgroundTask = new BackgroundTask(ChangePassword.this);
                    backgroundTask.execute(method,User.getRegdNo(),newPawdHash);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
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
            Log.i("hash", "hash is" + user_pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_pass;

    }

}
