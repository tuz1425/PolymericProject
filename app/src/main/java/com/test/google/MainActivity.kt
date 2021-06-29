package com.test.google

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * 首页
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/25
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.sign_google).setOnClickListener {
            startActivity(Intent(this, GoogleSignInActivity::class.java))
        }
        findViewById<Button>(R.id.sign_face_book).setOnClickListener {
            startActivity(Intent(this, FaceBookSignActivity::class.java))
        }
        findViewById<Button>(R.id.google_analytics).setOnClickListener {
            startActivity(Intent(this, GoogleAnalyticsActivity::class.java))
        }
        /** 两种支付方式 */
        /** 1外购 */
        findViewById<Button>(R.id.google_play).setOnClickListener {
            startActivity(Intent(this, GooglePayActivity::class.java))
        }
        /** 2内购 - 可以在google play 平台上创建商品。通过商品ID进行购买 */
        findViewById<Button>(R.id.google_pay).setOnClickListener {
            startActivity(Intent(this, GooglePaymentActivity::class.java))
        }



    }
}