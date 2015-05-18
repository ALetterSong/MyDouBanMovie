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
import com.xp.movie.utils.CacheUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Snapshot;

/**
 * Created by XP on 2015/4/30.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private DiskLruCache mDiskLruCache;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        try {
            // 获取图片缓存路径
            File cacheDir = CacheUtils.getDiskCacheDir(context, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            // 创建DiskLruCache实例，初始化缓存数据
            mDiskLruCache = DiskLruCache
                    .open(cacheDir, CacheUtils.getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//        Snapshot snapshot=null;
//        try{
//            final String key=CacheUtils.hashKeyForDisk(movie.getImage());
//            snapshot=mDiskLruCache.get(key);
//            if (snapshot==null){
//                DiskLruCache.Editor editor=mDiskLruCache.edit(key);
//                if (editor!=null){
//
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        DiskLruCache mDiskLruCache = null;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    File cacheDir = CacheUtils.getDiskCacheDir(getContext(), "bitmap");
//                        if (!cacheDir.exists()) {
//                            cacheDir.mkdirs();
//                        }
//                        mDiskLruCache = DiskLruCache.open(cacheDir, CacheUtils.getAppVersion(getContext()), 1, 10 * 1024 * 1024);
//
//                    String imageUrl = movie.getImage();
//                    String key = CacheUtils.hashKeyForDisk(imageUrl);
//                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
//                    if (editor != null) {
//                        OutputStream outputStream = editor.newOutputStream(0);
//                        if (HttpUtils.downloadUrlToStream(imageUrl, outputStream)) {
//                            editor.commit();
//                        } else {
//                            editor.abort();
//                        }
//                    }
//                    mDiskLruCache.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        try {
//            String imageUrl = movie.getImage();
//            String key = CacheUtils.hashKeyForDisk(imageUrl);
//            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
//            if (snapShot != null) {
//                InputStream is = snapShot.getInputStream(0);
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                viewHolder.imageView.setImageBitmap(bitmap);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        Picasso.with(getContext()).load(movie.getImage()).resize(320, 450)
                .centerCrop().placeholder(R.drawable.ic_action_play).into(viewHolder.imageView);
        viewHolder.textView.setText(movie.getTitle());
//        Log.i("Adapter", movie.getId() + "----------------------------");
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
    /**
     * 将缓存记录同步到journal文件中。
     */
    public void fluchCache() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
