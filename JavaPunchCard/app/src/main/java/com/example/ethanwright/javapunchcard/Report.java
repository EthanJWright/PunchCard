package com.example.ethanwright.javapunchcard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class Report extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent get = getIntent();
        final PunchCard current  = get.getParcelableExtra("parcelable_extra");
        final BundleCards cards = get.getParcelableExtra("card_bundle");



        final Intent viewPunch = new Intent(this, ViewPunchHistory.class);
        TextView tv1 = (TextView) findViewById(R.id.report_card_name);
        TextView tv2 = (TextView) findViewById(R.id.report_card_category);
        TextView tv3 = (TextView) findViewById(R.id.report_card_time);
        tv1.setText(current.getName());
        tv2.setText(current.getCategoryName());
        FormatTime ftime = new FormatTime();
        tv3.setText(ftime.getTime(current.getLogger().getActive_duration()));


        /*
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Report.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // Update the UI
                        updateUI(current);
                    }
                });
            }
        }, 0, 500);
        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPunch.putExtra("parcelable_extra", current);
                viewPunch.putExtra("card_bundle", cards);
                startActivity(viewPunch);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
