package com.amannawabi.moview.Controller;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.amannawabi.moview.Model.Movies;
import com.amannawabi.moview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> {

    private List<Movies> mMovies;
    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185//";
    private static final String TAG = "MovieAdapter";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(List<Movies> movies, ListItemClickListener listener){
        mMovies = movies;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);
        Log.d(TAG, "onCreateViewHolder: started");
        return new movieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, final int position) {

        try {
//            holder.mTestTV.setText(mMovies.get(position).getMovieTitle());
            Picasso.get().load(POSTER_PATH + mMovies.get(position).getMoviePoster()).into(holder.mPoster);
            Log.d(TAG, "onBindViewHolder: ");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " +mMovies.size());
        return mMovies.size();
    }

    public class movieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mPoster;
        LinearLayout mParentLayout;


        public movieViewHolder(View view) {
            super(view);
            mPoster = view.findViewById(R.id.movie_poster);
            mParentLayout = view.findViewById(R.id.parent_layout);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}





