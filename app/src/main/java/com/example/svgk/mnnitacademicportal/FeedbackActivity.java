package com.example.svgk.mnnitacademicportal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedback;
    private Button submitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Spinner spinner = findViewById(R.id.fdList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.feedback_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        feedback = findViewById(R.id.feedback);
        submitFeedback = findViewById(R.id.submit_feedback);
        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feed = feedback.getText().toString();
                String course = spinner.getSelectedItem().toString();
                BackgroundTask backgroundTask = new BackgroundTask(FeedbackActivity.this);
                String method = "feedback";
                backgroundTask.execute(method,User.getNAME(),User.getRegdNo(),course,feed);
                finish();
            }
        });
    }
}
