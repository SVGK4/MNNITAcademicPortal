package com.example.svgk.mnnitacademicportal;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_home,container,false);

        GraphView graph = rootView.findViewById(R.id.home_graph);
        LineGraphSeries<DataPoint> series0 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, 8.04)
        });
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(2, 8.15)
        });
        graph.addSeries(series0);
        graph.addSeries(series1);
        return rootView;
    }
}
