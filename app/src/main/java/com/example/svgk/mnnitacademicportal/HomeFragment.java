package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private LineChart mChart;
    private TextView name, regd_no, email, contact, viewProfile;
    private ImageView photo;
    static Bitmap imageBitmap = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mChart = rootView.findViewById(R.id.chart);
        name = rootView.findViewById(R.id.student_name);
        regd_no = rootView.findViewById(R.id.student_registration_no);
        email = rootView.findViewById(R.id.student_email);
        contact = rootView.findViewById(R.id.student_phone_number);
        photo = rootView.findViewById(R.id.student_photo);

        photo.setImageBitmap(imageBitmap);

        viewProfile = rootView.findViewById(R.id.view_full_profile);

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UserProfile.class);
                startActivity(intent);
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

}
