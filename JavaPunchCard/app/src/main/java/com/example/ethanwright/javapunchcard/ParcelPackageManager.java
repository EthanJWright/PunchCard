package com.example.ethanwright.javapunchcard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ethanwright on 5/26/17.
 */

public class ParcelPackageManager {
    private ArrayList<PunchCard> UserCards = new ArrayList<>();
    private User model = new User();


    public ArrayList<PunchCard> getUserCards() {
        return UserCards;
    }

    public void setUserCards(ArrayList<PunchCard> userCards) {
        UserCards = userCards;
    }


   private void makeDecks(PunchCard card){
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
        model.setDeck_set(list);
   }

}
