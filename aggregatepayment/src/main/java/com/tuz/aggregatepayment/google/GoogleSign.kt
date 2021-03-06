package com.tuz.aggregatepayment.google

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuz.aggregatepayment.model.InformationModel
import com.tuz.aggregatepayment.model.SuccessModel
import com.tuz.aggregatepayment.utils.Logger
import com.tuz.aggregatepayment.utils.Parameter
import com.tuz.aggregatepayment.utils.RequestListener
import com.tuz.aggregatepayment.utils.RequestParameter

/**
 * @author lidexin
 * @email tuz1425@dingtalk.com
 * @date 2021/6/28
 */
class GoogleSign {
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
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

    /** 连接并调用登录 */
    private fun build(model: InformationModel) {
        if (model.key.isEmpty() || model.key.length != 72) {
            throw RuntimeException("The client id cannot be empty or the length is less than 72")
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(model.key)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        mAuth = FirebaseAuth.getInstance()

        val signInIntent = mGoogleSignInClient!!.signInIntent
        activity!!.startActivityForResult(signInIntent, RequestParameter.SIGN_IN_CODE)
    }

    /** activity回调处理 */
    fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultCode.let {  }
        if (requestCode == RequestParameter.SIGN_IN_CODE) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                Logger.d("firebaseAuthWithGoogle:" + account!!.id)
                firebaseAuthWithGoogle(account.idToken.toString())
            } catch (e: ApiException) {
                Logger.d("Google sign in failed $e")
                callBack?.builder?.error?.invoke(Parameter.SIGN_IN_ERROR,"Google sign in failed $e")
            }
        }
    }

    /** 查验是否成功与否 */
    private fun firebaseAuthWithGoogle(idToken: String) {
        if (mAuth == null){
            throw RuntimeException("Please call google initialization method")
        }
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    Logger.d("signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    callBack?.builder?.success?.invoke(SuccessModel(firebaseUser = user))
                } else {
                    Logger.d("signInWithCredential:failure  ${task.exception}")
                    callBack?.builder?.error?.invoke(
                        Parameter.SIGN_IN_ERROR,
                        task.exception.toString()
                    )
                }
            }
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