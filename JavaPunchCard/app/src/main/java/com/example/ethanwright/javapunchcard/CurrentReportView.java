package com.example.ethanwright.javapunchcard;

import java.util.Date;

/**
 * Created by ethanwright on 5/29/17.
 */

public class CurrentReportView {
    private Date startTime;
    private Date endTime;
    private long totalTime;

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
}
