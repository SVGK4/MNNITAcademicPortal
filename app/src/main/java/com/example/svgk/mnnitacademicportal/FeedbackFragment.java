package com.example.svgk.mnnitacademicportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FeedbackFragment extends Fragment {

    private EditText feedback;
    private Button submitFeedback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        Spinner spinner = rootView.findViewById(R.id.fdList);
        ArrayAdapter<CharSequence> adapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.feedback_array, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        feedback = rootView.findViewById(R.id.feedback);
        submitFeedback = rootView.findViewById(R.id.submit_feedback);
        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feed = feedback.getText().toString();
                String course = spinner.getSelectedItem().toString();
                BackgroundTask backgroundTask = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    backgroundTask = new BackgroundTask(getContext());
                }
                String method = "feedback";
                backgroundTask.execute(method, User.getNAME(), User.getRegdNo(), course, feed);
            }
        });

        return rootView;
    }
}