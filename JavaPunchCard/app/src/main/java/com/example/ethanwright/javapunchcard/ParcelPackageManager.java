package com.example.ethanwright.javapunchcard;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ethanwright on 5/26/17.
 */

public class ParcelPackageManager {
    private ArrayList<PunchCard> UserCards = new ArrayList<>();
    private User model = new User();

    public User getModel() {
        return model;
    }

    public ArrayList<PunchCard> getUserCards() {
        return UserCards;
    }

    public void setUserCards(ArrayList<PunchCard> userCards) {
        UserCards = userCards;
    }

   public void insertAll(ArrayList<PunchCard> allCards){
       ArrayList<PunchCard> list = allCards;
       for(Iterator<PunchCard> iter = list.listIterator(); iter.hasNext(); ){
           PunchCard card = iter.next();
           makeDecks(card);
       }
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
            new_deck.setColor(card.color);
            list.add(new_deck);
        }
        model.setDeck_set(list);
   }

   // Return the List with all active cards at the top (For ListView)
   public ArrayList<PunchCard> activeFirst(ArrayList<PunchCard> cards) {
       ArrayList<PunchCard> newCards = cards;
       for (int i = 0; i < cards.size(); i++) {
           if (cards.get(i).isActive()) {
               PunchCard temp = newCards.get(i);
               newCards.remove(i);
               newCards.add(0, temp);
           }
       }
       return newCards;
   }

   public ArrayList<PunchCard> sortByCategory(ArrayList<PunchCard> cards) {
       ArrayList<PunchCard> newCards = new ArrayList<>();
       newCards.add(cards.get(0));
       for (int i = 1; i < cards.size(); i++) {
           // Set the boolean to move to next value
           Boolean inserted_item = false;
           for(int j = 0; j < newCards.size(); j++){
               // If the card to be inserted is equal to the current category at index j
               if (cards.get(i).getCategoryName().equals(newCards.get(j).getCategoryName()) && !inserted_item) {
               //    newCards.add(j, cards.get(i));
                   newCards.add(j, cards.get(i));
                   inserted_item = true;
               }
           }
           // If category didn't already exist, add to end
           if(!inserted_item){
               newCards.add(cards.get(i));
           }
       }
       return newCards;
   }

}
