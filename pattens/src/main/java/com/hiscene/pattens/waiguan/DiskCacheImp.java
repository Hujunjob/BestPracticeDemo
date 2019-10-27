package com.hiscene.pattens.waiguan;

import android.graphics.Bitmap;

/**
 * Created by hujun on 2019-10-27.
 */

public class DiskCacheImp implements DiskCache{
    @Override
    public Bitmap findByDisk(String url) {
        System.out.println("从本地存储寻找");
        return null;
    }
}
