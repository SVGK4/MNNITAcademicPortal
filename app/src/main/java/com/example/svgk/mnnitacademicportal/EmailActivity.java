package com.example.svgk.mnnitacademicportal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends Activity {

    EditText editTextSubject, editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        editTextSubject = findViewById(R.id.subject);
        editTextMessage = findViewById(R.id.multiText);

        Button buttonSend = findViewById(R.id.emailBtn);

        buttonSend.setOnClickListener(view -> sendEmail());
    }

    private void sendEmail() {
        String email = "";
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();
        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();

    }
}
