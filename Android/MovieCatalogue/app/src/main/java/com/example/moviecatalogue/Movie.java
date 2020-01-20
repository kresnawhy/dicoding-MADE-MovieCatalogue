package com.example.moviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

class Movie implements Parcelable {

    private String poster;
    private String title;
    private String description;

    private Movie(Parcel in) {
        poster = in.readString();
        title = in.readString();
        description = in.readString();
    }

    Movie() {
        
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
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

    String getPoster() {
        return poster;
    }

    void setPoster(String poster) {
        this.poster = poster;
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
