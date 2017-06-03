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

public class ViewAllCards2 extends AppCompatActivity {
    BundleCards allCards = new BundleCards();
    ArrayList<PunchCard> cardList;
    PunchCard currentCard;

    public void doFinish(BundleCards card, PunchCard current, String returnStyle) {
        Intent intent = new Intent();
        intent.putExtra("card_parcel", card);
        intent.putExtra("current_card", current);
        intent.putExtra("return_style", returnStyle);
        intent.putExtra("actual_all_cards", allCards);
        setResult(RESULT_OK, intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cards2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Timer timer = new Timer();

        Intent get = getIntent();
        BundleCards user_parcel = get.getParcelableExtra("parcelable_extra");
        currentCard = get.getParcelableExtra("current_card");

        if (getIntent().hasExtra("actual_all_cards")) {
            allCards = get.getParcelableExtra("actual_all_cards");
        }



        ArrayList<PunchCard> getCards = user_parcel.getCards();
        ParcelPackageManager manager = new ParcelPackageManager();
        cardList = manager.activeFirst(manager.sortByCategory(getCards));

        manager.insertAll(user_parcel.getCards());

// 1


        final ListView listView = (ListView)findViewById(R.id.card_list_view2);
        final CardAdapter CardAdapter = new CardAdapter(this, R.layout.list_item_recipe, cardList);
        listView.setAdapter(CardAdapter);


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ViewAllCards2.this.runOnUiThread(new Runnable() {
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
                ArrayList<PunchCard> returnCards = new ArrayList<>();
                returnCards.add(card);

                BundleCards cards = new BundleCards();
                ArrayList<PunchCard> make_stack = returnCards;
                cards.setCards(make_stack);

                PunchCard current_card = card;

                doFinish(cards, current_card, "listclicked");
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
    protected void onStop(){
        super .onStop();

//            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences();
//        SharedPreferences settings = getPreferences(0);
            SharedPreferences settings = getSharedPreferences("cards", 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();

        ArrayList<PunchCard> storeCards = cardList;
        PunchCard current = currentCard;
        String JSONcurrent = gson.toJson(current);

        String json = gson.toJson(storeCards);

        editor.putString("punchcard", json);
        editor.putString("current", JSONcurrent);
        editor.apply();
    }

}
