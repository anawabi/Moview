package com.amannawabi.moview.Utils;


import android.net.Uri;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkUtils {
    final static String BASE_URL = "http://api.themoviedb.org/3/movie";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "popular";
    final static String sortBy = "popular";
    final static String PARAM_API_KEY = "api_key";
    final static String API_KEY = "Add your API Key here";
    private static final String TAG = "MovieNetworkUtils";
    public static URL buildURL(String sortBy) {
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(PARAM_SORT)
                .appendQueryParameter(PARAM_API_KEY, API_KEY).build();

        URL Url = null;
        try {
            Url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            int status = urlConnection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
//                    Log.d(TAG, "getResponseFromHttpUrl: " + sb);
                    return sb.toString();
            }
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }


}
