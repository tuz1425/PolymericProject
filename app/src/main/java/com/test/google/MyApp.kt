package com.test.google

import android.app.Application
import com.test.aggregatepayment.utils.SignInUtils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/23
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SignInUtils.init(this)
    }
}