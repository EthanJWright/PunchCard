package com.example.ethanwright.javapunchcard;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ethanwright on 5/29/17.
 */
public class CurrentReportView implements Parcelable {
    private Date startTime;
    private Date endTime;
    private long totalTime;

    public CurrentReportView() {

    }

    public void setTotalTime(long time){
        totalTime = time;
    }

    public long getTotalTime(){
        return totalTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    protected CurrentReportView(Parcel in) {
        long tmpStartTime = in.readLong();
        startTime = tmpStartTime != -1 ? new Date(tmpStartTime) : null;
        long tmpEndTime = in.readLong();
        endTime = tmpEndTime != -1 ? new Date(tmpEndTime) : null;
        totalTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime != null ? startTime.getTime() : -1L);
        dest.writeLong(endTime != null ? endTime.getTime() : -1L);
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
