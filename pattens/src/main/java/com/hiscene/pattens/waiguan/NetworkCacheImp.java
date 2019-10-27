package com.hiscene.pattens.waiguan;

import java.io.InputStream;

/**
 * Created by hujun on 2019-10-27.
 */

public class NetworkCacheImp implements NetworkCache {
    @Override
    public InputStream loadFromNet(String url) {
        System.out.println("从网络下载");
        return null;
    }
}
