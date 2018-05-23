package com.amannawabi.moview.Utils;

import android.graphics.Movie;

import com.amannawabi.moview.Model.Movies;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Movies> parseMovieJson(String json){

        Movies movies = new Movies();
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
                        obj.getInt("vote_average"),
                        obj.getString("release_date"));

                mMoviesList.add(movies);
            }



        } catch (JSONException e){

            e.printStackTrace();
        }
        return mMoviesList;
    }

}
