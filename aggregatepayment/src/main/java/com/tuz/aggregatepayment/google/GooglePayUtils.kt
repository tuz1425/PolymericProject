package com.tuz.aggregatepayment.google

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.tuz.aggregatepayment.model.PurchaseModel
import com.tuz.aggregatepayment.model.SuccessModel
import com.tuz.aggregatepayment.utils.Logger
import com.tuz.aggregatepayment.utils.Parameter
import com.tuz.aggregatepayment.utils.RequestListener

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
object GooglePayUtils {
    /** 是否初始化过 */
    private var hasInit: Boolean = false

    /** 谷歌支付远程成功否 */
    private var isConnect: Boolean = false

    /** 监听成功失败 */
    private var callBack: RequestListener? = null


    private var billingClient: BillingClient? = null
    private var skuList = arrayListOf<String>()
    private var skuDetail: SkuDetails? = null

    /** 初始化 */
    fun init(context: Context,listener: RequestListener.Builder.() -> Unit) {
        if (!hasInit) {
            /** 初始化接口 */
            callBack = RequestListener().apply {
                registerListener(listener)
            }
            /** 初始化BillingClient */
            billingClient =
                BillingClient.newBuilder(context).enablePendingPurchases().setListener { p0, p1 ->
                    if (p0.responseCode == BillingClient.BillingResponseCode.OK && p1 != null) {
                        for (purchase in p1) {
                            handlePurchase(purchase)
                        }
                    } else if (p0.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                        // 处理由用户取消采购流引起的错误。
                        Logger.d("${p0.responseCode}:User cancel")
                        callBack?.builder?.error?.invoke(p0.responseCode, "User cancel")
                    } else {
                        Logger.d("${p0.responseCode}:User error")
                        callBack?.builder?.error?.invoke(p0.responseCode, "User error")
                    }
                }.build()
            billingClient?.startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    isConnect = false
                    callBack?.builder?.error?.invoke(Parameter.ABNORMAL_OPERATION, "Abnormal operation")
                }

                override fun onBillingSetupFinished(p0: BillingResult) {
                    if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
                        isConnect = true
                        Logger.d("Google payment link succeeded")
                    } else {
                        Logger.d("${p0.responseCode}")
                        callBack?.builder?.error?.invoke(p0.responseCode, "Link error")
                        isConnect = false
                    }
                }
            })
            hasInit = true
        }
    }

    /** 内购 */
    fun getSkuList(
        goodsId: String?,
        activity: Activity,
    ) {
        if (!isConnect) {
            throw RuntimeException("Please call the init method to initialize")
        }
        skuList.clear()
        skuList.add(goodsId.toString())
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient!!.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                if (skuDetailsList.size > 0) {
                    skuDetail = skuDetailsList[0]
                    /** todo 插入逻辑处理 */
                    //查到商品去支付
                    googlePay(activity)
                } else {
                    Logger.d("No product information found")
                    callBack?.builder?.error?.invoke(
                        Parameter.GOOGLE_PAY_ERROR,
                        "Current region does not support Google payments"
                    )
                }
            }
        }
    }

    /**
     * 是否链接成功
     * */
    fun isConnect() = isConnect

    /** 调用google 支付 */
    private fun googlePay(activity: Activity) {
        //预留order id
        //val mOrderId = mOrderId
        if (!isConnect) {
            throw RuntimeException("Please call the init method to initialize")
        }
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetail!!)
            .build()
        val responseCode = billingClient!!.launchBillingFlow(activity, flowParams).responseCode
        if (responseCode != 0) {
            Logger.d("$responseCode:Current region does not support Google payments")
            callBack?.builder?.error?.invoke(
                responseCode,
                "Current region does not support Google payments"
            )
        }
    }


    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // 授予用户权限
            // 如果尚未确认购买，请确认购买。
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                //注意这里通知方式分3种类型（消耗型、订阅型、奖励型），本文是消耗性产品的通知方式，其它方式请看官方文档
                billingClient!!.consumeAsync(
                    acknowledgePurchaseParams,
                    acknowledgePurchaseResponseListener
                )
            }
            val purchaseModel = PurchaseModel()
            purchaseModel.developerPayload = purchase.developerPayload
            purchaseModel.orderId = purchase.orderId
            purchaseModel.originalJson = purchase.originalJson
            purchaseModel.packageName = purchase.packageName
            purchaseModel.purchaseState = "${purchase.purchaseState}"
            purchaseModel.purchaseTime = "${purchase.purchaseTime}"
            purchaseModel.purchaseToken = purchase.purchaseToken
            purchaseModel.signature = purchase.signature
            purchaseModel.sku = purchase.skus[0]
            /** 返回调用者 */
            callBack?.builder?.success?.invoke(SuccessModel().apply {
                myPurchase = purchaseModel
            })
        }
    }

    private val acknowledgePurchaseResponseListener =
        ConsumeResponseListener { res, str ->
            Logger.d("acknowledge", "${res.responseCode} $str")
        }
}