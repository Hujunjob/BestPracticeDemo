package com.hiscene.demo2.net

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import java.lang.RuntimeException
import java.security.AccessControlContext

/**
 * Created by junhu on 2019-11-01
 */
class NetworkUtils {
    private constructor() {
        throw RuntimeException()
    }

    companion object {
        var x = 1

        fun isNetworkAvailable(context: Context): Boolean {

            var cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            var networkInfo: NetworkInfo? = cm.activeNetworkInfo as NetworkInfo

            if (networkInfo != null && networkInfo.isAvailable) {
                return true
            }
            return false
        }

        fun goNetSetting(context: Context) {
            var intent: Intent?;
            if (Build.VERSION.SDK_INT > 10) {
                intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            } else {
                intent = Intent()

            }
            context.startActivity(intent)
        }
    }


}