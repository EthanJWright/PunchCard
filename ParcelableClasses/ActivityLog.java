package com.example.ethanwright.javapunchcard;
import android.widget.Button;


import java.util.*;

public class ActivityLog {
    // Currently not doing anything with this, can be used for logging information
    private ArrayList<Date> start_log = new ArrayList<Date>();
    private ArrayList<Date> end_log = new ArrayList<Date>();

    // This is time that is set when user 'punches in'
    private Date start_active = new Date();
    // This is updated every second by timer process
    private long active_duration = 0;
    private int increasing = 0;

    // This allows for user to punch in and out without issue
    private long previous_active = 0;

    public void punch_card_active(){
        active_duration = previous_active + active_time();

    }

    // To be run when a card timer is supposed to start
    public void punch_in(){
        increasing = 1;
        start_active = new Date();
        start_log.add(new Date());
    }

    // Run when card timer is supposed to end
    public void punch_out(){
        increasing = 0;
        previous_active = active_duration;
        // log the changes
        end_log.add(new Date());
    }

    // This will return the difference in time between when card was punched in  and now
    private long active_time(){
        long current_time = new Date().getTime();
        long current_active;
        current_active = current_time - (start_active.getTime() * increasing);
        return current_active;
    }

    public ArrayList<Date> getStart_log() {
        return start_log;
    }

    public ArrayList<Date> getEnd_log() {
        return end_log;
    }


    public long getActive_duration() {
        punch_card_active();
        return active_duration;
    }

}
