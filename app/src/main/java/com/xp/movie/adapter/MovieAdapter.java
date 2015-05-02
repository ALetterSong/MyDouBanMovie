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
//import com.xp.movie.loader.DownloaderThread;
import com.xp.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XP on 2015/4/30.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
//    private DownloaderThread<ImageView>mDownloaderThread;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_movie_item, parent,false);
        }
        Movie movie = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_movie_imageView);
        TextView textView= (TextView) convertView.findViewById(R.id.item_movie_title);
        Picasso.with(getContext()).load(movie.getImgUrl()).resize(320, 450)
                .centerCrop().into(imageView);
        textView.setText(movie.getTitle());
        Log.i("Adapter",movie.getId()+"----------------------------");
   //     imageView.setImageResource(R.drawable.ic_launcher);


//        mDownloaderThread=new DownloaderThread<ImageView>();
//        mDownloaderThread.start();  //启动线程
//        mDownloaderThread.getLooper();
//        mDownloaderThread.queueThumbnail(imageView,movie.getImgUrl());


        return convertView;
    }
}
