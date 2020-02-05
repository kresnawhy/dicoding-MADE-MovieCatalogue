package com.example.moviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

class Item implements Parcelable {

    private String poster;
    private String backdrop;
    private String title;
    private String description;

    private Item(Parcel in) {
        poster = in.readString();
        backdrop = in.readString();
        title = in.readString();
        description = in.readString();
    }

    Item() {
        
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    String getPoster() {
        return poster;
    }

    void setPoster(String poster) {
        this.poster = poster;
    }

    String getBackdrop() {
        return backdrop;
    }

    void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

}
