package com.test.google

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.firebase.auth.*
import com.test.google.model.InformationModel
import com.test.google.utils.FaceBookSign
import com.test.google.utils.OverseasUtils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/24
 */
class FaceBookSignActivity : AppCompatActivity() {

    private val tag = "FacebookLogin"

    private var faceBuild: FaceBookSign? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.face_book_ac)

        faceBuild = OverseasUtils.faceBuild().apply {
            setReqListener(
                this@FaceBookSignActivity,
                InformationModel(faceLoginButton = findViewById(R.id.face_book_sign_in))
            ) {
                success {
                    Log.d(tag,"success 通过dsl回调更新ui")
                    updateUI(it?.faceUser)
                }
                error { i, s ->
                    Log.d(tag, "error $i + $s")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        faceBuild?.setActivityResult(requestCode, resultCode, data)
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d(tag, "updateUI ${user?.toJson()}")
    }


}