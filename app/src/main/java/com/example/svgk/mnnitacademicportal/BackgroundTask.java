package com.example.svgk.mnnitacademicportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public interface BackroundResponse{
        void processFinished(boolean result);
    }

    public BackgroundTask(Context context) {
        ctx = context;
    }

    private void storeInformation(String user_name,String user_pass){
        SharedPreferences preferences = ctx.getSharedPreferences(
                "loginInformation",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_name",user_name);
        editor.putString("user_pass",user_pass);
        editor.apply();
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Invalid credentials");
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url = "http://10.0.2.2/mnnit_database/register.php";
        String log_url = "http://10.0.2.2/mnnit_database/login.php";
        String method = params[0];

        if (method.equals("register")) {

            String user_name = params[1];
            String user_pass = params[2];
            String name = params[3];
            String branch = params[4];
            String semester = params[5];
            String mailId = params[6];
            String gender = params[7];
            String contact = params[8];
            String db = params[9];

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
                        URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mailId, "UTF-8") + "&" +
                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                        URLEncoder.encode("db", "UTF-8") + "=" + URLEncoder.encode(db, "UTF-8") ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream Is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "ISO-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                Is.close();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("login")) {

            String user_name = params[1];
            String user_pass = params[2];

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
                InputStream Is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "ISO-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                Is.close();
                storeInformation(user_name,user_pass);
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Successfully registered.") || result.equals("User already exists.")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean login_status = Boolean.parseBoolean(result);
        if (login_status) {
            delegate.processFinished(login_status);
            Toast.makeText(ctx, "Welcome", Toast.LENGTH_SHORT).show();
        } else {
            alertDialog.setMessage("Enter correct details");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
    }
}
