package com.hiscene.pattens.waiguan;

import android.graphics.Bitmap;

/**
 * Created by hujun on 2019-10-27.
 */

public interface MemoryCache {
    //从内存中寻找图片
    Bitmap findByMemory(String url);
}
