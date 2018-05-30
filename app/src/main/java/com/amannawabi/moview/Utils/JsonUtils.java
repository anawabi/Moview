/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.amannawabi.moview.Utils;

import android.util.Log;

import com.amannawabi.moview.Model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "MovieJsonUtils";
    /**
     * Gets the data as String and parse it to JSON, stores it in Array list and return it
     *
     */

    public static List<Movies> parseMovieJson(String json){

        Movies movies;
        List<Movies> mMoviesList = new ArrayList<>();
        try{
            JSONObject response = new JSONObject(json);
            JSONArray resultArray = response.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject obj = resultArray.getJSONObject(i);
                movies = new Movies(obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("poster_path"),
                        obj.getString("overview"),
                        obj.getString("vote_average"),
                        obj.getString("release_date"));

                mMoviesList.add(movies);
            }
        } catch (JSONException e){

            e.printStackTrace();
        }
        return mMoviesList;
    }

    public static String parseTrailerJson(String json){

            String mMovieTrailer ="";
        try{
            JSONObject response = new JSONObject(json);
            JSONArray resultArray = response.getJSONArray("results");
//            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject obj = resultArray.getJSONObject(0);
                mMovieTrailer = obj.getString("key");
//             }
            Log.d(TAG, "parseTrailerJson: " + mMovieTrailer);
        } catch (JSONException e){

            e.printStackTrace();
        }
        return mMovieTrailer;
    }

}
