package com.example.svgk.mnnitacademicportal;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving user data from database.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils() { }

    /**
     * Return a list of {@link AdminUser} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<AdminUser> extractFeatureFromJson(String userJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding users to
        List<AdminUser> users = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(userJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or users).
            JSONArray userArray = baseJsonResponse.getJSONArray("server_response");

            // For each user in the userArray, create an {@link AdminUser} object
            for (int i = 0; i < userArray.length(); i++) {

                // Get a single user at position i within the list of users
                JSONObject currentUser = userArray.getJSONObject(i);

                String reg_no = currentUser.getString("regd_no");
                String name = currentUser.getString("name");
                String branch = currentUser.getString("branch");
                String mail_id = currentUser.getString("email");
                // Create a new {@link AdminUser} object with the magnitude, location, time,
                // and url from the JSON response.
                AdminUser user = new AdminUser(reg_no,name,branch,mail_id);

                // Add the new {@link AdminUser} to the list of users.
                users.add(user);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the user JSON results", e);
        }

        // Return the list of users
        return users;
    }


    public static List<AdminUser> fetchUserData(String jsonResponse) {

        // Extract relevant fields from the JSON response and create a list of {@link AdminUser}s
        List<AdminUser> users = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link AdminUser}s
        return users;
    }



}