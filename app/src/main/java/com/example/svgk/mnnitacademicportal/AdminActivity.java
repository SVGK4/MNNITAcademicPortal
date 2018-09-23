package com.example.svgk.mnnitacademicportal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminListAdapter adapter;
    List<AdminUser> usersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usersList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String url = "http://10.0.2.2/mnnit_database/admin_user.php";

        //gets users data from the background
        getUsersBackground background = new getUsersBackground();
        background.execute(url);

        adapter = new AdminListAdapter(this,usersList);
        recyclerView.setAdapter(adapter);
    }

    class getUsersBackground extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            usersList = QueryUtils.fetchUserData(url);
            return null;
        }
    }

}
