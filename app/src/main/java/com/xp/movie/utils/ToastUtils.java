package com.xp.movie.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by XP on 2015/5/18.
 */
public class ToastUtils {

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }
}
