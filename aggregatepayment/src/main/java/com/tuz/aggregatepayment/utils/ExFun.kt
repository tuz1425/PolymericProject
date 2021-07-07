package com.tuz.aggregatepayment.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/24
 */

/**
 * 扩展方法，将指定对象转换成json字符串
 * @receiver Any 待转换对象
 * @return String? json字符串,无法转换返回null
 */
fun Any.toJson(): String? {
    try {
        return Gson().toJson(this)
    } catch (e: JsonSyntaxException) {
        Logger.d("转换json异常:${e.message.toString()}")
    } catch (e: Exception) {
        Logger.d("转换json异常:${e.message.toString()}")
    }
    return null
}