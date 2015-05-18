package com.xp.movie;

import android.app.Application;
import android.content.Context;

/**
 * Created by XP on 2015/5/18.
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}

