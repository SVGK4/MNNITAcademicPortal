package com.example.svgk.mnnitacademicportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class RegisterActivity extends Activity {

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creation of objects of the particular component used
        EditText nameText = findViewById(R.id.name);
        EditText regNo = findViewById(R.id.regNo);
        EditText setPass = findViewById(R.id.setPass);
        EditText confirmPass = findViewById(R.id.confirmPass);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        TextView dob = findViewById(R.id.getDob);
        Button btn = findViewById(R.id.button);
        ImageView dateImg = findViewById(R.id.date);

        //For setting data into spinners
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        //For generating the datePicker when the textview is clicked
        dateImg.setOnClickListener((v) -> {
            calendar = Calendar.getInstance();
            int currDay, currMonth, currYear;
            currDay = calendar.get(Calendar.DAY_OF_MONTH);
            currMonth = calendar.get(Calendar.MONTH);
            currYear = calendar.get(Calendar.YEAR);
            datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    dob.setText(day + ":" + month + ":" + year);
                }
            }, currYear, currMonth, currDay);

            datePickerDialog.show();
        });


        //To check wether the name is filled
        regNo.setOnClickListener((v)->{
            if(nameText.getText().toString().equalsIgnoreCase("")) {
                nameText.setError("Fill Name First");
            }
        });



        btn.setOnClickListener((View v) -> {
                try {
                    String name = nameText.getText().toString();
                    int regno = Integer.parseInt(regNo.getText().toString());
                    String setPas = setPass.getText().toString();
                    String confirmPas = confirmPass.getText().toString();
                    String mail = email.getText().toString();
                    String call = phone.getText().toString();
                    String branch = spinner.getSelectedItem().toString();
                    String semester = spinner2.getSelectedItem().toString();
                    String Gender = spinner3.getSelectedItem().toString();
                    String db = dob.getText().toString();
                }catch (NullPointerException e) {
                        System.out.print("ef");

                }
        });

    }
}
