package com.example.ethanwright.javapunchcard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ethanwright on 5/26/17.
 */

public class ParcelPackageManager {
    private ParcelableUser userParcel = new ParcelableUser();
    private User model = new User();


   private ArrayList<CardDeck> makeDecks(PunchCard card){
       ArrayList<CardDeck> list = model.getDeck_set();
       Boolean added = false;
		for (Iterator<CardDeck> iter = list.listIterator(); iter.hasNext(); ) {
		    CardDeck a = iter.next();
		    if (a.getCategory().equals(card.getCategoryName())) {
                a.addCard(card);
                added = true;
		    }
	   	}
	   	if(added == false){
            CardDeck new_deck = new CardDeck();
            new_deck.setCategory(card.getCategoryName());
            new_deck.addCard(card);
            list.add(new_deck);
        }
		return list;
   }

   // TODO Still in progress
   private PunchCard makeCard(int index){
       int dayNumber = 3;
       PunchCard newCard = new PunchCard();
       newCard.generateNewCard(userParcel.getNames().get(index), dayNumber);
       newCard.setCategoryName(userParcel.getCategories().get(index));
       return newCard;
   }

    public void getUserFromParcel(ParcelableUser parcel){
        userParcel = parcel;
        ArrayList<String> names = userParcel.getNames();

    }
}
