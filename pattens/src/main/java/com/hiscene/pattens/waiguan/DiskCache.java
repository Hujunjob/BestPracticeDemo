package com.hiscene.pattens.waiguan;

import android.graphics.Bitmap;

/**
 * Created by hujun on 2019-10-27.
 */

public interface DiskCache {
    //内存中没有找到，则从sdcard中寻找
    Bitmap findByDisk(String url);
}
