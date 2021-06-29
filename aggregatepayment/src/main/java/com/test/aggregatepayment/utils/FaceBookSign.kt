package com.test.aggregatepayment.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.aggregatepayment.model.InformationModel
import java.lang.RuntimeException

/**
 * @author lidexin
 * @email tuz1425@dingtalk.com
 * @date 2021/6/28
 */
class FaceBookSign {
    private val tag = "FaceBookSign"

    private var mAuth: FirebaseAuth? = null
    private var mCallbackManager: CallbackManager? = null

    private var activity: Activity? = null

    /** 监听成功失败 */
    private var callBack: RequestListener? = null

    /** 接口监听 */
    fun setReqListener(
        activity: Activity,
        model: InformationModel,
        listener: RequestListener.Builder.() -> Unit
    ) = apply {
        callBack = RequestListener().apply {
            registerListener(listener)
        }
        this.activity = activity
        if (Firebase.auth.currentUser != null) {
            callBack?.builder?.success?.invoke(SuccessModel(faceUser = Firebase.auth.currentUser))
        } else {
            build(model)
        }
    }

    private fun build(model: InformationModel) {
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        if (model.faceLoginButton == null){
            throw RuntimeException("Initialize facebook button")
        }
        val loginButton: LoginButton = model.faceLoginButton!!
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(tag, "facebook:onSuccess:${loginResult.toJson()}")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(tag, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(tag, "facebook:onError", error)
            }
        })
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(tag, "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    callBack?.builder?.success?.invoke(SuccessModel(faceUser = user))
                } else {
                    Log.w(tag, "signInWithCredential:failure", task.exception)
                    Toast.makeText(activity!!, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    callBack?.builder?.success?.invoke(null)
                }
            }
    }

    /** activity回调处理 */
    fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (mCallbackManager == null){
            throw RuntimeException("Please call facebook initialization method")
        }
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }
    /**
     * 删除用户
     * */
    fun deleteUser(): Boolean {
        if (Firebase.auth.currentUser != null) {
            //退出并删除
            Firebase.auth.signOut()
            //证明登录过
            Firebase.auth.currentUser?.let {
                it.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "user is delete")
                    } else {
                        Log.d(tag, "user not delete")
                    }
                }
            }
        }
        return true
    }

    /**
     * 获取用户信息
     * */

    fun findUserInfo(): FirebaseUser? {
        if (Firebase.auth.currentUser != null) {
            return Firebase.auth.currentUser
        }
        return null
    }
}