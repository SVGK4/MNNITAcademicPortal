package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmailActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    public static Context EMAIL_ACTIVITY_CONTEXT ;
    EditText emailSubject, emailMessge;
    EditText regd_no;
    List<MultipleUsers> usersList;
    CardViewAdapter adapter;
    RecyclerView recyclerView;
    SendMail sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        EMAIL_ACTIVITY_CONTEXT = this;
        emailSubject = findViewById(R.id.subject);
        emailMessge = findViewById(R.id.message);
        emailSubject.setVisibility(View.INVISIBLE);
        emailMessge.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recycler_view_email);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        regd_no = findViewById(R.id.regd_number_search);

        Button sendEmail = findViewById(R.id.send_email);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        regd_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 5) {
                    startBackgroundTask();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regd_no.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL || event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (regd_no.getText().toString().length() >= 5) {
                        startBackgroundTask();
                    }
                }
                return true;
            }
        });

    }

    private void startBackgroundTask() {
        String method = "search_student";
        BackgroundTask backgroundTask = new BackgroundTask(EmailActivity.this);
        backgroundTask.delegate = EmailActivity.this;
        backgroundTask.execute(method, regd_no.getText().toString());
    }

    private void sendEmail() {
        String email = "surapanenigopi44@gmail.com";
        String subject = emailSubject.getText().toString().trim();
        String message = emailMessge.getText().toString().trim();
        sm = new SendMail(this, email, subject, message);
        sm.execute();
    }

    @Override
    public void processFinished(String result) {
        usersList = extractFeatureFromJson(result);
        adapter = new CardViewAdapter(this, usersList,null);
        recyclerView.setAdapter(adapter);
    }

    private List<MultipleUsers> extractFeatureFromJson(String userJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding users to
        List<MultipleUsers> users = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(userJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or users).
            JSONArray userArray = baseJsonResponse.getJSONArray("server_response");

            // For each user in the userArray, create an {@link MultipleUsers} object
            for (int i = 0; i < userArray.length(); i++) {

                // Get a single user at position i within the list of users
                JSONObject currentUser = userArray.getJSONObject(i);

                String reg_no = currentUser.getString("regd_no");
                String name = currentUser.getString("name");
                String branch = currentUser.getString("branch");
                String mail_id = currentUser.getString("email");
                // Create a new {@link MultipleUsers} object with the magnitude, location, time,
                // and url from the JSON response.
                MultipleUsers user = new MultipleUsers(reg_no, name, branch, mail_id);

                // Add the new {@link MultipleUsers} to the list of users.
                users.add(user);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Admin Users", "Problem parsing the user JSON results", e);
        }

        // Return the list of users
        return users;
    }

}
