/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.amannawabi.moview;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.net.NetworkRequest;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.amannawabi.moview.Model.Movies;
import com.amannawabi.moview.Utils.HttpHandler;
import com.amannawabi.moview.Utils.HttpHandlerTrailer;
import com.amannawabi.moview.Utils.NetworkUtils;
import com.amannawabi.moview.Utils.onTaskCompleted;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


public class DetailedLayout extends AppCompatActivity implements onTaskFinished {
    private static final String TAG = "MovieDetailedLayout";
    private TextView mMovieTitle, mMovieRating, mMovieOverview, mMovieReleaseDate;
    public VideoView mMovieTrailerVV;
    private ImageView mMoviePoster;
    private String iMovieRating;
    private URL trailerUrl;
    private String sMovieTrailerRef;
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
        mMovieTrailerVV = findViewById(R.id.movie_trailer);
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

        String iMovieID = Integer.toString(movies.getMovieId());
        trailerUrl = NetworkUtils.buildTrailerURL(iMovieID);
        boolean isNetworkConnected = NetworkUtils.isNetworkConnected(this);
        if(isNetworkConnected) {
            HttpHandlerTrailer trailerQuery = new HttpHandlerTrailer(DetailedLayout.this);
            trailerQuery.execute(trailerUrl);
        }else {
            Toast.makeText(DetailedLayout.this, "Network disconnected\n Please connect to internet", Toast.LENGTH_LONG).show();
        }



        Log.d(TAG, "createDetailLayout: Trailer URL" + trailerUrl );
        Log.d(TAG, "createDetailLayout: " + movies.getMovieId());

//        https://www.youtube.com/watch?v=ue80QwXMRHg
    }



    @Override
    public void onTaskCompleted2(String sMovieTrailer) {
        sMovieTrailerRef = sMovieTrailer;
        Log.d(TAG, "onTaskCompleted2: " + sMovieTrailerRef);
        try {

            MediaController mMediaController = new MediaController(DetailedLayout.this);
            mMovieTrailerVV.setMediaController(mMediaController);
            mMediaController.setAnchorView(mMovieTrailerVV);
            String mYouTubeUrl = NetworkUtils.buildTrailerVideoURL(sMovieTrailerRef).toString();
            Log.d(TAG, "createDetailLayout: YouTube URL " + mYouTubeUrl);
            Uri trailerURI_YT = Uri.parse(mYouTubeUrl);
            Log.d(TAG, "onTaskCompleted2: " +trailerURI_YT);
            mMovieTrailerVV.setVideoURI(trailerURI_YT);
            mMovieTrailerVV.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
