package com.xp.movie.parser;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xp.movie.App;
import com.xp.movie.R;
import com.xp.movie.model.Movie;
import com.xp.movie.model.MovieInfo;
import com.xp.movie.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XP on 2015/4/29.
 */

//http://developers.douban.com/wiki/?title=api_v2 豆瓣V2文档
public class JsonParser {
    public static List<Movie> getMovieWithSubject(String url) {
        List<Movie> moviesList = new ArrayList<>();
        try {
            String string1 = HttpUtils.getUrl(url);
            JSONObject jsonObjectMovie = new JSONObject(string1);
            JSONArray subjects = jsonObjectMovie.getJSONArray("subjects");//第一层，subject是一个Json数组
            for (int i = 0; i < subjects.length(); i++) {
                JSONObject object = subjects.getJSONObject(i);
                JSONObject subject = object.getJSONObject("subject");//第二层 subject是一个Json对象
                JSONObject images = subject.getJSONObject("images");//第三层 images是一个Json对象
                Movie movie = new Movie();
                movie.setId(subject.getString("id")); //id层
                movie.setTitle(subject.getString("title")); //subject层
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
                boolean sPref = preferences.getBoolean(String.valueOf(R.string.image_quality_key), true);
                if (sPref) {
                    movie.setImage(images.getString("large"));
                } else {
                    movie.setImage(images.getString("medium"));
                }//images层
                moviesList.add(movie);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }

    public static List<Movie> getMovie(String url) {
        List<Movie> moviesList = new ArrayList<>();
        try {
//            Log.i("json-----------",url);
            String string1 = HttpUtils.getUrl(url);
//            Log.i("json-----------",string1);
            JSONObject jsonObjectMovie = new JSONObject(string1);
            JSONArray subjects = jsonObjectMovie.getJSONArray("subjects");//第一层，subject是一个Json数组
            for (int i = 0; i < subjects.length(); i++) {
                JSONObject object = subjects.getJSONObject(i);
                JSONObject images = object.getJSONObject("images");//第三层 images是一个Json对象
                Movie movie = new Movie();
                movie.setId(object.getString("id")); //id层
                movie.setTitle(object.getString("title")); //subject层
                movie.setTime(object.getString("year"));//year层
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
                boolean sPref = preferences.getBoolean(String.valueOf(R.string.image_quality_key), true);
                if (sPref) {
                    movie.setImage(images.getString("large"));
                } else {
                    movie.setImage(images.getString("medium"));
                }//images层
                moviesList.add(movie);

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }


    public static List<MovieInfo> getMovieInfo(String url) {
        List<MovieInfo> movieInfoLists = new ArrayList<>();
        try {
            JSONObject castsObject;
            String casts = " ";
            JSONObject directorsObject;
            String directors = " ";
            String string2 = HttpUtils.getUrl(url);
            JSONObject jsonObjectMovieInfo = new JSONObject(string2);
            JSONArray castsArray = jsonObjectMovieInfo.getJSONArray("casts");
            JSONArray directorsArray = jsonObjectMovieInfo.getJSONArray("directors");
            MovieInfo movieInfo = new MovieInfo();

            for (int i = 0; i < castsArray.length(); i++) {
                castsObject = castsArray.getJSONObject(i);
                casts += castsObject.getString("name") + " ";
            }
            for (int i = 0; i < directorsArray.length(); i++) {
                directorsObject = directorsArray.getJSONObject(i);
                directors += directorsObject.getString("name") + " ";
            }
            JSONObject images = jsonObjectMovieInfo.getJSONObject("images");
            movieInfo.setTitle(jsonObjectMovieInfo.getString("title"));
            movieInfo.setCasts(casts);
            movieInfo.setDirectors(directors);
            movieInfo.setGenres(jsonObjectMovieInfo.getString("genres"));
            movieInfo.setImages(images.getString("large"));
            movieInfo.setSummary(jsonObjectMovieInfo.getString("summary"));
            movieInfoLists.add(movieInfo);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return movieInfoLists;
    }
}
