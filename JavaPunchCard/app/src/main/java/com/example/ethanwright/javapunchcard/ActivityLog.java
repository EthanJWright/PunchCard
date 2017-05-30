package com.example.ethanwright.javapunchcard;

import java.util.*;
import android.os.Parcel;
import android.os.Parcelable;

public class ActivityLog implements Parcelable {
    // Currently not doing anything with this, can be used for logging information
    private ArrayList<Date> start_log = new ArrayList<>();
    private ArrayList<Date> end_log = new ArrayList<>();
    private ArrayList<Long> amount_accomplished = new ArrayList<>();

    // This is time that is set when user 'punches in'
    private Date start_active = new Date();
    // This is updated every second by timer process
    private long active_duration = 0;
    private int increasing = 0;

    // This allows for user to punch in and out without issue
    private long previous_active = 0;

    public ArrayList<Long> getAmount_accomplished(){
        return amount_accomplished;
    }

    public void reset(){
        amount_accomplished.add(active_duration);
        increasing = 0;
        active_duration = 0;
        previous_active = 0;
    }

    public ActivityLog() {

    }

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
        current_active = (current_time - start_active.getTime()) * increasing;
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


    protected ActivityLog(Parcel in) {
        if (in.readByte() == 0x01) {
            start_log = new ArrayList<Date>();
            in.readList(start_log, Date.class.getClassLoader());
        } else {
            start_log = null;
        }
        if (in.readByte() == 0x01) {
            end_log = new ArrayList<Date>();
            in.readList(end_log, Date.class.getClassLoader());
        } else {
            end_log = null;
        }
        long tmpStart_active = in.readLong();
        start_active = tmpStart_active != -1 ? new Date(tmpStart_active) : null;
        active_duration = in.readLong();
        increasing = in.readInt();
        previous_active = in.readLong();
        //TODO add amount accomplished to parcelable

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (start_log == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(start_log);
        }
        if (end_log == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(end_log);
        }
        dest.writeLong(start_active != null ? start_active.getTime() : -1L);
        dest.writeLong(active_duration);
        dest.writeInt(increasing);
        dest.writeLong(previous_active);
        //TODO add amount accomplished to parcelable
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
