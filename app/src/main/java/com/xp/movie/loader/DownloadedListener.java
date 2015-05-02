package com.xp.movie.loader;

import android.graphics.Bitmap;

/**
 * Created by XP on 2015/5/1.
 */
public interface DownloadedListener<Handle> {
    void onThumbnailDownloaded(Handle handle,Bitmap bitmap);
}
