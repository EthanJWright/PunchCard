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
    //TODO hacky code here, refactor
    public boolean returnedSet = false;
    ArrayList<CardDeck> cardList = new ArrayList<>();
    public BundleCards user_parcel = new BundleCards();
    public BundleCards returnedCards = new BundleCards();
    public PunchCard currentCard;

    public void doFinish(BundleCards cards) {
        final Intent allCards = new Intent(this, ViewAllCards2.class);
        allCards.putExtra("parcelable_extra", cards);
        ArrayList<PunchCard> shipping = user_parcel.getCards();
        BundleCards shipCards = new BundleCards();
        shipCards.setCards(shipping);
        allCards.putExtra("actual_all_cards", shipCards);
        startActivityForResult(allCards, 1);
    }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            // This means returning from new card create
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    String returnStyle = data.getStringExtra("return_style");
                    BundleCards all_cards = data.getParcelableExtra("actual_all_cards");
                    BundleCards card_parcel = data.getParcelableExtra("card_parcel");
                    PunchCard currentCard = data.getParcelableExtra("current_card");
                    returnedCards = card_parcel;
                    // If a listclick was executed return to home screen
                    if(returnStyle.equals("listclicked")) {
                        returnedSet = true;
                        // Get data from view
                        // Package simulating ViewAllCards
                        Intent intent = new Intent();
                        intent.putExtra("actual_all_cards", all_cards);
                        intent.putExtra("current_card", currentCard);
                        intent.putExtra("return_style", returnStyle);
                        setResult(RESULT_OK, intent);
                        finish();
                   }
                    else{
                        // Insert the list of all cards to the punch card interface
                        CardView punchCardInterface = new CardView();
                        for(int i = 0; i < all_cards.getCards().size(); i++){
                            punchCardInterface.addCard(all_cards.getCards().get(i));
                        }
                        // Get the list of all cards modified in ViewAllCards
                        ArrayList<PunchCard> list = card_parcel.getCards();
                        // Take the original cards, insert the newly changed cards from the return
                        for (Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ) {
                            PunchCard a = iter.next();
                            punchCardInterface.removeCard(a);
                            punchCardInterface.addCard(a);
                        }

                        // Send the list of all cards with the newly modified cards to the listView
                        BundleCards cardsWithChanges = new BundleCards();
                        cardsWithChanges.setCards(punchCardInterface.model.getAllCards());
                        returnedCards = cardsWithChanges;
                        user_parcel = cardsWithChanges;
                        buildListView();
                    }
                }
            }
            // go here
        }




    @Override
    protected void onResume(){
        super.onResume();

    }


    public void buildListView(){

        final ParcelPackageManager manager = new ParcelPackageManager();

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
        user_parcel = get.getParcelableExtra("card_parcel");
        returnedSet = false;
        currentCard = get.getParcelableExtra("current_card");
        buildListView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if(!returnedSet){
            returnedCards.setCards(user_parcel.getCards());
        }
        intent.putExtra("card_parcel", returnedCards);
        intent.putExtra("current_card", currentCard);
        intent.putExtra("return_style", "backpressed");
        setResult(RESULT_OK, intent);
        finish();

    }

}
