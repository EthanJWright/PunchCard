package com.example.ethanwright.javapunchcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
            printing += user_parcel.getCards().get(i).getName() + " -- " + user_parcel.getCards().get(i).getCategoryName() + "\n";
        }

//        printing = Integer.toString(user_parcel.getCards().size()) + user_parcel.getCards().get(1).getCategoryName();
        cardText.setText(printing);


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
