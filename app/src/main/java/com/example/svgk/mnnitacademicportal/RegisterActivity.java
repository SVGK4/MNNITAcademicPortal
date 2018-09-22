package com.example.svgk.mnnitacademicportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends Activity {

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    EditText nameText, regNo, setPass, confirmPass, email, phone;
    TextView dob;
    Button register;
    ImageView dateImg;
    TextInputLayout til;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creation of objects of the particular component used
        nameText = findViewById(R.id.name);
        regNo = findViewById(R.id.regNo);
        setPass = findViewById(R.id.setPass);
        confirmPass = findViewById(R.id.confirmPass);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.getDob);
        register = findViewById(R.id.button);
        dateImg = findViewById(R.id.date);
        til = findViewById(R.id.confirmPassword);

        //For setting data into spinners
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.gender, android.R.layout.simple_spinner_item);
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
                    dob.setText(day + "/" + month + "/" + year);
                }
            }, currYear, currMonth, currDay);

            datePickerDialog.show();
        });


        //when register button is clicked
        register.setOnClickListener((View v) -> {

            String name = nameText.getText().toString();
            try {
                String regno = regNo.getText().toString();
                if (regno.length() != 8) {
                    regNo.setError("We know registration number is of 8 digits");
                }
            } catch (Exception e) {

            }
            try {
                String setPas = setPass.getText().toString();
                String confirmPas = confirmPass.getText().toString();
                if (!setPas.equals(confirmPas)) {
                    til.setError("Passwords Do not match");
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
            try {
                String mailId = email.getText().toString();
                if (!isValidEmail(mailId)) {
                    email.setError("Enter valid Email");
                }
            } catch (Exception e) {

            }
            try {
                String contactNo = phone.getText().toString();
                String branch = spinner.getSelectedItem().toString();
                String semester = spinner2.getSelectedItem().toString();
                String Gender = spinner3.getSelectedItem().toString();
                String db = dob.getText().toString();
            } catch (Exception e) {

            }
            finish();
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
