package com.example.ethanwright.javapunchcard;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class CardView {
	private static Scanner scanner;
	public String card_print;
    public User model = new User();
    public CurrentCard current = new CurrentCard();


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



        current.setCurrentCard(card, model);


    }

    // Add a new card to the model (given a deck)
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
            model.findDeck(category).addCard(adding);
            current.setCurrentCard(adding, model);
        }
        else{
            current.setCurrentCard(adding, model);
            // If it did exist, Set it as current card
          //  CurrentCard.setCurrentCard(model.findDeck(category).findCard(name));
        }
    }

    public String activeNames(){
        CardDeck allActive = model.getActive();
        String returning = "";
        for(int i = 0; i < allActive.getTotalCount(); i++){
            returning += allActive.getDeck().get(i) + " , ";
        }
        return returning;
    }

    // Android interface for getting active cards
    public CardDeck getActive(){
        CardDeck allActive = model.getActive();
        return allActive;
    }

    // Android interface for punching in
    public void PunchOut(){
        current.getCard().setActive(false);
        /*
        CurrentCard.getCard().setActive(false);
        CurrentCard.setActive(false);
        */
    }

    // Android interface for punching out
    public void PunchIn(){
        current.getCard().setActive(true);
        /*
        CurrentCard.getCard().setActive(true);
        CurrentCard.setActive(true);
        */
    }

    public CurrentCard getCurrent(){
        return current;
    }


}
