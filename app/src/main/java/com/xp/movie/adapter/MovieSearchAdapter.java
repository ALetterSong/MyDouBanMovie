package com.xp.movie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xp.movie.R;
import com.xp.movie.model.Movie;

import java.util.List;

/**
* Created by XP on 2015/5/4.
*/
public class MovieSearchAdapter extends ArrayAdapter {
    public MovieSearchAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_search_movie_item, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.search_item_movie_imageview);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.item_movie_title);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.search_item_movie_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Movie movie = (Movie) getItem(position);
            Picasso.with(getContext()).load(movie.getImage()).resize(320, 450)
                    .centerCrop().into(viewHolder.imageView);
            viewHolder.tv_title.setText(movie.getTitle());
            viewHolder.tv_time.setText(movie.getTime());
        Log.i("adapter-------------",movie.getTitle()+movie.getTime());
            return convertView;

    }
        private static class ViewHolder {
            private ImageView imageView;
            private TextView tv_title;
            private TextView tv_time;
        }

    }