package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CardViewAdapter adapter;
    protected static Context DOWNLOAD_ACTIVITY_CONTEXT;
    List<DownloadFiles> files;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        recyclerView = findViewById(R.id.download_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DOWNLOAD_ACTIVITY_CONTEXT = getApplicationContext();
        files = new ArrayList<>();

        files.add(new DownloadFiles("First Year Curriculum","https://server-manasabhilash.c9users.io/downloads/1.pdf"));
        files.add(new DownloadFiles("Academic Calendar","https://server-manasabhilash.c9users.io/downloads/2.pdf"));
        files.add(new DownloadFiles("Affidavit For Parents","https://server-manasabhilash.c9users.io/downloads/3.pdf"));
        files.add(new DownloadFiles("Affidavit For Students","https://server-manasabhilash.c9users.io/downloads/4.pdf"));
        files.add(new DownloadFiles("Anti Ragging - 1","https://server-manasabhilash.c9users.io/downloads/5.pdf"));
        files.add(new DownloadFiles("Anti Ragging - 2","https://server-manasabhilash.c9users.io/downloads/6.pdf"));
        files.add(new DownloadFiles("Charges and rules","https://server-manasabhilash.c9users.io/downloads/7.pdf"));
        files.add(new DownloadFiles("Day Scholars","https://server-manasabhilash.c9users.io/downloads/8.pdf"));
        files.add(new DownloadFiles("Hostel Allotment Form","https://server-manasabhilash.c9users.io/downloads/9.pdf"));
        files.add(new DownloadFiles("Phd Performa","https://server-manasabhilash.c9users.io/downloads/10.pdf"));

        adapter = new CardViewAdapter(getApplicationContext(),null,files);
        recyclerView.setAdapter(adapter);

        recyclerView = findViewById(R.id.download_view);
    }

}
