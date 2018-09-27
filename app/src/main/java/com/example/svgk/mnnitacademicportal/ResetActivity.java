package com.example.svgk.mnnitacademicportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetActivity extends AppCompatActivity {

    private String password,confirmPassword,enteredCode;
    private EditText pass,confirmPass,code;
    Button reset;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirm_password);
        reset = findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = pass.getText().toString();
                enteredCode = code.getText().toString();
                confirmPassword = confirmPass.getText().toString();
                if(password.equals(confirmPassword) && !enteredCode.equals("")){
                    checkCode();
                }
            }
        });

    }

    private void checkCode(){
        String method = "";
    }
}
