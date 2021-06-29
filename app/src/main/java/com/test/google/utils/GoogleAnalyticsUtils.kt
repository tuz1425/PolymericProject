package com.test.google.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.google.firebase.analytics.FirebaseAnalytics
import com.test.google.toJson

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
    /** log */
    private const val TAG = "GoogleAnalyticsUtils"

    /** debug log */
    private var hasLog: Boolean = false

    /** 是否初始化过 */
    private var hasInit: Boolean = false
    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun init(context: Context) {
        if (!hasInit) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            hasInit = true
        }
    }

    /** debug 开关 */
    fun setDebug(boolean: Boolean) {
        hasLog = boolean
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
            printLog("error","key或者参数不能为空")
            return
        }
        val bundle = Bundle()
        for (entry in hashMap) {
            val key = entry.key
            val value = entry.value
            bundle.putString(key, value)
        }
        firebaseAnalytics?.logEvent(keyString, bundle)
        printLog(keyString, hashMap.toJson())
    }

    /** 输出log */
    private fun printLog(keyString: String, valueJson: String?) {
        if (!hasLog) {
            Log.d(TAG, "输出结果:\nkey=$keyString\nvalue=$valueJson")
        }
    }
}