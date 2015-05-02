package com.xp.movie.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.xp.movie.R;
import com.xp.movie.model.Movie;
import com.xp.movie.model.MovieInfo;
import com.xp.movie.utils.JsonParser;

import java.util.List;

/**
 * Created by XP on 2015/5/2.
 */
public class MovieInfoActivity extends BaseActivity{
    private Toolbar toolbar;
    private List<MovieInfo> mMovieInfos;
    private static final String TAG ="MovieInfoActivity";
    private static final String INFO_ENDPOINT ="http://api.douban.com/v2/movie/subject/";
    private String moviesId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initView();

    }
    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);  //toolbar
        toolbar.setTitle("");           //设置Toolbar标题

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moviesId=getIntent().getStringExtra("id");
        new MovieInfoTask().execute(moviesId);
        Log.i(TAG,moviesId+"-----------------------");

    }
    //后台线程,从豆瓣下载并解析Json并存入List容器
    public class MovieInfoTask extends AsyncTask<String, Void, List<MovieInfo>> {

        @Override
        protected List<MovieInfo> doInBackground(String... strings) {
            mMovieInfos = JsonParser.getMovieInfo(INFO_ENDPOINT + strings[0]);
            return mMovieInfos;

        }

        @Override
        protected void onPostExecute(List<MovieInfo> movieInfos) {
            super.onPostExecute(movieInfos);
            for (int i = 0; i < movieInfos.size(); i++) {
                MovieInfo movieInfo;
                movieInfo = mMovieInfos.get(i);
                Log.i(TAG, movieInfo.getTitle() + movieInfo.getCasts());
            }

        }
    }

}
