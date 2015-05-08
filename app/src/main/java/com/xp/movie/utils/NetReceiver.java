package com.xp.movie.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by XP on 2015/5/8.
 */
public class NetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (mobileInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, "当前正在使用移动网络", Toast.LENGTH_SHORT).show();
        }
    }
}
