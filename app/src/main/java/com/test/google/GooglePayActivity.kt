package com.test.google

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.aggregatepayment.utils.GooglePayUtils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/24
 */
class GooglePayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gogole_play_ac)

        GooglePayUtils.init(this)
        //todo 如果log 显示成功
        //todo 则调用 GooglePayUtils.isConnect() 来做下一步做的
    }
}