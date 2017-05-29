package com.example.ethanwright.javapunchcard;
import java.util.Date;
import android.os.Parcelable;
import android.os.Parcel;


public class PunchCard implements Parcelable {
    private boolean active = false;
    private Date beginDate = new Date();
    private Date endDate = new Date();
    private long goal = 0;
    private String name = "";
    private String categoryName = "";
    private ActivityLog logger = new ActivityLog();
    private Boolean modified = false;

    public boolean isModified(){
        return modified;
    }

    public void setModified(Boolean _modified){
        modified = _modified;
    }




    public void generateNewCard(String _name, int day_number){
        name = _name;
        Date _beginDate = new Date();
        beginDate = _beginDate;
        endDate = new Date();
        long milliseconds_day = 86400000;
        endDate.setTime(endDate.getTime() + (day_number * milliseconds_day));
        goal = endDate.getTime() - beginDate.getTime();
        active = false;
        categoryName = "default";
    }


    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean _active) {
        if(_active){
            logger.punch_in();
        }
        if(!_active){
            logger.punch_out();
        }
        this.active = _active;
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
    public void setGoal(long goal) {
        this.goal = goal;
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
