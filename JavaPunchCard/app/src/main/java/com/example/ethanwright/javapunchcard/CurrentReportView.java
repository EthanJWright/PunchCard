package com.example.ethanwright.javapunchcard;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ethanwright on 5/29/17.
 */
public class CurrentReportView implements Parcelable {
    private String startTime;
    private String endTime;
    private long totalTime;

    public CurrentReportView() {

    }

    public void setTotalTime(long time){
        totalTime = time;
    }

    public long getTotalTime(){
        return totalTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss",startTime));
        this.startTime = date;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        String date = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss",endTime));
        this.endTime = date;
    }

    protected CurrentReportView(Parcel in) {
        long tmpStartTime = in.readLong();
        String startTime = in.readString();
        String endTime = in.readString();
        long tmpEndTime = in.readLong();

        totalTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeLong(totalTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CurrentReportView> CREATOR = new Parcelable.Creator<CurrentReportView>() {
        @Override
        public CurrentReportView createFromParcel(Parcel in) {
            return new CurrentReportView(in);
        }

        @Override
        public CurrentReportView[] newArray(int size) {
            return new CurrentReportView[size];
        }
    };
}
