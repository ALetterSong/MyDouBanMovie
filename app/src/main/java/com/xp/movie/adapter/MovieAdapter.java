package com.xp.movie.adapter;

import android.content.Context;
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
 * Created by XP on 2015/4/30.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_movie_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_movie_imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_movie_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Movie movie = getItem(position);
        Picasso.with(getContext()).load(movie.getImage()).resize(320, 450)
                .centerCrop().placeholder(R.drawable.ic_action_play).into(viewHolder.imageView);
        viewHolder.textView.setText(movie.getTitle());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
