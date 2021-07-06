package com.test.aggregatepayment.utils

import android.app.Application
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

/**
 *初始化google 登录
 *
 * face book 登录
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/28
 */
object SignInUtils {

    /** 是否初始化过 */
    private var hasInit: Boolean = false

    @Suppress("DEPRECATION")
    fun init(application: Application) {
        if (!hasInit) {
            Log.d("OverseasUtils", "init success")
            FacebookSdk.sdkInitialize(application)
            AppEventsLogger.activateApp(application)
            hasInit = true
        }
    }

    fun googleBuild(): GoogleSign {
        if (!hasInit) {
            throw RuntimeException("Please call the init method to initialize")
        }
        return GoogleSign()
    }

    fun faceBuild(): FaceBookSign {
        if (!hasInit) {
            throw RuntimeException("Please call the init method to initialize")
        }
        return FaceBookSign()
    }
}