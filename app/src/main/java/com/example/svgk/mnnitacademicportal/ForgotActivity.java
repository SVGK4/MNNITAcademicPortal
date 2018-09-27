package com.example.svgk.mnnitacademicportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class ForgotActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse{

    SendMail sendMail;
    EditText regd_no;
    String email;
    String registrationNumber;
    TextView resetWithCode;
    ProgressDialog progressDialog;
    String resetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Button reset = findViewById(R.id.reset);
        regd_no = findViewById(R.id.regd_no_forgot);
        resetWithCode = findViewById(R.id.reset_with_code);

        resetWithCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotActivity.this,ResetActivity.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executes after reset button is clicked
                registrationNumber = regd_no.getText().toString();
                if(registrationNumber.length() != 8){
                    regd_no.setError("Registration number is of eight digits");
                }else{
                    startBackgroundTask();
                    ProgressDialog.show(ForgotActivity.this,"","",false,false);

                }
            }
        });
    }

    private void startBackgroundTask() {
        String method = "reset_password";
        BackgroundTask backgroundTask = new BackgroundTask(ForgotActivity.this);
        backgroundTask.execute(method, registrationNumber);
    }

    private void sendEmail() {
        String subject = "Reset link for your account";
        Random r = new Random();
        int code = r.nextInt();
        if(code <= 0){
            code *= -1;
        }
        resetCode = String.valueOf(code);
        String message = "" + String.valueOf(r.nextInt()) + " is your password reset code";
        sendMail = new SendMail(this, email, subject, message);
        sendMail.execute();
    }

    @Override
    public void processFinished(String result) {

    }
}
