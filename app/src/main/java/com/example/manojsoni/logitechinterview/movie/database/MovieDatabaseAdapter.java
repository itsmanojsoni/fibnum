package com.example.manojsoni.logitechinterview.movie.database;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseAdapter extends RecyclerView.Adapter<MovieDatabaseAdapter.ViewHolder>{

    private static final String TAG = MovieDatabaseAdapter.class.getSimpleName();

    private List<Movie> movieList = new ArrayList<>();
    private OnItemClicked listener;

    interface OnItemClicked {
        void onItemClicked(int position);
    }

    MovieDatabaseAdapter(OnItemClicked eventListener) {
        this.listener = eventListener;
    }
    @Override
    public MovieDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_database_item, parent, false);

        return new MovieDatabaseAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MovieDatabaseAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(movieList.get(position).getTitle());
    }

    public void setMovieList (List<Movie> movies) {
        this.movieList = movies;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        public ViewHolder(View itemView, OnItemClicked listener) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieDatabaseTitle);

            itemView.setOnClickListener(view -> {
                // delete the individual movie
                listener.onItemClicked(getAdapterPosition());
            });
        }
    }
}
