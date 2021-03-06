package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class ViewAllCards extends AppCompatActivity {
    BundleCards allCards = new BundleCards();
    ArrayList<PunchCard> cardList;
    PunchCard currentCard;

    public void doFinish(BundleCards cards, PunchCard current, String returnStyle) {
        Intent intent = new Intent();
        intent.putExtra("card_parcel", cards);
        intent.putExtra("current_card", current);
        intent.putExtra("return_style", returnStyle);
        intent.putExtra("actual_all_cards", allCards);
        setResult(RESULT_OK, intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Timer timer = new Timer();

        Intent get = getIntent();
        BundleCards user_parcel = get.getParcelableExtra("parcelable_extra");
        currentCard = get.getParcelableExtra("current_card");





        ArrayList<PunchCard> getCards = user_parcel.getCards();
        ParcelPackageManager manager = new ParcelPackageManager();
        cardList = manager.activeFirst(manager.sortByCategory(getCards));
        if (getIntent().hasExtra("actual_all_cards")) {
            allCards = get.getParcelableExtra("actual_all_cards");
        } else {
           allCards.setCards(cardList);
        }
        manager.insertAll(user_parcel.getCards());

        final ListView listView = (ListView)findViewById(R.id.card_list_view2);
        final CardAdapter CardAdapter = new CardAdapter(this, R.layout.list_item_recipe, cardList);
        listView.setAdapter(CardAdapter);


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ViewAllCards.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // Update the UI
                        ((CardAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                });
            }
        }, 0, 500);



        listView.setClickable(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                timer.cancel();
                PunchCard card = (PunchCard) listView.getItemAtPosition(position);
                BundleCards cards = new BundleCards();
                cards.setCards(cardList);
                doFinish(cards, card, "listclicked");
                return;
            }

        });
    }

    @Override
    public void onBackPressed() {
        BundleCards temp = new BundleCards();
        temp.setCards(cardList);
        doFinish(temp, currentCard, "backpressed");
    }

        @Override
    protected void onStop() {
        super.onStop();
        if (!getIntent().hasExtra("actual_all_cards")) {
            SharedPreferences settings = getSharedPreferences("cards", 0);
            SharedPreferences.Editor editor = settings.edit();

            Gson gson = new Gson();

            ArrayList<PunchCard> storeCards = allCards.getCards();
            PunchCard current = currentCard;
            String JSONcurrent = gson.toJson(current);

            String json = gson.toJson(storeCards);

            editor.putString("punchcard", json);
            editor.putString("current", JSONcurrent);
            editor.apply();
        }
    }
}
