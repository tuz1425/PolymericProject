package com.tuz.aggregatepayment.facebook

import android.app.Activity
import android.content.Intent
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
import com.tuz.aggregatepayment.model.InformationModel
import com.tuz.aggregatepayment.model.SuccessModel
import com.tuz.aggregatepayment.utils.Logger
import com.tuz.aggregatepayment.utils.Parameter
import com.tuz.aggregatepayment.utils.RequestListener
import com.tuz.aggregatepayment.utils.toJson

/**
 * @author lidexin
 * @email tuz1425@dingtalk.com
 * @date 2021/6/28
 */
class FaceBookSign {
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
            callBack?.builder?.success?.invoke(SuccessModel(firebaseUser = Firebase.auth.currentUser))
        } else {
            build(model)
        }
    }
    @Suppress("DEPRECATION")
    private fun build(model: InformationModel) {
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        if (model.faceLoginButton == null) {
            throw RuntimeException("Initialize facebook button")
        }
        val loginButton: LoginButton = model.faceLoginButton!!
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Logger.d("facebook:onSuccess:${loginResult.toJson()}")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Logger.d( "facebook:onCancel")
                callBack?.builder?.error?.invoke(
                    Parameter.REGISTER_CANCEL,
                    "facebook:onCancel"
                )
            }

            override fun onError(error: FacebookException) {
                Logger.d( "facebook:onError $error")
                callBack?.builder?.error?.invoke(
                    Parameter.REGISTER_ERROR,
                    "facebook:onError=$error"
                )
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Logger.d( "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth?.let {
            it.signInWithCredential(credential).addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Logger.d("signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    callBack?.builder?.success?.invoke(SuccessModel(firebaseUser = user))
                } else {
                    Logger.d("signInWithCredential:failure ${task.exception}")
                    callBack?.builder?.error?.invoke(
                        Parameter.SIGN_IN_ERROR,
                        "signInWithCredential:failure"
                    )
                }
            }
        }
    }

    /** activity回调处理 */
    fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (mCallbackManager == null) {
            throw RuntimeException("Please call facebook initialization method")
        }
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 删除用户
     * */
    fun deleteUser() {
        if (Firebase.auth.currentUser != null) {
            //退出并删除
            Firebase.auth.signOut()
            //证明登录过
            Firebase.auth.currentUser?.let {
                it.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callBack?.builder?.deleteUserSuccess?.invoke(Parameter.DELETE_USER_SUCCESS)
                        Logger.d("user is delete")
                    } else {
                        callBack?.builder?.deleteUserSuccess?.invoke(Parameter.DELETE_USER_ERROR)
                        Logger.d("user not delete")
                    }
                }
            }
        }
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