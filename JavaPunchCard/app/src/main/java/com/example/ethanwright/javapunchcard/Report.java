package com.example.ethanwright.javapunchcard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import java.util.List;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Report extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent get = getIntent();
        final PunchCard current  = get.getParcelableExtra("parcelable_extra");
        final BundleCards cards = get.getParcelableExtra("card_bundle");
        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);
//        YourData[] dataObjects = ...
//        int[] dataObjects = (1, 2, 3);

        List<Entry> entries = new ArrayList<Entry>();
/*
        for (int data : dataObjects) {

            // turn your data into Entry objects
 //           entries.add(new Entry(data.getValueX(), data.getValueY()));
        }

*/


        final Intent viewPunch = new Intent(this, ViewPunchHistory.class);
        TextView tv1 = (TextView) findViewById(R.id.report_card_name);
        TextView tv2 = (TextView) findViewById(R.id.report_card_category);
        final TextView tv3 = (TextView) findViewById(R.id.report_card_time);
        final TextView tv4 = (TextView) findViewById(R.id.total_time);
        tv1.setText(current.getName());
        tv2.setText(current.getCategoryName());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Report.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // Update the UI
                        FormatTime ftime = new FormatTime();
                        tv3.setText(ftime.getTime(current.getActiveDuration()));
                        ArrayList<Long> total_worked = current.getAmountAccomplished();
                        Long sum = Long.valueOf(0);
                        for(int i = 0; i < total_worked.size(); i++){
                           sum += total_worked.get(i);
                        }
                        tv4.setText(ftime.getTime(sum));


                    }
                });
            }
        }, 0, 500);


        final Button log = (Button) findViewById(R.id.view_log);
        int background_color = Colors.colorPrimaryDark;
        log.setBackgroundTintList(ColorStateList.valueOf(background_color));
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPunch.putExtra("current_card", current);
                viewPunch.putExtra("card_parcel", cards);
                startActivity(viewPunch);
            }
        });
    }

}
