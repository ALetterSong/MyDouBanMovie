package com.xp.movie.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xp.movie.R;
import com.xp.movie.adapter.DrawerItemAdapter;
import com.xp.movie.adapter.MovieAdapter;
import com.xp.movie.loader.JsonParser;
import com.xp.movie.model.DrawerItem;
import com.xp.movie.model.Movie;
import com.xp.movie.utils.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XP on 2015/4/12.
 */
public class HomeActivity extends BaseActivity {

    private Toolbar mToolbar;
    private GridView gridView;
    private List<Movie> mMovies;
    private ProgressBar progressBar;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout relativeLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView leftDrawerListView, settingListView;
    private ArrayAdapter drawerItemAdapter, drawerSettingItemAdapter;
    private static final String ENDPOINT = "http://api.douban.com";
    private static final String US_BOX = "北美票房榜";
    private static final String TOP250 = "TOP250";
    private static final String SEARCH = "搜索";
    private static final String SETTING = "设置";
    private static final String QUIT = "退出";
    private static final String US_BOX_URL = "/v2/movie/us_box";
    private static final String TOP250_URL = "/v2/movie/top250";
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        leftDrawerListView = (ListView) findViewById(R.id.lv_left_menu);
        settingListView = (ListView) findViewById(R.id.lv_left_setting_menu);
        gridView = (GridView) findViewById(R.id.gridView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.orange);
        setupAdapter();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {  //打开菜单
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {  //关闭菜单
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "is refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        initDrawerItem();
        new MovieTask().execute(US_BOX_URL);
    }

    //gridView适配器
    private void setupAdapter() {
        if (mMovies != null) {
            gridView.setAdapter(new MovieAdapter(HomeActivity.this, mMovies));
        } else {
            gridView.setAdapter(null);
        }
    }

    //初始化drawer元素
    public void initDrawerItem() {
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
        //上半部分
        ArrayList<DrawerItem> drawerItemArrayList = new ArrayList<>();
        String[] drawerItemNames = new String[]{US_BOX,TOP250,SEARCH};
        int[] drawerItemIconIds = new int[]{
                R.drawable.ic_action_play,
                R.drawable.ic_action_play,
                R.drawable.ic_action_search_black,
        };
        for (int i = 0; i < 3; i++) {
            DrawerItem drawerItem = new DrawerItem(drawerItemIconIds[i], drawerItemNames[i]);
            drawerItemArrayList.add(drawerItem);
        }
        drawerItemAdapter = new DrawerItemAdapter(this, R.layout.drawer_item, drawerItemArrayList);
        leftDrawerListView.setAdapter(drawerItemAdapter);
        //下半部分
        ArrayList<DrawerItem> drawerSettingItemsArrayList = new ArrayList<>();
        String[] drawerSettingItemNames = new String[]{SETTING,QUIT};
        int[] drawerSettingItemIconIds = new int[]{
                R.drawable.ic_action_settings,
                R.drawable.ic_action_cancel
        };
        for (int i = 0; i < 2; i++) {
            DrawerItem drawerItem = new DrawerItem(drawerSettingItemIconIds[i], drawerSettingItemNames[i]);
            drawerSettingItemsArrayList.add(drawerItem);
        }
        drawerSettingItemAdapter = new DrawerItemAdapter(this, R.layout.drawer_item, drawerSettingItemsArrayList);
        settingListView.setAdapter(drawerSettingItemAdapter);
        initClick();
    }

    //点击事件
    public void initClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Movie movie = ((MovieAdapter) adapterView.getAdapter()).getItem(i);
                String movieId = movie.getId();
                Intent intent = new Intent(HomeActivity.this, MovieInfoActivity.class);
                intent.putExtra("id", movieId);
                startActivity(intent);
            }
        });
        leftDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(HomeActivity.this,"加载中",Toast.LENGTH_SHORT).show();
                        new MovieTask().execute(US_BOX_URL);
                        mDrawerLayout.closeDrawer(relativeLayout);
                        break;
                    case 1:
                        Toast.makeText(HomeActivity.this,"加载中",Toast.LENGTH_SHORT).show();
                        new MovieTask().execute(TOP250_URL);
                        mDrawerLayout.closeDrawer(relativeLayout);
                        break;
                    case 2:
                        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(relativeLayout);
                        break;
                    default:
                        break;
                }
            }
        });
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(relativeLayout);
                        break;
                    case 1:
                        ActivityCollector.finishAll();
                    default:
                        break;
                }
            }
        });
    }


    //后台线程,从豆瓣下载并解析Json并存入List容器
    public class MovieTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings[0] == US_BOX_URL) {
                mMovies = JsonParser.getMovieWithSubject(ENDPOINT + strings[0]);
            } else if (strings[0] == TOP250_URL) {
                mMovies = JsonParser.getMovie(ENDPOINT + strings[0]);
            }
            return mMovies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMovies = movies;
            setupAdapter();
        }
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //响应菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.action_setting:
                initAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //关于
    public void initAbout() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.about)
                .customView(R.layout.dialog_webview, false)
                .positiveText(android.R.string.ok)
                .build();
        WebView webView = (WebView) dialog.getCustomView().findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/webview.html");
        dialog.show();
    }
}


