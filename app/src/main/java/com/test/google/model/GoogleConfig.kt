package com.test.google.model

import android.content.Context
import com.test.google.utils.RequestListener

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
data class GoogleConfig(private val context: Context) {

    var string: String? = null

    var callback: RequestListener? = null

}
