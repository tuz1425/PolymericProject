package com.tuz.aggregatepayment.google

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.RequiresPermission
import androidx.annotation.Size
import com.google.firebase.analytics.FirebaseAnalytics
import com.tuz.aggregatepayment.toJson
import com.tuz.aggregatepayment.utils.Logger

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 *
 * 硬核查看log
 * adb shell setprop log.tag.FA VERBOSE
 * adb shell setprop log.tag.FA-SVC VERBOSE
 * adb logcat -v time -s FA FA-SVC
 */
object GoogleAnalyticsUtils {

    /** 是否初始化过 */
    private var hasInit: Boolean = false
    private var firebaseAnalytics: FirebaseAnalytics? = null

    @RequiresPermission(allOf = ["android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"])
    fun init(context: Context) {
        if (!hasInit) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            hasInit = true
        }
    }

    /** 统计埋点 */
    fun statistics(
        @NonNull @Size(min = 1L, max = 40L) keyString: String,
        hashMap: HashMap<String, String>
    ) {
        if (!hasInit) {
            throw RuntimeException("Please call the init method to initialize")
        }
        if (keyString.isEmpty() || hashMap.isEmpty()) {
            Logger.d("key或者参数不能为空")
            return
        }
        val bundle = Bundle()
        for (entry in hashMap) {
            val key = entry.key
            val value = entry.value
            bundle.putString(key, value)
        }
        firebaseAnalytics?.logEvent(keyString, bundle)
        Logger.d("key$keyString \n json${hashMap.toJson()}")
    }
}