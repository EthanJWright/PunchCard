package com.example.ethanwright.javapunchcard;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import android.os.Parcelable;
import android.os.Parcel;
import android.text.format.DateUtils;


public class PunchCard implements Parcelable {
    private boolean active = false;
    private Date beginDate = new Date();
    private Date endDate = new Date();
    private long goal = 0;
    private String name = "";
    private String categoryName = "";
    private ActivityLog logger = new ActivityLog();
//    private Boolean modified = false;
    private int numberInCategory = 1;
    // here
    public int color = -1;
    private long lifeTime = 0;

    public void clearProgress(){
        lifeTime += logger.getActive_duration();
        logger.reset();
        active = false;
    }

    public long getActiveDuration(){
        return this.getLogger().getActive_duration();
    }

    public void addUserBuffer(long mill){
        this.getLogger().addToActive(mill);
    }


    public long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getNumberInCategory() {
        return numberInCategory;
    }

    public void setNumberInCategory(int numberInCategory) {
        this.numberInCategory = numberInCategory;
    }


    public void generateNewCard(String _name, int day_number){
        name = _name;
        Date _beginDate = new Date();
        beginDate = _beginDate;
        endDate = new Date();
        long milliseconds_day = 86400000;
        endDate.setTime(endDate.getTime() + (day_number * milliseconds_day));
        active = false;
        categoryName = "default";
        lifeTime = 0;
    }


    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getCategoryName() {
        if(categoryName == null){
            return "default";
        } else{
            return categoryName;
        }
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public Long getTotalWorked(){
        long sum = 0;
        ArrayList<Long> accomplished = getAmountAccomplished();
        for(int i = 0; i < accomplished.size(); i++){
            sum += accomplished.get(i);
        }
        sum += logger.getActive_duration() + logger.getCurrentAccomplished();
        return sum;
    }

    public Long getTimeWorkedToday(){
        Long timeWorkedToday = (long) 0;
        for(int i = logger.getStart_log().size() -1; i >=0; i--) {
            Long punchedIn = logger.getStart_log().get(i).getTime();
//				Long punchedOut = a.getPunchOut(i).getTime();
            Long punchedOut = logger.getEnd_log().get(i).getTime();
            if (DateUtils.isToday(punchedIn) && DateUtils.isToday(punchedOut)) {
                timeWorkedToday += logger.getAmountAccomplished().get(i);
            }
            timeWorkedToday += getCurrentAccomplished();
        }
        return timeWorkedToday;
    }

    public Long getCurrentAccomplished(){
        return logger.getCurrentAccomplished();
    }

    public void setActive(boolean _active) {
        if(_active){
            logger.punchIn();
        }
        if(!_active){
            logger.punchOut();
        }
        this.active = _active;
    }

    public ArrayList<Long> getAmountAccomplished(){
        return this.getLogger().getAmountAccomplished();
    }

    public Date getPunchIn(int index){
        return logger.getStart_log().get(index);
    }

    public Date getPunchOut(int index){
        return logger.getEnd_log().get(index);
    }

    public ActivityLog getLogger() {
        return logger;
    }


    public void setLogger(ActivityLog logger) {
        this.logger = logger;
    }


    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate() {
        Date current = new Date();
        this.beginDate = current;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public long getGoal() {
        return goal;
    }

    public void addGoal(long _goal) {
        if((this.goal + _goal) < 0){
           long extra = this.goal + _goal;
           this.goal += (_goal - extra);
        }else{
            this.goal += _goal;
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    protected PunchCard(Parcel in) {
        active = in.readByte() != 0x00;
        long tmpBeginDate = in.readLong();
        beginDate = tmpBeginDate != -1 ? new Date(tmpBeginDate) : null;
        long tmpEndDate = in.readLong();
        endDate = tmpEndDate != -1 ? new Date(tmpEndDate) : null;
        goal = in.readLong();
        name = in.readString();
        categoryName = in.readString();
        logger = (ActivityLog) in.readValue(ActivityLog.class.getClassLoader());
        color = in.readInt();
        lifeTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeLong(beginDate != null ? beginDate.getTime() : -1L);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeLong(goal);
        dest.writeString(name);
        dest.writeString(categoryName);
        dest.writeValue(logger);
        dest.writeInt(color);
        dest.writeLong(lifeTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PunchCard> CREATOR = new Parcelable.Creator<PunchCard>() {
        @Override
        public PunchCard createFromParcel(Parcel in) {
            return new PunchCard(in);
        }

        @Override
        public PunchCard[] newArray(int size) {
            return new PunchCard[size];
        }
    };

    public PunchCard() {

    }
}
