/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.amannawabi.moview;


import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.graphics.Movie;
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
import com.amannawabi.moview.Utils.HttpHandler;
import com.amannawabi.moview.Utils.JsonUtils;
import com.amannawabi.moview.Utils.NetworkUtils;
import com.amannawabi.moview.Utils.onTaskCompleted;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onTaskCompleted,  MovieAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    private static final String TAG = "MovieMainActivity";
    private static List<Movies> mMovieList = new ArrayList<>();
    private URL url;

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
    private void createRecycler(String sortBy){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        url = NetworkUtils.buildURL(sortBy);
        Log.d(TAG, "onCreate: URL Generated" + url);
        boolean isNetworkConnected = NetworkUtils.isNetworkConnected(this);
        if(isNetworkConnected) {
            HttpHandler movieQuery = new HttpHandler(MainActivity.this);
            movieQuery.execute(url);
        }else {
            Toast.makeText(MainActivity.this, "Network disconnected\n Please connect to internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTaskCompleted(List<Movies> movies) {
        Log.d(TAG, "onTaskCompleted: " + movies.size());
        mMovieList = movies;
        mAdapter = new MovieAdapter(mMovieList, MainActivity.this);
        Log.d(TAG, "onCreate: " + mMovieList.size());
        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "onPostExecute: " + mMovieList.size());
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

}

