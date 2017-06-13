package com.example.ethanwright.javapunchcard;


import java.util.Random;
import android.graphics.Color;

class CardView {
    public User model = new User();
    public CurrentCard current = new CurrentCard();


        // Add a new card to the model (given a deck)
    public void removeCard(PunchCard card){
        String category = card.getCategoryName();
        String name = card.getName();
        if(category == ""){
            category = "default";
        }

        // If the category doesn't exist, make the category
        if(model.findDeck(category) == null){
            return;
        }
        // Now that we definitely have the deck, check to see if card exists already
        if(model.findDeck(category).findCard(name) == null){
            return;
        }
        else{
            PunchCard temp_card = model.findDeck(category).findCard(name);
            model.findDeck(category).removeCard(temp_card);
        }

    }

    private int randColor(){
        Random rand = new Random();
        double r = rand.nextDouble() * 255;
        double b = rand.nextDouble() * 255;
        double g = rand.nextDouble() * 255;
        int red = ((int) r);
        int blue = ((int) b);
        int green = ((int) g);
        return Color.argb(255, red, blue, green);
    }


    public String getGoalPercent(PunchCard card){
        double worked = card.getLogger().getActive_duration();
        double goals = card.getGoal();
        goals = Math.round((worked/goals) * 100);
        String extra = Double.toString(goals);
        return extra;
    }

    // Add a new card to the model (given a deck)
    public void addCard(PunchCard card){
        String name = card.getName();
        String category = card.getCategoryName();
        if(category == ""){
            category = "default";
        }
        PunchCard adding = card;
//        adding.setCategoryName(category);

        Boolean firstInCategory = false;
        // If the category doesn't exist, make the category
        if(model.findDeck(category) == null){
            if(adding.color == -1) {
                adding.color = randColor();
            }
            firstInCategory = true;
            CardDeck new_deck = new CardDeck();
            new_deck.setNewDeck(category);
            new_deck.addCard(adding);
            new_deck.setColor(adding.color);
            model.addDeck(new_deck);
        }
        // Now that we definitely have the deck, check to see if card exists already
        if(!firstInCategory){
            adding.color = model.findDeck(category).getColor();
        }
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
