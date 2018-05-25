/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.amannawabi.moview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amannawabi.moview.Model.Movies;
import com.squareup.picasso.Picasso;


public class DetailedLayout extends AppCompatActivity {
    private TextView mMovieTitle, mMovieRating, mMovieOverview, mMovieReleaseDate;
    private ImageView mMoviePoster;
    private String iMovieRating;


    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w780//";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_layout);
        mMovieTitle = findViewById(R.id.movie_title);
        mMoviePoster = findViewById(R.id.movie_poster);
        mMovieRating = findViewById(R.id.movie_rating);
        mMovieOverview = findViewById(R.id.movie_overview);
        mMovieReleaseDate = findViewById(R.id.movie_release_date);
        createDetailLayout();

    }

    /**
     * Sets the movie data into UI components in Detail Activity
     */
    private void createDetailLayout(){
        final String RATING = "/10";
        Intent intent = getIntent();
        Movies movies = intent.getParcelableExtra("Detail Layout");
        iMovieRating = movies.getRatings();
        mMovieTitle.setText(movies.getMovieTitle());
        Picasso.get().load(POSTER_PATH + movies.getMoviePoster()).into(mMoviePoster);
        mMovieRating.setText(iMovieRating + RATING);
        mMovieReleaseDate.setText(movies.getReleaseDate().substring(0,4));
        mMovieOverview.setText(movies.getMovieOverView());


    }
}
