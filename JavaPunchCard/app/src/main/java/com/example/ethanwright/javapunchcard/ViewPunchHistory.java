package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPunchHistory extends AppCompatActivity {

    public void doFinish(BundleCards card) {
        Intent intent = new Intent();
        intent.putExtra("card_parcel", card);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent get = getIntent();
//        BundleCards user_parcel = get.getParcelableExtra("card_bundle");
        PunchCard current = get.getParcelableExtra("parcelable_extra");

 //       final ParcelPackageManager manager = new ParcelPackageManager();

//        manager.insertAll(user_parcel.getCards());

        ArrayList<CurrentReportView> reporting = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            CurrentReportView new_report = new CurrentReportView();
            new_report.setStartTime(new Date());
            new_report.setEndTime(new Date());
            reporting.add(new_report);
        }

        /*
        ArrayList<CurrentReportView> reporting = new ArrayList<>();
        for(int i = 0; i < current.getLogger().getStart_log().size(); i++){
           CurrentReportView new_view = new CurrentReportView();
            new_view.setStartTime(current.getLogger().getStart_log().get(i));
            new_view.setEndTime(current.getLogger().getEnd_log().get(i));
            reporting.add(new_view);
        }
       */


        /*
        for(int i = 0; i < current.getLogger().getEnd_log().size(); i++){
            CurrentReportView new_view = new CurrentReportView();
        }*/

// 1
        // From the bundle, get the cards sorted by category with active cards at the top
//        ArrayList<CardDeck> cardList = manager.getModel().getDeck_set();


        final ListView listView = (ListView)findViewById(R.id.punch_history_list);
        final ViewPunchAdapter adapter = new ViewPunchAdapter(this, R.layout.list_item_category, reporting);
        listView.setAdapter(adapter);

/*
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ViewPunchHistory.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // Update the UI
                        ((PunchHistoryAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                });
            }
        }, 0, 500);
        */


/*
        listView.setClickable(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                CardDeck deck = (CardDeck) listView.getItemAtPosition(position);
                BundleCards cardBundle = new BundleCards();
                ArrayList<PunchCard> cards = deck.getDeck();
                cardBundle.setCards(cards);
                doFinish(cardBundle);
            }

        });

    */
    }

}
