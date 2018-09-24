package com.example.svgk.mnnitacademicportal;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class AdminActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    RecyclerView recyclerView;
    AdminListAdapter adapter;
    List<AdminUser> usersList;
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
        progressDialog = ProgressDialog.show(AdminActivity.this,"Getting Details    ","Please wait...",false,false);

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
                                // TODO: OnUnshare
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
        usersList = QueryUtils.fetchUserData(result);
        adapter = new AdminListAdapter(this, usersList);
        recyclerView.setAdapter(adapter);
    }
}
