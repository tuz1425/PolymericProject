package com.tuz.aggregatepayment.utils

import android.app.Activity
import com.tuz.aggregatepayment.model.GoogleConfig

/**
 * internal 禁止跨model访问该类
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
internal object GoogleManger {

    fun create(activity: Activity,config: GoogleConfig){
        //todo 可以做二次 验证。
        //todo 可以在新起类 做实际操作.
        //todo call back 在config里面。
        activity.let {

        }
        config.let {

        }
    }
}