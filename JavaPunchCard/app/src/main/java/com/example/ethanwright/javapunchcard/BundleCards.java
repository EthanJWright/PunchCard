package com.example.ethanwright.javapunchcard;

import java.util.ArrayList;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by ethanwright on 5/26/17.
 */

public class BundleCards implements Parcelable {
    private ArrayList<PunchCard> cards = new ArrayList<>();

    public BundleCards() {

    }

    public void addCard(PunchCard adding) {
        cards.add(adding);
    }

    public ArrayList<PunchCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<PunchCard> cards) {
        this.cards = cards;
    }

    protected BundleCards(Parcel in) {
        if (in.readByte() == 0x01) {
            cards = new ArrayList<PunchCard>();
            in.readList(cards, PunchCard.class.getClassLoader());
        } else {
            cards = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (cards == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(cards);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BundleCards> CREATOR = new Parcelable.Creator<BundleCards>() {
        @Override
        public BundleCards createFromParcel(Parcel in) {
            return new BundleCards(in);
        }

        @Override
        public BundleCards[] newArray(int size) {
            return new BundleCards[size];
        }
    };
}
