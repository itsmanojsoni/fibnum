package com.example.manojsoni.logitechinterview.ui.movielist;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manojsoni.logitechinterview.FibNumAdapter;
import com.example.manojsoni.logitechinterview.R;
import com.example.manojsoni.logitechinterview.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie>  movieList = new ArrayList<>();
    private static final String TAG = MovieListAdapter.class.getSimpleName();

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);

        return viewHolder;
    }

    public void setMovieList(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        holder.onBind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieIv;
        TextView movieTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            movieIv = itemView.findViewById(R.id.movieIv);
            movieTitleTv = itemView.findViewById(R.id.movieTitleTv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Item Clicked");
                }
            });
        }

        public void onBind(Movie movie) {
            Glide.with(itemView.getContext())
                    .load(movie.image)
                    .into(movieIv);
            movieTitleTv.setText(movie.title);
        }
    }
}

