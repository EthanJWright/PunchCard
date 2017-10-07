package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Date;

public class ViewPunchHistory extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_punch_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent get = getIntent();
        final PunchCard current = get.getParcelableExtra("current_card");
        final BundleCards card_parcel = get.getParcelableExtra("card_parcel");

        ArrayList<CurrentReportView> report = new ArrayList<>();
        for(int i = current.getLogger().getStart_log().size() - 1; i >= 0; i--){
            CurrentReportView newReport = new CurrentReportView();

            newReport.setStartTime(current.getPunchIn(i));
            newReport.setEndTime(current.getPunchOut(i));
            newReport.setTotalTime(current.getAmountAccomplished().get(i));

            report.add(newReport);
        }


        ViewPunchAdapter adapter = new ViewPunchAdapter(this, R.layout.list_item_punch, report);

        final ListView listView = (ListView)findViewById(R.id.punch_list);
        listView.setAdapter(adapter);

    }
}


