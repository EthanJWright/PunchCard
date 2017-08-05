package com.example.ethanwright.javapunchcard;

import java.util.*;
import android.os.Parcel;
import android.os.Parcelable;

public class ActivityLog implements Parcelable {
    // Currently not doing anything with this, can be used for logging information
    private ArrayList<Date> startLog = new ArrayList<>();
    private ArrayList<Date> endLog = new ArrayList<>();
    private ArrayList<Long> amountAccomplished = new ArrayList<>();

    private ArrayList<Date> firstPunch = new ArrayList<>();
    private ArrayList<Date> lastPunch = new ArrayList<>();

    // This is time that is set when user 'punches in'
    private Date startActive = new Date();
    // This is updated every second by timer process
    private long activeDuration = 0;
    private int increasing = 0;

    private long userOffset = 0;

    private long totalWorked;

    // This allows for user to punch in and out without issue
    private long previousActive = 0;

    public ArrayList<Long> getAmountAccomplished(){
        return amountAccomplished;
    }

    public ArrayList<Date> getFirstPunch() {
        return firstPunch;
    }

    public ArrayList<Date> getLastPunch() {
        return lastPunch;
    }

    public void reset(){
        amountAccomplished.add(activeDuration + userOffset);
        firstPunch.add(startLog.get(0));
        lastPunch.add(new Date());
        totalWorked += activeDuration + userOffset;
        increasing = 0;
        activeDuration = 0;
        previousActive = 0;
        userOffset = 0;
    }

    public void addToActive(long addMills){
       if((activeDuration + userOffset + addMills) < 0) {
           long extra = activeDuration + userOffset + addMills;
           userOffset += (addMills - extra);
       }
       else {
           userOffset += addMills;
       }
    }

    public ActivityLog() {

    }

    public void punchCardActive(){
        activeDuration = previousActive + active_time();

    }

    // To be run when a card timer is supposed to start
    public void punchIn(){
        increasing = 1;
        startActive = new Date();
        startLog.add(new Date());
    }

    // Run when card timer is supposed to end
    public void punchOut(){
        increasing = 0;
        previousActive = activeDuration;
        // log the changes
        endLog.add(new Date());
    }

    // This will return the difference in time between when card was punched in  and now
    private long active_time(){
        long current_time = new Date().getTime();
        long current_active;
        current_active = (current_time - startActive.getTime()) * increasing;
        return current_active;
    }

    public ArrayList<Date> getStart_log() {
        return startLog;
    }

    public ArrayList<Date> getEnd_log() {
        return endLog;
    }


    public long getActive_duration() {
        punchCardActive();
        return activeDuration + userOffset;
    }


    protected ActivityLog(Parcel in) {
        if (in.readByte() == 0x01) {
            startLog = new ArrayList<>();
            in.readList(startLog, Date.class.getClassLoader());
        } else {
            startLog = null;
        }
        if (in.readByte() == 0x01) {
            endLog = new ArrayList<>();
            in.readList(endLog, Date.class.getClassLoader());
        } else {
            endLog = null;
        }
        long tmpStart_active = in.readLong();
        startActive = tmpStart_active != -1 ? new Date(tmpStart_active) : null;
        activeDuration = in.readLong();
        increasing = in.readInt();
        previousActive = in.readLong();
        //TODO add amount accomplished to parcelable
         if (in.readByte() == 0x01) {
            firstPunch = new ArrayList<>();
            in.readList(firstPunch, Date.class.getClassLoader());
        } else {
            firstPunch = null;
        }
         if (in.readByte() == 0x01) {
            lastPunch = new ArrayList<>();
            in.readList(lastPunch, Date.class.getClassLoader());
        } else {
            firstPunch = null;
        }
        if (in.readByte() == 0x01) {
            amountAccomplished = new ArrayList<>();
            in.readList(amountAccomplished, Date.class.getClassLoader());
        } else {
            amountAccomplished = null;
        }

        totalWorked = in.readLong();
        userOffset = in.readLong();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (startLog == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(startLog);
        }
        if (endLog == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(endLog);
        }
        dest.writeLong(startActive != null ? startActive.getTime() : -1L);
        dest.writeLong(activeDuration);
        dest.writeInt(increasing);
        dest.writeLong(previousActive);
        //TODO add amount accomplished to parcelable
         if (firstPunch == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(firstPunch);
        }
        if (lastPunch == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(lastPunch);
        }
        if (amountAccomplished == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(amountAccomplished);
        }
        dest.writeLong(totalWorked);
        dest.writeLong(userOffset);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ActivityLog> CREATOR = new Parcelable.Creator<ActivityLog>() {
        @Override
        public ActivityLog createFromParcel(Parcel in) {
            return new ActivityLog(in);
        }

        @Override
        public ActivityLog[] newArray(int size) {
            return new ActivityLog[size];
        }
    };
}
