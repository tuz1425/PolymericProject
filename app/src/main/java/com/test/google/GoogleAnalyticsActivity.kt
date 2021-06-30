package com.test.google

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.test.aggregatepayment.utils.GoogleAnalyticsUtils

/**
 * 文献参考
 * @linked https://firebase.google.cn/docs/analytics/get-started?platform=android
 * @author lidexin
 * @email tuz1425@dingtalk.com
 * @date 2021/6/24
 */
class GoogleAnalyticsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.google_analytics_ac)
        /** init */
        GoogleAnalyticsUtils.init(this)

        findViewById<Button>(R.id.test_analy_tics).setOnClickListener {
            val hashmap = hashMapOf<String,String>().apply {
                put("user", "10001")
                put("content", "test")
                put("type", "image")
            }
            GoogleAnalyticsUtils.statistics(keyString = "testKey", hashmap)
        }
    }
}