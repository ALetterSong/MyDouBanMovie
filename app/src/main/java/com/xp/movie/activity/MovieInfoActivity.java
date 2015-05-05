package com.xp.movie.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xp.movie.R;
import com.xp.movie.loader.JsonParser;
import com.xp.movie.model.MovieInfo;

import java.util.List;

/**
 * Created by XP on 2015/5/2.
 */
public class MovieInfoActivity extends BaseActivity {
    private Toolbar toolbar;
    private List<MovieInfo> mMovieInfos;
    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDirector;
    private TextView tvCasts;
    private TextView tvGenres;
    private TextView tvSummary;
    private static final String TAG = "MovieInfoActivity";
    private static final String INFO_ENDPOINT = "http://api.douban.com/v2/movie/subject/";
    private String moviesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initView();


    }

    public void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);  //toolbar
//        toolbar.setTitle("");           //设置Toolbar标题

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moviesId = getIntent().getStringExtra("id");
        new MovieInfoTask().execute(moviesId);
        Log.i(TAG, moviesId + "-----------------------");
        imageView = (ImageView) findViewById(R.id.movie_info_imageView);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDirector = (TextView) findViewById(R.id.tv_directors);
        tvCasts = (TextView) findViewById(R.id.tv_casts);
        tvGenres = (TextView) findViewById(R.id.tv_genres);
        tvSummary = (TextView) findViewById(R.id.tv_summary);

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
                Picasso.with(MovieInfoActivity.this).load(movieInfo.getImages()).resize(300, 400)
                        .centerCrop()
                        .into(imageView);
                tvTitle.setText(movieInfo.getTitle());
                tvDirector.setText("导演:   " + movieInfo.getDirectors());
                tvCasts.setText("主演:   " + movieInfo.getCasts());
                tvGenres.setText("类型:   " + movieInfo.getGenres());
                tvSummary.setText("简介:   " + movieInfo.getSummary());
                Log.i("JsonParser", movieInfo.getImages());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
