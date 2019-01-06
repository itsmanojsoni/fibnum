package com.example.manojsoni.logitechinterview.movie.database;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.model.Movie;
import com.example.manojsoni.logitechinterview.movie.movielist.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseAdapter extends RecyclerView.Adapter<MovieDatabaseAdapter.ViewHolder>{


    private List<Movie> movieList = new ArrayList<>();

    private OnItemClicked listener;

    interface OnItemClicked {
        void onItemClicked(int position);
    }

    public MovieDatabaseAdapter(OnItemClicked eventListener) {
        this.listener = eventListener;
    }
    @Override
    public MovieDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_database_item, parent, false);

        MovieDatabaseAdapter.ViewHolder viewHolder = new MovieDatabaseAdapter.ViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieDatabaseAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(movieList.get(position).title);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // delete the individual movie
                    listener.onItemClicked(getAdapterPosition());

                }
            });
        }
    }
}
