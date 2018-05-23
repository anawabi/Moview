package com.amannawabi.moview;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amannawabi.moview.Controller.MovieAdapter;
import com.amannawabi.moview.Model.Movies;
import com.amannawabi.moview.R;
import com.amannawabi.moview.Model.Movies;
import com.amannawabi.moview.Utils.JsonUtils;
import com.amannawabi.moview.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final String TAG = "MovieMainActivity";
    static List<Movies> mMovieList =  new ArrayList<>();
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        recyclerView = findViewById(R.id.movies_rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        url = NetworkUtils.buildURL("popular");
        Log.d(TAG, "onCreate: URL Generated" + url);
        new movieQuery().execute(url);
        mAdapter = new MovieAdapter(mMovieList, this);
        Log.d(TAG, "onCreate: " + mMovieList.size());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        Intent intent = new Intent(MainActivity.this, DetailedLayout.class);
//        intent.putExtra("Detail Layout", mMoviesList.get(clickedItemIndex));
//        startActivity(intent);
//        if (mToast != null) {
//            mToast.cancel();
//        }
//        String toastMessage = "You Clicked " +mMoviesList.get(0).getMovieTitle();
//        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
//
//
//
//        mToast.show();
    }

    public static class movieQuery extends AsyncTask<URL, Void, List<Movies>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movies> doInBackground(URL... urls) {
            URL url = urls[0];
            String jSonData;

            List<Movies> mMovielist = new ArrayList<>();

            try {
                jSonData = NetworkUtils.getResponseFromHttpUrl(url);
//                Log.d(TAG, "doInBackground: " +jSonData);

                mMovielist = JsonUtils.parseMovieJson(jSonData);
                Log.d(TAG, "doInBackground: " +mMovielist.size());

//                return mMovielist;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return mMovielist;
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            super.onPostExecute(movies);
            mMovieList = movies;
            Log.d(TAG, "onPostExecute: " +mMovieList.size());

        }
    }

}

