package com.example.svgk.mnnitacademicportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, Void, String> {

    private AlertDialog alertDialog;
    private Context ctx;
    public BackroundResponse delegate = null;
    private String method;

    public interface BackroundResponse {
        void processFinished(String result);
    }

    public BackgroundTask(Context context) {
        ctx = context;
    }

    private void storeInformation(String user_name, String user_pass) {
        SharedPreferences preferences = ctx.getSharedPreferences(
                "loginInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name", user_name);
        editor.putString("user_pass", user_pass);
        editor.apply();
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Invalid credentials");
    }

    @Override
    protected String doInBackground(String... params) {

//        String reg_url = "http://10.0.2.2/mnnit_database/register.php";
//        String log_url = "http://10.0.2.2/mnnit_database/user.php";
//        String admin_url = "http://10.0.2.2/mnnit_database/admin_user.php";
//        String approve_url = "http://10.0.2.2/mnnit_database/set_approve.php";
//        String feedback_url = "http://10.0.2.2/mnnit_database/feedback.php";
//        String image_url = "http://10.0.2.2/mnnit_database/image_connection.php";


        String reg_url = "https://server-manasabhilash.c9users.io/register.php";
        String log_url = "https://server-manasabhilash.c9users.io/user.php";
        String admin_url = "https://server-manasabhilash.c9users.io/admin_user.php";
        String approve_url = "https://server-manasabhilash.c9users.io/set_approve.php";
        String feedback_url = "https://server-manasabhilash.c9users.io/feedback.php";
        String image_url = "https://server-manasabhilash.c9users.io/image_connection.php";


        method = params[0];

        if (method.equals("register")) {

            String user_name = params[1];
            String user_pass = params[2];
            String name = params[3];
            String branch = params[4];
            String mailId = params[5];
            String gender = params[6];
            String contact = params[7];
            String db = params[8];
            String status = "Requested";

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("branch", "UTF-8") + "=" + URLEncoder.encode(branch, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mailId, "UTF-8") + "&" +
                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                        URLEncoder.encode("db", "UTF-8") + "=" + URLEncoder.encode(db, "UTF-8") + "&" +
                        URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is = httpURLConnection.getInputStream();
                return getResponse(Is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("login")) {

            String user_name = params[1];
            String user_pass = params[2];

            String JSON_String = null;

            try {
                URL url = new URL(log_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                while ((JSON_String = bufferedReader.readLine()) != null) {
                    builder.append(JSON_String + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return builder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("admin_user")) {
            String JSON_String = null;
            try {
                URL url = new URL(admin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                while ((JSON_String = bufferedReader.readLine()) != null) {
                    builder.append(JSON_String + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return builder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("set_approve")) {
            String user_name = params[1];
            try {
                URL url = new URL(approve_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is = httpURLConnection.getInputStream();
                return getResponse(Is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("feedback")) {
            String name = params[1];
            String regd_no = params[2];
            String course = params[3];
            String feedback = params[4];

            try {
                URL url = new URL(feedback_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("regd_no", "UTF-8") + "=" + URLEncoder.encode(regd_no, "UTF-8") + "&" +
                        URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(course, "UTF-8") + "&" +
                        URLEncoder.encode("feedback", "UTF-8") + "=" + URLEncoder.encode(feedback, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is = httpURLConnection.getInputStream();
                return getResponse(Is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("uploadImage")){
            String encoded_image = params[1];
            String regd_no = params[2];
            try {
                URL url = new URL(image_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("regd_no", "UTF-8") + "=" + URLEncoder.encode(regd_no, "UTF-8") + "&" +
                        URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encoded_image, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is = httpURLConnection.getInputStream();
                return getResponse(Is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String getResponse(InputStream Is) {
        String response = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "ISO-8859-1"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            Is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    protected void onPostExecute(String result) {

        if (method.equals("register")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        } else if (method.equals("login")) {
            delegate.processFinished(result);
        } else if (method.equals("admin_user")) {
            delegate.processFinished(result);
        } else if (method.equals("set_approve")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        } else if (method.equals("feedback")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }
}
