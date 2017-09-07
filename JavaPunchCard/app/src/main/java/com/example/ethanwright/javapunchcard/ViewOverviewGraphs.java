package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewOverviewGraphs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_punch_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent get = getIntent();
        final PunchCard current = get.getParcelableExtra("current_card");
        final BundleCards card_parcel = get.getParcelableExtra("card_parcel");

        final ParcelPackageManager manager = new ParcelPackageManager();
        final User model = new User();
        manager.setModel(model);
        manager.insertAll(card_parcel.getCards());

        /* Set Up Data for Live Snapshot Graph */
        ArrayList<PieData> collect = new ArrayList<>();
        List<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colorList = new ArrayList<>();
        long total = 0;
        ArrayList<CardDeck> list = model.getDeck_set();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            total += a.getTimeWorked();
        }
        list = model.getDeck_set();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            double value = 20;
            if(total != 0){
                value = ((double) a.getTimeWorked() / (double) total) * 100;
            }else{
                value = 0.0;
            }
            entries.add(new PieEntry((float) value, a.getCategory()));
            colorList.add(a.getColor());
        }
        PieDataSet set = new PieDataSet(entries, "Live Snapshot");
        int[] colors = {Color.parseColor("#F44336"), Color.parseColor("#2196F3"), Color.parseColor("#8BC34A"), Color.parseColor("#FFC107")};
        set.setColors(colorList);
        PieData data = new PieData(set);
        collect.add(data);

        /* Set Up data points for Worked Today*/
        total = 0;
        list = model.getDeck_set();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            total += a.getTimeWorkedToday();
        }


        list = model.getDeck_set();
        entries = new ArrayList<>();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            double value;
             if(total != 0){
                value = ((double) a.getTimeWorkedToday() / (double) total) * 100;
            }else{
                value = 0.0;
            }
            entries.add(new PieEntry((float) value, a.getCategory()));
        }
        set = new PieDataSet(entries, "Worked Today");
        set.setColors(colorList);
        data = new PieData(set);
        collect.add(data);

        /* Set up data points for lifetime accomplished */
        total = 0;
        list = model.getDeck_set();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            total += a.getTotalTimeWorked();
        }


        list = model.getDeck_set();
        entries = new ArrayList<>();
        for(Iterator<CardDeck> iter = list.listIterator(); iter.hasNext();){
            CardDeck a = iter.next();
            double value;
             if(total != 0){
                value = ((double) a.getTotalTimeWorked() / (double) total) * 100;
            }else{
                value = 0.0;
            }
            entries.add(new PieEntry((float) value, a.getCategory()));
        }
        set = new PieDataSet(entries, "Total Time \n Worked");
        set.setColors(colorList);
        data = new PieData(set);
        collect.add(data);


        GraphAdapter adapter = new GraphAdapter(this, R.layout.list_item_graph, collect);

        final ListView listView = (ListView)findViewById(R.id.punch_list);
        listView.setAdapter(adapter);
    }
}


