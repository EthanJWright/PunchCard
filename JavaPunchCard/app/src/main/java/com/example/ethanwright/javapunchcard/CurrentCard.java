package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/23/17.
 */

public class CurrentCard {
    private static long current_duration = 0;
    private static String name = "";
    private static PunchCard card = new PunchCard();
    private static boolean active = false;


    public static String getFormatedTime(){
        long time = current_duration;
        FormatTime ftime = new FormatTime();
        return ftime.getTime(time);
    }

    public static void setCurrentCard(PunchCard current){
        name = current.getName();
        card = current;
        current_duration = current.getLogger().getActive_duration();
    }

    public static long getCurrent_duration() {
        return current_duration;
    }
    public static void setCurrent_duration(long current_duration) {
        CurrentCard.current_duration = current_duration;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        CurrentCard.name = name;
    }
    public static PunchCard getCard() {
        return card;
    }
    public static void setCard(PunchCard card) {
        CurrentCard.card = card;
    }
    public static boolean isActive() {
        return active;
    }
    public static void setActive(boolean active) {
        CurrentCard.active = active;
    }
}

