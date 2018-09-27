package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BackgroundTask.BackroundResponse {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView name, regd_no, branch;
    private ImageView photo;
    private String acad_cal_url = "https://server-manasabhilash.c9users.io/misc/academic_calendar.pdf";
    private String fee_structure_url = "https://server-manasabhilash.c9users.io/misc/fee_structure.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        View header = navigationView.getHeaderView(0);

        name = header.findViewById(R.id.student_name_navigation);
        regd_no = header.findViewById(R.id.student_regd_no_navigation);
        branch = header.findViewById(R.id.student_branch_navigation);
        photo = header.findViewById(R.id.student_photo_navigation);

        name.setText(User.getNAME());
        regd_no.setText(User.getRegdNo());
        branch.setText(User.getBRANCH());

        photo.setImageBitmap(HomeFragment.imageBitmap);

        if (HomeFragment.imageBitmap == null) {
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.delegate = this;
            String method = "recieve_image";
            backgroundTask.execute(method, User.getRegdNo());
        }
        //Drawer Layout
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;
                    case R.id.fd:
                        Intent forgotIntent = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(forgotIntent);
                        break;
                    case R.id.logOut:
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user_name", null);
                        editor.putString("user_pass", null);
                        editor.apply();
                        HomeFragment.imageBitmap = null;
                        finish();
                        break;
                    case R.id.acad:
                        getPdf(acad_cal_url);
                        break;
                    case R.id.fee:
                        getPdf(fee_structure_url);
                        break;
                    case R.id.result:
                        Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
                        startActivity(resultIntent);
                        break;
                    case R.id.dwnlds:
                        Intent intent = new Intent(MainActivity.this,DownloadActivity.class);
                        startActivity(intent);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.email:
                Intent intent = new Intent(MainActivity.this, EmailActivity.class);
                startActivity(intent);
                break;
            case R.id.newPass:
                Intent intent1 = new Intent(MainActivity.this, ChangePassword.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPdf(String pdfUrl){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(pdfUrl));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void processFinished(String result) {
        try {
            byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
            HomeFragment.imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            photo.setImageBitmap(HomeFragment.imageBitmap);
            ImageView image = findViewById(R.id.student_photo);
            image.setImageBitmap(HomeFragment.imageBitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}