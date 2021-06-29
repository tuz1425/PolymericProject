package com.test.google

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.test.google.utils.OverseasUtils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/23
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        OverseasUtils.init(this)
    }
}