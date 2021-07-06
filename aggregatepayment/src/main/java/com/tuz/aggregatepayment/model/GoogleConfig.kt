package com.tuz.aggregatepayment.model

import android.content.Context
import com.tuz.aggregatepayment.utils.RequestListener

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