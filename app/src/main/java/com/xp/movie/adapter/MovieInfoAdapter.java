package com.xp.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xp.movie.R;
import com.xp.movie.activity.BaseActivity;
import com.xp.movie.model.MovieInfo;

import java.util.List;

/**
 * Created by XP on 2015/4/30.
 */
public class MovieInfoAdapter extends ArrayAdapter {
    public MovieInfoAdapter(Context context, int resource) {
        super(context, resource);
    }
//    private List<MovieInfo> movieInfoList;
//    @Override
//    public int getCount() {
//        return movieInfoList.size();
//    }
//
//    @Override
//    public MovieInfo getItem(int i) {
//        return movieInfoList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        MovieInfo movieInfo=new MovieInfo();
        if (convertView == null) {
            convertView
                    = LayoutInflater.from(getContext()).inflate(R.layout.activity_movie_info, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView
                    = (ImageView) convertView.findViewById(R.id.movie_info_imageView);
            viewHolder.tvTitle
                    = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvDirectors
                    = (TextView) convertView.findViewById(R.id.tv_directors);
            viewHolder.tvCasts
                    = (TextView) convertView.findViewById(R.id.tv_casts);
            viewHolder.tvGenres
                    = (TextView) convertView.findViewById(R.id.tv_genres);
            viewHolder.tvSummary
                    = (TextView) convertView.findViewById(R.id.tv_summary);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext()).load(movieInfo.getImages()).resize(320, 450)
                .centerCrop().into(imageView);
        viewHolder.tvTitle.setText(notes.get(position).getTitle());
        viewHolder.tvContent.setText(notes.get(position).getContent());
        viewHolder.tvTime.setText(notes.get(position).getTime());
        return convertView;
    }

    //ViewHolder内部类
    public static class ViewHolder {
        public ImageView imageView;
        public TextView tvTitle;
        public TextView tvDirectors;
        public TextView tvCasts;
        public TextView tvGenres;
        public TextView tvSummary;
    }


        return null;
    }
}
