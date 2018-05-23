package com.amannawabi.moview.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable{

    private int mMovieId;
    private String mMovieTitle;
    private String mMoviePoster;
    private String mMovieOverView;
    private double mRatings;
    private String mReleaseDate;
    public Movies(int mMovieId, String movieTitle, String moviePoster, String movieOverView, double ratings, String releaseDate) {
        this.mMovieId = mMovieId;
        this.mMovieTitle = movieTitle;
        this.mMoviePoster = moviePoster;
        this.mMovieOverView = movieOverView;
        this.mRatings = ratings;
        this.mReleaseDate = releaseDate;

    }
    protected Movies(Parcel in) {
        mMovieId = in.readInt();
        mMovieTitle = in.readString();
        mMoviePoster = in.readString();
        mMovieOverView = in.readString();
        mRatings = in.readDouble();
        mReleaseDate = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieId);
        dest.writeString(mMovieTitle);
        dest.writeString(mMoviePoster);
        dest.writeString(mMovieOverView);
        dest.writeDouble(mRatings);
        dest.writeString(mReleaseDate);
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public Movies() {
    }



    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.mMovieTitle = movieTitle;
    }

    public String getMoviePoster() {
        return mMoviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.mMoviePoster = moviePoster;
    }

    public String getMovieOverView() {
        return mMovieOverView;
    }

    public void setMovieOverView(String movieOverView) {
        this.mMovieOverView = movieOverView;
    }

    public double getRatings() {
        return mRatings;
    }

    public void setRatings(double ratings) {
        this.mRatings = ratings;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }


}
