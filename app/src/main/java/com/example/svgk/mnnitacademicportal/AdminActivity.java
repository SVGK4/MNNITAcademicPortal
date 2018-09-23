package com.example.svgk.mnnitacademicportal;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersList.add(new AdminUser("20174063","surapaneni venkata Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","surapaneni venkata Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","surapaneni venkata Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        usersList.add(new AdminUser("20174063","Gpi Krishna","COmputer Engi","SurapaneniGopi 44"));
        adapter = new AdminListAdapter(this,usersList);
        recyclerView.setAdapter(adapter);
    }
}
