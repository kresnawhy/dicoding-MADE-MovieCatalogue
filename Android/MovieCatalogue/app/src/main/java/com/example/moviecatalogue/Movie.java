package com.example.moviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

class Movie implements Parcelable {

    private int poster;
    private String title;
    private String description;

    protected Movie(Parcel in) {
        poster = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    public Movie() {
        
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(poster);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
