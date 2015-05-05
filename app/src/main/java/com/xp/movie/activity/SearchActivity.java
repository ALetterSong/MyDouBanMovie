package com.xp.movie.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xp.movie.R;
import com.xp.movie.adapter.MovieSearchAdapter;
import com.xp.movie.loader.JsonParser;
import com.xp.movie.model.Movie;

import java.util.List;


/**
 * Created by XP on 2015/5/2.
 */
public class SearchActivity extends BaseActivity {
    private static final String SEARCH_URL = "http://api.douban.com/v2/movie/search?q=";
    private List<Movie> movieList;
    private ListView searchResultListView;
    private MaterialEditText editText;
    private Toolbar toolbar;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }


    public void initView() {
//        toolbar= (Toolbar) findViewById(R.id.search_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.material_edit_text);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
//        editText = (MaterialEditText) getSupportActionBar().getCustomView().findViewById(R.id.edit_text_search);m
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
//      mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (s.trim().equals("")) {
                    if (movieList != null) {
                        movieList.clear();
                    }
                    new SearchTask().execute(s);
                } else {
                    new MaterialDialog.Builder(SearchActivity.this)
                            .title(R.string.suggest_title)
                            .content(R.string.suggest_content)
                            .positiveText(R.string.agree)
                            .show();
                }
                return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

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

