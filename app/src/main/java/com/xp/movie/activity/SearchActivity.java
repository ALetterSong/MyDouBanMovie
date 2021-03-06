package com.xp.movie.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xp.movie.R;
import com.xp.movie.adapter.MovieSearchAdapter;
import com.xp.movie.model.Movie;
import com.xp.movie.parser.JsonParser;
import com.xp.movie.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by XP on 2015/5/2.
 */
public class SearchActivity extends BaseActivity {
    private static final String SEARCH_URL = "http://api.douban.com/v2/movie/search?q=";
    private List<Movie> movieList;
    private ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    public void initView() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle(getString(R.string.search_movie));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SearchView mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(true);//表示搜索图标是否在输入框内
        mSearchView.setIconified(false);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                ToastUtils.showLongToast(SearchActivity.this,"搜索中...");
                if (!TextUtils.isEmpty(s)) {
                    try {
                        String s1 = URLEncoder.encode(s, "utf-8");
                        new SearchTask().execute(s1);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else
                    new MaterialDialog.Builder(SearchActivity.this)
                            .title(R.string.suggest_title)
                            .content(R.string.suggest_content)
                            .positiveText(R.string.agree)
                            .show();

              return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchResultListView = (ListView) findViewById(R.id.list_view_search);
        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Movie movie = (Movie) adapterView.getAdapter().getItem(i);
                String movieId = movie.getId();
                Intent intent = new Intent(SearchActivity.this, MovieInfoActivity.class);
                intent.putExtra("id", movieId);
                startActivity(intent);
            }
        });
    }


    public class SearchTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {
            movieList = JsonParser.getMovie(SEARCH_URL + strings[0]);
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieList = movies;
            searchResultListView.setAdapter(new MovieSearchAdapter(SearchActivity.this, movieList));
        }

    }
}



