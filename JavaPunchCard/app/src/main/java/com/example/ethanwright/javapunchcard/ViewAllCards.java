package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ViewAllCards extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent get = getIntent();
        BundleCards user_parcel = get.getParcelableExtra("parcelable_extra");
        final TextView cardText = (TextView) findViewById(R.id.nav_card_text);

        ParcelPackageManager manager = new ParcelPackageManager();
        manager.insertAll(user_parcel.getCards());

        String printing = "";
        for(int i = 0; i < user_parcel.getCards().size(); i++){
            printing += user_parcel.getCards().get(i).getName() + " -- " + user_parcel.getCards().get(i).getCategoryName() + " -- " + Boolean.toString(user_parcel.getCards().get(i).isActive()) + "\n";
        }

//        printing = Integer.toString(user_parcel.getCards().size()) + user_parcel.getCards().get(1).getCategoryName();
       // cardText.setText(printing);
        ListView mListView;
        mListView = (ListView) findViewById(R.id.card_list_view);
// 1
        final ArrayList<PunchCard> cardList = user_parcel.getCards();
// 2
        String[] listItems = new String[cardList.size()];
// 3
        for(int i = 0; i < cardList.size(); i++){
//            PunchCard myCard = cardList.get(i);
            printing = user_parcel.getCards().get(i).getName() + " -- " + user_parcel.getCards().get(i).getCategoryName() + " -- " + Boolean.toString(user_parcel.getCards().get(i).isActive()) + "\n";
//            listItems[i] = myCard.getName();
            listItems[i] = printing;
        }
// 4
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
