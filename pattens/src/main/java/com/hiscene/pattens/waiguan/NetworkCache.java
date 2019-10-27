package com.hiscene.pattens.waiguan;

import java.io.InputStream;

/**
 * Created by hujun on 2019-10-27.
 */

public interface NetworkCache {
    //内存中找不到，sdcard里找不到，则从网络下载
    InputStream loadFromNet(String url);
}
