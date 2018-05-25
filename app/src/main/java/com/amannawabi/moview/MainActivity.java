/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.amannawabi.moview;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amannawabi.moview.Controller.MovieAdapter;
import com.amannawabi.moview.Model.Movies;
import com.amannawabi.moview.Utils.JsonUtils;
import com.amannawabi.moview.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private RecyclerView recyclerView;

    private static final String TAG = "MovieMainActivity";
    private static List<Movies> mMovieList = new ArrayList<>();
    private URL url;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        recyclerView = findViewById(R.id.movies_rv);
        createRecycler("popular");
    }

    /**
     * Generates URL by sending the sort order parameter to Network Utils buildURL method and generates
     * Recycler view and populates the movie data by calling Async Task class.
     *
     */
    private void createRecycler(String sortBy) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mProgressBar = findViewById(R.id.pb_loading_indicator);

        url = NetworkUtils.buildURL(sortBy);
        Log.d(TAG, "onCreate: URL Generated" + url);
        new movieQuery().execute(url);
    }

    /**
     * Opens Detail Activity once the movie poster is clicked.
     * sends movie data to detail activity through intent.putExtra method
     *
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, DetailedLayout.class);
        intent.putExtra("Detail Layout", mMovieList.get(clickedItemIndex));
        startActivity(intent);
    }

    /**
     * Populates the Menu in the action bar of the activity
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Enables the user to sort the movie data by Popularity and Highest Rating by providing selectable menu items
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        if (selectedItem == R.id.sort_by_popular) {
            createRecycler("popular");
        } else if (selectedItem == R.id.sort_by_top_rated) {
            createRecycler("top_rated");
            Log.d(TAG, "onOptionsItemSelected: " +url);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Enable the Application to retrieve movie data from MovieDB in a background thread and send the data
     * to UI tread through Async Task methods
     */
    private class movieQuery extends AsyncTask<URL, Void, List<Movies>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Get the URL and starts the getting data from server in background thread and send
         * the data to JSON parser for parsing and stores it in Array List to be sent to UI thread
         *
         */
        @Override
        protected List<Movies> doInBackground(URL... urls) {
            URL url = urls[0];
            String jSonData;

            List<Movies> mMovielist = new ArrayList<>();

            try {
                jSonData = NetworkUtils.getResponseFromHttpUrl(url);
//                Log.d(TAG, "doInBackground: " +jSonData);

                mMovielist = JsonUtils.parseMovieJson(jSonData);
                Log.d(TAG, "doInBackground: " + mMovielist.size());



            } catch (IOException e) {
                e.printStackTrace();
            }
            return mMovielist;
        }

        /**
         * Gets the background thread result and sends it to UI thread, it also initiate the Recycler Adapter
         * and set the data to Recycler view
         *
         */
        @Override
        protected void onPostExecute(List<Movies> movies) {
            super.onPostExecute(movies);
            RecyclerView.Adapter mAdapter;
            mMovieList = movies;

            mAdapter = new MovieAdapter(mMovieList, MainActivity.this);
            Log.d(TAG, "onCreate: " + mMovieList.size());
            mProgressBar.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(mAdapter);
            Log.d(TAG, "onPostExecute: " + mMovieList.size());


        }
    }

}

