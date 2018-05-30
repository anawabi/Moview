package com.amannawabi.moview.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.amannawabi.moview.onTaskFinished;

import java.io.IOException;
import java.net.URL;


public class HttpHandlerTrailer extends AsyncTask<URL, Void, String> {

//    public class HttpHandler extends AsyncTask<URL, Void, List<Movies>> {
        private onTaskFinished mTaskFinishwd;
        private static final String TAG = "MovieHttpHandlerTrailer";
        public HttpHandlerTrailer(onTaskFinished onTaskFinished) {

            mTaskFinishwd = onTaskFinished;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//        mProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Get the URL and starts the getting data from server in background thread and send
         * the data to JSON parser for parsing and stores it in Array List to be sent to UI thread
         *
         */
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];

            String jSonData;

//            List<Movies> mMovielist = new ArrayList<>();
            String mMovieTrailer = "";

            try {
                jSonData = NetworkUtils.getResponseFromHttpUrl(url);
//                Log.d(TAG, "doInBackground: " +jSonData);

                mMovieTrailer = JsonUtils.parseTrailerJson(jSonData);

                Log.d(TAG, "doInBackground: " +mMovieTrailer);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return mMovieTrailer;
        }

        /**
         * Gets the background thread result and sends it to UI thread, it also initiate the Recycler Adapter
         * and set the data to Recycler view
         *
         */
        @Override
        protected void onPostExecute(String mMovieTrailer) {
            super.onPostExecute(mMovieTrailer);
            mTaskFinishwd.onTaskCompleted2(mMovieTrailer);

        }


}
