package com.tuz.aggregatepayment.utils

import android.app.Activity
import com.tuz.aggregatepayment.model.GoogleConfig

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
class GooglePaymentUtils {

    companion object {
        /**
         * 通过上下文，创建浮窗的构建者信息，使浮窗拥有一些默认属性
         * @param activity 上下文信息，优先使用Activity上下文，因为系统浮窗权限的自动申请，需要使用Activity信息
         * @return 浮窗属性构建者
         */
        @JvmStatic
        fun with(activity: Activity): Builder = Builder(activity)

    }

    /**
     * 建造者模式
     * */
    class Builder(private val activity: Activity) {
        private var config = GoogleConfig(activity)


        fun setString(string: String) = apply {
            config.string = string
        }

        fun registerCallBack(listener: RequestListener.Builder.() -> Unit) = apply {
            config.callback = RequestListener().apply { registerListener(listener) }
        }

        fun create() = GoogleManger.create(activity = activity, config)
    }


}