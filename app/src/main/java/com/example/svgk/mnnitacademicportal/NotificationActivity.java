package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NotificationActivity extends AppCompatActivity {

    Button upload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        upload = findViewById(R.id.upload_pdf);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(
                        Intent.createChooser(intent, "Select txt file"),
                        2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 2 && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                File file = null;
                if (uri != null) {
                    file = new File(uri.toString());
                }
                String stringEncoded = encodeFileToBase64Binary(file);
                Toast.makeText(this, stringEncoded, Toast.LENGTH_SHORT).show();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e) {
            e.printStackTrace();

         }
    }

    private String encodeFileToBase64Binary(File file){
        byte[] data = null;
        try {

            data = FileUtils.readFileToByteArray(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return Base64.encodeToString(data, Base64.DEFAULT);
    }


}
