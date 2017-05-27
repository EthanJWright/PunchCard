package com.example.ethanwright.javapunchcard;


import java.util.ArrayList;

class CardView {
    public User model = new User();
    public CurrentCard current = new CurrentCard();


    public void Current_Card_Time(){
        model.setNewUser("John Smith");
        addCard("Default Card", "");
    }

    // Add a new card to the model (given a deck)
    public void addCard(String name, String category){
        if(category == ""){
            category = "default";
        }
        PunchCard adding = new PunchCard();
        adding.generateNewCard(name, 3);

        /*
        //If there is no deck set
        if(model.getDeck_set().size() == 0){
            ArrayList<CardDeck> new_deck_set = new ArrayList<>();
            model.setDeck_set(new_deck_set);
        }
        */

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
