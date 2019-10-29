package com.hiscene.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by hujun on 2019-10-27.
 * 6.0、7.0之后获取文件FileProvider
 */

public class UrlParseUtils {
    /**
     * 创建一个图片文件输出路径的Uri（fileprovider）
     *
     * @param context
     * @param file
     * @return 转换后的scheme为FileProvider的Uri
     */
    private static Uri getUriForFile(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, getFileProvider(context), file);
        return uri;
    }

    /**
     * 获取FileProvider的路径，适配6.0+
     *
     * @param context
     * @return
     */
    private static String getFileProvider(Context context) {
        String file = context.getApplicationInfo().packageName + ".fileProvider";
        return file;
    }

    /**
     * 安装apk
     *
     * @param activity
     * @param apkFile
     */
    public static void installApk(Activity activity, File apkFile) {
        if (!apkFile.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Uri uri = getUriForFile(activity, apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }
}
