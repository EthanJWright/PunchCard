package com.example.ethanwright.javapunchcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ResetByCategory extends AppCompatActivity {
    //TODO hacky code here, refactor
    public boolean returnedSet = false;
    ArrayList<CardDeck> cardList = new ArrayList<>();
    public BundleCards user_parcel = new BundleCards();
    public BundleCards returnedCards = new BundleCards();
    public PunchCard currentCard;

    public void doFinish(BundleCards cards) {
        final Intent allCards = new Intent(this, ViewAllCards.class);
        allCards.putExtra("parcelable_extra", cards);
        ArrayList<PunchCard> shipping = user_parcel.getCards();
        BundleCards shipCards = new BundleCards();
        shipCards.setCards(shipping);
        allCards.putExtra("actual_all_cards", shipCards);
        startActivityForResult(allCards, 1);
    }

    public void clearCategoryData(CardDeck deck){
        deck.clearAll();
        manager.getModel().removeDeck(deck.getCategory());
        manager.getModel().addDeck(deck);
    }

        public void deletePrompt(final CardDeck deck) {
            new AlertDialog.Builder(this)
                    .setTitle("Reset Category Data")
                    .setMessage("Are you sure you want to reset the category data? All cards will have data added to log.")
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearCategoryData(deck);
                            // do the acknowledged action, beware, this is run on UI thread
                        }
                    })
                    .create()
                    .show();

    }



    @Override
    protected void onResume(){
        super.onResume();

    }


    final ParcelPackageManager manager = new ParcelPackageManager();

    public void buildListView(){


        manager.insertAll(user_parcel.getCards());


        // From the bundle, get the cards sorted by category with active cards at the top
         cardList = manager.getModel().getDeck_set();


        final ListView listView = (ListView)findViewById(R.id.categories_list);
        final CategoryAdapter CategoryAdapter = new CategoryAdapter(this, R.layout.list_item_category, cardList);
        listView.setAdapter(CategoryAdapter);


        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ResetByCategory.this.runOnUiThread(new Runnable() {
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
                deletePrompt(deck);
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
        user_parcel = get.getParcelableExtra("card_parcel");
        returnedSet = false;
        currentCard = get.getParcelableExtra("current_card");
        buildListView();
    }

    public void backPressFinish(){
         Intent intent = new Intent();
        BundleCards cardBundle = new BundleCards();
        cardBundle.setCards(manager.getModel().getAllCards());
        intent.putExtra("card_parcel", cardBundle);
        intent.putExtra("current_card", currentCard);
        intent.putExtra("return_style", "backpressed");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        backPressFinish();
    }

}
