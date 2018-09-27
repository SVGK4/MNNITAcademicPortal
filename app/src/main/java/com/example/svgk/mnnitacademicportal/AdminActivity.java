package com.example.svgk.mnnitacademicportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    RecyclerView recyclerView;
    CardViewAdapter adapter;
    List<MultipleUsers> usersList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //gets users data from the background
        BackgroundTask background = new BackgroundTask(this);
        background.delegate = AdminActivity.this;
        final String method = "admin_user";
        progressDialog = ProgressDialog.show(AdminActivity.this, "Getting Details    ", "Please wait...", false, false);

        background.execute(method);

        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Profile",
                        R.mipmap.profile,
                        Color.parseColor("#ffffff"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Deny",
                        R.mipmap.deny,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                                String method = "deny_approval";
                                BackgroundTask backgroundTask = new BackgroundTask(AdminActivity.this);
                                backgroundTask.execute(method, usersList.get(pos).REGD_NO);
                                usersList.remove(pos);
                                adapter.notifyItemRemoved(pos);
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Approve",
                        R.mipmap.approve,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                String method1 = "set_approve";
                                BackgroundTask backgroundTask = new BackgroundTask(AdminActivity.this);
                                backgroundTask.execute("set_approve", usersList.get(pos).REGD_NO);
                                usersList.remove(pos);
                                adapter.notifyItemRemoved(pos);
                            }
                        }
                ));
            }
        };
    }

    @Override
    public void processFinished(String result) {
        progressDialog.dismiss();
        usersList = extractFeatureFromJson(result);
        adapter = new CardViewAdapter(this, usersList);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent intent = new Intent(AdminActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.newPass:
                Intent intent1 = new Intent(AdminActivity.this, ChangePassword.class);
                startActivity(intent1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_admin, menu);
        return true;
    }

}
