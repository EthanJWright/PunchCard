package com.example.ethanwright.javapunchcard;
import java.util.Date;
import java.util.ArrayList;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by ethanwright on 5/26/17.
 */

public class ParcelableUser implements Parcelable {
    private ArrayList<Date> startDates = new ArrayList<>();
    private ArrayList<Long> durations = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    public ParcelableUser() {
        startDates.add(new Date());
        long duration = 0;
        durations.add(duration);
        names.add("Ethan");
        categories.add("default");


    }

    public ArrayList<Date> getStartDates() {
        return startDates;
    }

    public void setStartDates(ArrayList<Date> startDates) {
        this.startDates = startDates;
    }

    public ArrayList<Long> getDurations() {
        return durations;
    }

    public void setDurations(ArrayList<Long> durations) {
        this.durations = durations;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }


    protected ParcelableUser(Parcel in) {
        if (in.readByte() == 0x01) {
            startDates = new ArrayList<Date>();
            in.readList(startDates, Date.class.getClassLoader());
        } else {
            startDates = null;
        }
        if (in.readByte() == 0x01) {
            durations = new ArrayList<Long>();
            in.readList(durations, Long.class.getClassLoader());
        } else {
            durations = null;
        }
        if (in.readByte() == 0x01) {
            names = new ArrayList<String>();
            in.readList(names, String.class.getClassLoader());
        } else {
            names = null;
        }
        if (in.readByte() == 0x01) {
            categories = new ArrayList<String>();
            in.readList(categories, String.class.getClassLoader());
        } else {
            categories = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (startDates == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(startDates);
        }
        if (durations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(durations);
        }
        if (names == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(names);
        }
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ParcelableUser> CREATOR = new Parcelable.Creator<ParcelableUser>() {
        @Override
        public ParcelableUser createFromParcel(Parcel in) {
            return new ParcelableUser(in);
        }

        @Override
        public ParcelableUser[] newArray(int size) {
            return new ParcelableUser[size];
        }
    };
}
