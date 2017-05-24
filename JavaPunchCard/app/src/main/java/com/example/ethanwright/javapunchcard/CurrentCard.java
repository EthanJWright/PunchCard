package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/23/17.
 */

public class CurrentCard {
    private static String name;
    private static String category;
    private static User model;

    public static String getFormattedTime(){
        long time = model.findDeck(category).findCard(name).getLogger().getActive_duration();
        FormatTime ftime = new FormatTime();
        return ftime.getTime(time);
    }

    public static void setCurrentCard(PunchCard current, User _model){
        // Updates the current card to be worked with
        model = _model;
        PunchCard tempCard = _model.findDeck(current.getCategoryName()).findCard(current.getName());
        name = tempCard.getName();
        category = tempCard.getCategoryName();
    }

    public static long getCurrent_duration() {
        long current_duration = model.findDeck(category).findCard(name).getLogger().getActive_duration();
        return current_duration;
    }

    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        CurrentCard.name = name;
    }
    public static PunchCard getCard() {
        return model.findDeck(category).findCard(name);
    }
    public static boolean isActive() {
        return model.findDeck(category).findCard(name).isActive();
    }
}

