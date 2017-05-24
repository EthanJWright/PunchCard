package com.example.ethanwright.javapunchcard;
import java.util.Date;
import java.util.Scanner;

class CardView {
	private static Scanner scanner;
	public String card_print;
    static public User model = new User();


    public void Current_Card_Time(){
        model.setNewUser("John Smith");

        // Create first deck
        CardDeck deck = new CardDeck();
        deck.setNewDeck();

        //Give deck to user
        model.addDeck(deck);

        // Create card
        PunchCard card = new PunchCard();
        /* Set end date in three days  */
        card.generateNewCard("work", 3);
        model.findDeck("default").addCard(card);
    //    model.findDeck("default").findCard("work").setActive(true);

        CurrentCard.setCurrentCard(card);


    }

    public void addCard(String name, String category){
        if(category == ""){
            category = "default";
        }
        PunchCard adding = new PunchCard();
        adding.generateNewCard(name, 3);

        // If the category doesn't exist, make the category
        if(model.findDeck(category) == null){
            CardDeck new_deck = new CardDeck();
            new_deck.setNewDeck(category);
            new_deck.addCard(adding);
            model.addDeck(new_deck);
        }
        // Now that we definitely have the deck, check to see if card exists already
        if(model.findDeck(category).findCard(name) == null){
            CurrentCard.setCurrentCard(adding);
            model.findDeck(category).addCard(adding);
        }
        else{
            // If it did exist, Set it as current card
            CurrentCard.setCurrentCard(model.findDeck(category).findCard(name));
        }
    }

    public void PunchOut(){
        CurrentCard.getCard().setActive(false);
        CurrentCard.setActive(false);
    }

    public void PunchIn(){
        CurrentCard.getCard().setActive(true);
        CurrentCard.setActive(true);
    }


}
