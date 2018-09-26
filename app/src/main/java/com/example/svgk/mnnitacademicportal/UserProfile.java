package com.example.svgk.mnnitacademicportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfile extends AppCompatActivity {
    ImageView editImage, profileImage;
    Bitmap profileImageBitmap;
    TextView name,email,branch,contact,address,dob,regd_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.student_name_profile);
        email = findViewById(R.id.student_email_profile);
        branch = findViewById(R.id.student_branch_profile);
        contact = findViewById(R.id.student_contact_profile);
        address = findViewById(R.id.student_address_profile);
        dob = findViewById(R.id.student_dob_profile);
        regd_no = findViewById(R.id.student_regd_no_profile);

        name.setText(": " + User.getNAME());
        email.setText(User.getEMAIL());
        branch.setText(": " + User.getBRANCH());
        contact.setText(": " + User.getContactNo());
        address.setText(User.getADDRESS());
        dob.setText(": " + User.getDateOfBirth());
        regd_no.setText(": " + User.getRegdNo());

        Button upload = findViewById(R.id.upload_profile);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundTask backgroundTask = new BackgroundTask(UserProfile.this);
                String method = "update_user";
                if(isValidEmail(email.getText().toString())) {
                    backgroundTask.execute(method, User.getRegdNo(), email.getText().toString(), address.getText().toString());
                }else{
                    email.setError("Enter valid Email");
                }
            }
        });

        editImage = findViewById(R.id.change_image);
        profileImage = findViewById(R.id.student_profile_image);
        profileImage.setImageBitmap(HomeFragment.imageBitmap);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                profileImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileImage.setImageBitmap(profileImageBitmap);

            String encodeImage = imageToString(profileImageBitmap);
            BackgroundTask backgroundTask = new BackgroundTask(this);
            String method = "uploadImage";
            backgroundTask.execute(method, encodeImage, User.getRegdNo());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodeImage;
    }

}
