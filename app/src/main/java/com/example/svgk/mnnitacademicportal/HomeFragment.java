package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private LineChart mChart;
    private TextView name,regd_no,email,contact;
    private ImageView photo;
    private Button upload;
    private Bitmap imageBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_home,container,false);

        mChart = rootView.findViewById(R.id.chart);
        name = rootView.findViewById(R.id.student_name);
        regd_no = rootView.findViewById(R.id.student_registration_no);
        email = rootView.findViewById(R.id.student_email);
        contact = rootView.findViewById(R.id.student_phone_number);
        photo = rootView.findViewById(R.id.student_photo);
        photo = rootView.findViewById(R.id.student_photo);
        upload = rootView.findViewById(R.id.button);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent,1);
            }
        });
        photo.setImageResource(R.mipmap.back_arrow);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encodeImage = imageToString(imageBitmap);
                BackgroundTask backgroundTask = new BackgroundTask(getContext());
                String method = "sendImage";
                backgroundTask.execute(method,encodeImage);
            }
        });

        name.setText("Name : " + User.getNAME());
        regd_no.setText("Regd. No : " + User.getRegdNo());
        email.setText("Email : " + User.getEMAIL());
        contact.setText("Contact : " + User.getContactNo());

        setData(8);
        mChart.animateX(1000);

        return rootView;
    }

    private void setData(int count) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = i + 1;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = i + 5;
            yVals2.add(new Entry(i, val));
        }

        LineDataSet set1, set2;
        set1 = new LineDataSet(yVals1, "CPI");
        set2 = new LineDataSet(yVals2, "SPI");

        LineData lineData = new LineData(set1, set2);
        mChart.setData(lineData);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photo.setImageBitmap(imageBitmap);

        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodeImage;
    }


}
