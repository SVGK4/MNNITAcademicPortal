package com.example.svgk.mnnitacademicportal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class AdminActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    RecyclerView recyclerView;
    AdminListAdapter adapter;
    List<AdminUser> usersList;
    Button approveBtn;

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
        background.execute(method);

        approveBtn = findViewById(R.id.approve);
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method1 = "set_approve";
                BackgroundTask backgroundTask = new BackgroundTask(AdminActivity.this);
                System.out.print(usersList.get(0).REGD_NO);
                backgroundTask.execute("set_approve", usersList.get(0).REGD_NO);
            }
        });

        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Profile",
                        0,
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
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Approve",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnUnshare
                            }
                        }
                ));
            }
        };


    }


    @Override
    public void processFinished(String result) {
        usersList = QueryUtils.fetchUserData(result);
        adapter = new AdminListAdapter(this, usersList);
        recyclerView.setAdapter(adapter);
    }
}
