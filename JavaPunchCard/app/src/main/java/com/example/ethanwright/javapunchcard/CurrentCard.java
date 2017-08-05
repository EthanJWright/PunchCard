package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/23/17.
 */

public class CurrentCard {
    private String name;
    private String category;
    private User model;

    public String getFormattedTime(){
        PunchCard card = model.findDeck(category).findCard(name);
        long time = card.getActiveDuration();
        FormatTime ftime = new FormatTime();
        return ftime.getTime(time);
    }

    public void setCurrentCard(PunchCard current, User _model){
        // Updates the current card to be worked with
        model = _model;
        PunchCard tempCard = _model.findDeck(current.getCategoryName()).findCard(current.getName());
        name = tempCard.getName();
        category = tempCard.getCategoryName();
    }

    public long getCurrent_duration() {
        PunchCard card = model.findDeck(category).findCard(name);
        long current_duration = card.getActiveDuration();
        return current_duration;
    }

    public String getName() {
        return name;
    }
    public PunchCard getCard() {
        return model.findDeck(category).findCard(name);
    }
    public boolean isActive() {
        return model.findDeck(category).findCard(name).isActive();
    }
}

