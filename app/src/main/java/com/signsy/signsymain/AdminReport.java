package com.signsy.signsymain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class AdminReport extends AppCompatActivity {

    GraphView graphView1, graphView2, graphView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_report);

        graphView1 = findViewById(R.id.graph);
        graphView2 = findViewById(R.id.graph2);
        graphView3 = findViewById(R.id.graph3);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,3),
                new DataPoint(4,4),
                new DataPoint(8,8),
                new DataPoint(12,10),
                new DataPoint(16,7),
                new DataPoint(20,11)
        });

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(4,2),
                new DataPoint(8,4),
                new DataPoint(12,3),
                new DataPoint(16,5),
                new DataPoint(20,8)
        });

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(4,0),
                new DataPoint(8,2),
                new DataPoint(12,4),
                new DataPoint(16,5),
                new DataPoint(20,6)
        });

        graphView1.setTitle("Active Users");
        graphView1.setTitleTextSize(60);
        graphView1.addSeries(series);

        graphView2.setTitle("Active NGOs");
        graphView2.setTitleTextSize(60);
        graphView2.addSeries(series2);

        graphView3.setTitle("Active Donations");
        graphView3.setTitleTextSize(60);
        graphView3.addSeries(series3);
    }
}