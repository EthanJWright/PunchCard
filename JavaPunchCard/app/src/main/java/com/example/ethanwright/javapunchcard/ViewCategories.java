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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Iterator;

public class ViewCategories extends AppCompatActivity {

    public boolean returningFromCardView = false;
    public BundleCards user_parcel = new BundleCards();

    public void doFinish(BundleCards cards) {
        final Intent allCards = new Intent(this, ViewAllCards2.class);
        allCards.putExtra("parcelable_extra", cards);
        startActivityForResult(allCards, 1);
        /*
        Intent intent = new Intent();
        intent.putExtra("card_parcel", card);
        setResult(RESULT_OK, intent);
        finish();
        */
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            // This means returning from new card create
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {

                    String returnStyle = data.getStringExtra("return_style");
                    // If a listclick was executed return to home screen
                    if(returnStyle.equals("listclicked")) {
                    // Get data from view
                    BundleCards card = data.getParcelableExtra("card_parcel");
                    PunchCard currentCard = data.getParcelableExtra("current_card");

                    // Package simulating ViewAllCards
                    Intent intent = new Intent();
                    intent.putExtra("card_parcel", card);
                    intent.putExtra("current_card", currentCard);
                    setResult(RESULT_OK, intent);
                    finish();
                   }
                    else{
                        // Otherwise update category info with current screen
                        user_parcel = data.getParcelableExtra("card_parcel");
                        buildListView(user_parcel);
                    }
                }
            }
            // go here
        }


    @Override
    public void onBackPressed() {
        // your code.
    }

    @Override
    protected void onResume(){
        super.onResume();

    }


    public void buildListView(BundleCards user_parcel){

        final ParcelPackageManager manager = new ParcelPackageManager();

        manager.insertAll(user_parcel.getCards());

// 1
        // From the bundle, get the cards sorted by category with active cards at the top
        ArrayList<CardDeck> cardList = manager.getModel().getDeck_set();


        final ListView listView = (ListView)findViewById(R.id.categories_list);
        final CategoryAdapter CategoryAdapter = new CategoryAdapter(this, R.layout.list_item_category, cardList);
        listView.setAdapter(CategoryAdapter);


        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ViewCategories.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // Update the UI
                        ((CategoryAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                });
            }
        }, 0, 500);



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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent get = getIntent();
        BundleCards user_parcel = get.getParcelableExtra("card_parcel");
        buildListView(user_parcel);
    }

}
