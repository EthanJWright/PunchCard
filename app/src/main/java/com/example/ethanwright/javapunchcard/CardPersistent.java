package com.example.ethanwright.javapunchcard;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by god on 6/3/17.
 */

public class CardPersistent extends MainActivity{
    public static final String PREFS_NAME = "MyPrefsFile";

    SharedPreferences settings = getPreferences(0);






    @Override
    protected void onStop(){
        super .onStop();

        SharedPreferences settings = getPreferences(1);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        PunchCard testCard = new PunchCard();

        ArrayList<PunchCard> cards = new ArrayList<>();
        cards.add(testCard);

        String json = gson.toJson(cards);

        editor.putString("punchcard", json);
        editor.apply();
    }
}
