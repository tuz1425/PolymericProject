package com.test.aggregatepayment.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.test.aggregatepayment.GooglePaymentsUtil
import com.test.aggregatepayment.microsToString
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToLong

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
@SuppressLint("StaticFieldLeak")
object GoogleOutsourcingUtils {
    private const val TAG = "GoogleOutsourcingUtils"
    private lateinit var paymentsClient: PaymentsClient
    private const val shippingCost = (90 * 1000000).toLong()
    private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    /** 监听成功失败 */
    private var callBack: RequestListener? = null

    /** 是否初始化过 */
    private var hasInit: Boolean = false

    private var mContext: Activity? = null
    private var mButton: View? = null


    fun init(context: Activity, button: View,listener: RequestListener.Builder.() -> Unit) {
        if (!hasInit) {
            /** 初始化接口 */
            callBack = RequestListener().apply {
                registerListener(listener)
            }
            this.mContext = context
            this.mButton = button
            possiblyShowGooglePayButton()
        }
    }

    /** 通过回调的方式完成检查 */
    fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }
                    Activity.RESULT_CANCELED -> {
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            handleError(it.statusCode)
                        }
                    }
                }
                mButton?.isClickable = true
            }
        }
    }

    /**
     * 检查google 支付是否可用
     * */
    private fun possiblyShowGooglePayButton() {
        val isReadyToPayJson = GooglePaymentsUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                Log.d("isReadyToPay failed", exception.toString())
            }
        }
    }

    /**
     * 支付按钮是否显示
     * */
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            mButton?.visibility = View.VISIBLE
        } else {
            mButton?.visibility = View.GONE
            Toast.makeText(mContext, "该设备google支付不可用", Toast.LENGTH_LONG).show()
            //todo 回调状态
        }
    }

    /**
     * 支付
     * */
    private fun requestPayment() {
        // Disables the button to prevent multiple clicks.
        mButton?.isClickable = false
        val garmentPriceMicros = (10.0 * 1000000).roundToLong()
        val price = (garmentPriceMicros + shippingCost).microsToString()

        val paymentDataRequestJson = GooglePaymentsUtil.getPaymentDataRequest(price)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "无法获取支付数据")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        //由于loadPaymentData可能会显示用户界面，要求用户选择付款方式，因此我们使用
        //AutoResolveHelper等待用户与其交互。一旦完成，
        //onActivityResult将与结果一起调用。
        if (request != null) {
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request), mContext!!, LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson() ?: return

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("type") == "PAYMENT_GATEWAY" && paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token") == "examplePaymentMethodToken"
            ) {

                AlertDialog.Builder(mContext)
                    .setTitle("Warning")
                    .setMessage(
                        "Gateway name set to \"example\" - please modify " +
                                "Constants.java and replace it with your own gateway."
                    )
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)

            //Toast.makeText(
            //    this,
            //    getString(R.string.payments_show_name, billingName),
            //    Toast.LENGTH_LONG
            //).show()

            // Logging token string.
            Log.d(
                "GooglePaymentToken", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )

        } catch (e: JSONException) {
            Log.e("handlePaymentSuccess", "Error: $e")
        }
    }

    private fun handleError(statusCode: Int) {
        Log.w(TAG, String.format("Error code: %d", statusCode))
    }


}