//package com.xp.movie.loader;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Message;
//import android.util.Log;
//
//import com.xp.movie.utils.HttpUtils;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by XP on 2015/5/1.
// */
//public class DownloaderThread<Handle> extends HandlerThread {
//    private static final String TAG ="DownloaderThread";
//    private static final int MESSAGE_DOWNLOAD =0;
//    Handler mHandler;
//    Handler mResponseHandler;
//    DownloadedListener<Handle> mListener;
//    //请求Map
//    Map<Handle ,String > requestMap = Collections.synchronizedMap(new HashMap<Handle, String>());
//
//    public DownloaderThread() {
//        super(TAG);
//    }
//
//    @Override
//    protected void onLooperPrepared() {
//        super.onLooperPrepared();
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == MESSAGE_DOWNLOAD) {
//                    Handle handle = (Handle) msg.obj;
//                    Log.i(TAG, "Got a request from " + requestMap.get(handle));
//                    handleRequest(handle);
//                }
//
//            }
//        };
//    }
//
//    public void queueThumbnail(Handle handle ,String url){
//        Log.i(TAG,"url :" +url);
//        requestMap.put(handle,url);
//        Message message =mHandler.obtainMessage(MESSAGE_DOWNLOAD,handle);
//        message.sendToTarget();
//
//    }
//private void handleRequest(final Handle handle){
//    final  String url =requestMap.get(handle);
//    if (url==null)
//        return;
//    try {
//        byte[] bitmapBytes = HttpUtils.getUrlBytes(url);
//        final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length);
//        Log.i(TAG,"Bitmap created");
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//
//}
