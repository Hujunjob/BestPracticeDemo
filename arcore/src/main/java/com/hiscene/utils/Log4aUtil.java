package com.hiscene.utils;

import android.util.Log;

/**
 * Created by hujun on 2019/1/25.
 */

public class Log4aUtil {
    public  static void d(String tag,String format,Object...args){
        Log.d(tag, String.format(format,args));
    }
}
