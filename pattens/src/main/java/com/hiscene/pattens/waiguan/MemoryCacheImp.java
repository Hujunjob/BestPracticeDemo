package com.hiscene.pattens.waiguan;

import android.graphics.Bitmap;

/**
 * Created by hujun on 2019-10-27.
 */

public class MemoryCacheImp implements MemoryCache{
    @Override
    public Bitmap findByMemory(String url) {
        System.out.println("内存里寻找"+url);
        return null;
    }
}
