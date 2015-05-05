package com.xp.movie.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.xp.movie.activity.HomeActivity;
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


public class JsonParser {
    //http://developers.douban.com/wiki/?title=api_v2 豆瓣V2文档
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
                movie.setImage(images.getString("large"));  //images层
                moviesList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }

    public static List<Movie> getMovie(String url) {
        List<Movie> moviesList = new ArrayList<>();
        try {
            Log.i("json-----------",url);
            String string1 = HttpUtils.getUrl(url);
            JSONObject jsonObjectMovie = new JSONObject(string1);
            JSONArray subjects = jsonObjectMovie.getJSONArray("subjects");//第一层，subject是一个Json数组
            for (int i = 0; i < subjects.length(); i++) {
                JSONObject object = subjects.getJSONObject(i);
                JSONObject images = object.getJSONObject("images");//第三层 images是一个Json对象
                Movie movie = new Movie();
                movie.setId(object.getString("id")); //id层
                movie.setTitle(object.getString("title")); //subject层
                movie.setTime(object.getString("year"));//year层
                movie.setImage(images.getString("large"));//images层
                moviesList.add(movie);
                for (i = 0; i < moviesList.size(); i++) {
                    movie = moviesList.get(i);
                    Log.i("json---", movie.getTitle() + movie.getImage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }


    public static List<MovieInfo> getMovieInfo(String url) {
        List<MovieInfo> movieInfoLists = new ArrayList<>();
        try {
            JSONObject castsObject=null;
            String casts =" ";
            JSONObject directorsObject=null;
            String directors =" ";
            String string2 = HttpUtils.getUrl(url);
            JSONObject jsonObjectMovieInfo = new JSONObject(string2);
            JSONArray castsArray = jsonObjectMovieInfo.getJSONArray("casts");
            JSONArray directorsArray =jsonObjectMovieInfo.getJSONArray("directors");
            MovieInfo movieInfo = new MovieInfo();

            for (int i = 0; i < castsArray.length(); i++) {
                castsObject = castsArray.getJSONObject(i);
                casts+=castsObject.getString("name")+" ";
            }
            for (int i = 0; i < directorsArray.length(); i++) {
                directorsObject = directorsArray.getJSONObject(i);
                directors+=directorsObject.getString("name")+" ";
            }
            JSONObject images = jsonObjectMovieInfo.getJSONObject("images");
            movieInfo.setTitle(jsonObjectMovieInfo.getString("title"));
            movieInfo.setCasts(casts);
            movieInfo.setDirectors(directors);
            movieInfo.setGenres(jsonObjectMovieInfo.getString("genres"));
            movieInfo.setImages(images.getString("large"));
            movieInfo.setSummary(jsonObjectMovieInfo.getString("summary"));
            movieInfoLists.add(movieInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieInfoLists;
    }
}
