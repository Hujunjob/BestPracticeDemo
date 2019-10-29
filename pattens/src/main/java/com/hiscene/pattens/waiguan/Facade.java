package com.hiscene.pattens.waiguan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by hujun on 2019-10-27.
 */

public class Facade {
    private String url;
    private MemoryCache memoryCache;
    private NetworkCache networkCache;
    private DiskCache diskCache;

    public Facade(String url) {
        this.url = url;
        memoryCache = new MemoryCacheImp();
        networkCache = new NetworkCacheImp();
        diskCache = new DiskCacheImp();
    }

    public Bitmap loader() {
        Bitmap bitmap = memoryCache.findByMemory(url);
        if (bitmap == null) {
            bitmap = diskCache.findByDisk(url);
        }
        if (bitmap == null) {
            InputStream inputStream = networkCache.loadFromNet(url);
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        }
        return bitmap;
    }
}
