package com.example.svgk.mnnitacademicportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private LineChart mChart;
    private TextView name, regd_no, email, contact, viewProfile;
    private ImageView photo;
    static Bitmap imageBitmap = null;
    public static final String PDF_FETCH_URL = "https://server-manasabhilash.c9users.io/fetch_pdf.php";
    PdfAdapter pdfAdapter;
    ListView listView;
    ArrayList<Pdf> pdfList = new ArrayList<Pdf>();
    private static int checkLoaded = 0;

    @SuppressLint("SetTextI18n")
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
        listView = rootView.findViewById(R.id.listView);

        photo.setImageBitmap(imageBitmap);

        viewProfile = rootView.findViewById(R.id.view_full_profile);

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserProfile.class);
                startActivity(intent);
            }
        });

        getPdfs();

        name.setText("Name : " + User.getNAME());
        regd_no.setText("Regd. No : " + User.getRegdNo());
        email.setText("Email : " + User.getEMAIL());
        contact.setText("Contact : " + User.getContactNo());

        setData(8);
        mChart.animateX(1000);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Pdf pdf = (Pdf) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);

            }
        });


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

    private void getPdfs() {

        checkLoaded = 1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdfList.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("pdfs");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf = new Pdf();
                                String pdfName = jsonObject.getString("name");
                                String pdfUrl = jsonObject.getString("url");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdfList.add(pdf);

                            }

                            setPdf();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


        );

        RequestQueue request = Volley.newRequestQueue(getContext());
        request.add(stringRequest);

    }

    private void setPdf() {
        pdfAdapter = new PdfAdapter(getActivity(), R.layout.list_layout, pdfList);

        listView.setAdapter(pdfAdapter);

        pdfAdapter.notifyDataSetChanged();
    }


}
