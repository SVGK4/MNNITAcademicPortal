package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

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
        if(requestCode == 2 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            File file = null;
            //String path = getPath
            if (uri != null) {
                file = new File(uri.toString());

            }
            String stringEncoded = encodeFileToBase64Binary(file);
            Toast.makeText(this, stringEncoded, Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String encodeFileToBase64Binary(File file){
        String encodedfile = null;


        return encodedfile;
    }


}
