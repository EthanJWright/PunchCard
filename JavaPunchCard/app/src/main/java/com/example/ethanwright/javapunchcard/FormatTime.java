package com.example.ethanwright.javapunchcard;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by ethanwright on 5/23/17.
 */

public class FormatTime {

    private String addZero(long value){
        if(0 <= value  && value < 10){
            return "0" + Long.toString(value);
        }
        else{
            return Long.toString(value);
        }
    }

    private String removeExtra(long value){
        if(value == 0){
            return "00:";
        }
        else{
            return Long.toString(value) + ":";
        }
    }

    public String getTime(long time){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(time) % 24;
        long days = TimeUnit.MILLISECONDS.toDays(time);
        String day_str;
        if(days == 0){
            day_str = "";
        }
        else{
            day_str = Long.toString(days) + " days, ";
        }


        String time_string =  day_str + removeExtra(hours) + addZero(minutes) + ":" + addZero(seconds);
        return time_string;
    }
}
