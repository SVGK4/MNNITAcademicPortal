package com.example.svgk.mnnitacademicportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String method = "get_course";
        BackgroundTask backgroundTask = new BackgroundTask(ResultActivity.this);
        backgroundTask.delegate = ResultActivity.this;
        backgroundTask.execute(method, User.getRegdNo());
    }

    @Override
    public void processFinished(String result) {

    }

}
