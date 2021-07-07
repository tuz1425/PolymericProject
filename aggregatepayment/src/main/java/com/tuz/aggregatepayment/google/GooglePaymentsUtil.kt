package com.tuz.aggregatepayment.google

import android.app.Activity
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.tuz.aggregatepayment.utils.Constants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *@linked https://developers.google.com/pay/api/android/guides/tutorial?hl=zh-cn
 * 参考链接
 * google 支付类
 */
object GooglePaymentsUtil {
    val MICROS = BigDecimal(1000000.0)

    private val baseRequest = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
    }

    private fun gatewayTokenizationSpecification(): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject(Constants.PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS))
        }
    }

    private fun directTokenizationSpecification(): JSONObject {
        if (Constants.DIRECT_TOKENIZATION_PUBLIC_KEY == "REPLACE_ME" ||
            (Constants.DIRECT_TOKENIZATION_PARAMETERS.isEmpty() ||
                    Constants.DIRECT_TOKENIZATION_PUBLIC_KEY.isEmpty())
        ) {

            throw RuntimeException(
                "Please edit the Constants.java file to add protocol version & public key."
            )
        }

        return JSONObject().apply {
            put("type", "DIRECT")
            put("parameters", JSONObject(Constants.DIRECT_TOKENIZATION_PARAMETERS))
        }
    }

    private val allowedCardNetworks = JSONArray(Constants.SUPPORTED_NETWORKS)


    private val allowedCardAuthMethods = JSONArray(Constants.SUPPORTED_METHODS)


    // Optionally, you can add billing address/phone number associated with a CARD payment method.
    private fun baseCardPaymentMethod(): JSONObject {
        return JSONObject().apply {

            val parameters = JSONObject().apply {
                put("allowedAuthMethods", allowedCardAuthMethods)
                put("allowedCardNetworks", allowedCardNetworks)
                put("billingAddressRequired", true)
                put("billingAddressParameters", JSONObject().apply {
                    put("format", "FULL")
                })
            }

            put("type", "CARD")
            put("parameters", parameters)
        }
    }


    private fun cardPaymentMethod(): JSONObject {
        val cardPaymentMethod = baseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", gatewayTokenizationSpecification())

        return cardPaymentMethod
    }


    fun isReadyToPayRequest(): JSONObject? {
        return try {
            val isReadyToPayRequest = JSONObject(baseRequest.toString())
            isReadyToPayRequest.put(
                "allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod())
            )

            isReadyToPayRequest

        } catch (e: JSONException) {
            null
        }
    }


    private val merchantInfo: JSONObject
        @Throws(JSONException::class)
        get() = JSONObject().put("merchantName", "Example Merchant")


    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(Constants.PAYMENTS_ENVIRONMENT)
            .build()

        return Wallet.getPaymentsClient(activity, walletOptions)
    }


    @Throws(JSONException::class)
    private fun getTransactionInfo(price: String): JSONObject {
        return JSONObject().apply {
            put("totalPrice", price)
            put("totalPriceStatus", "FINAL")
            put("countryCode", Constants.COUNTRY_CODE)
            put("currencyCode", Constants.CURRENCY_CODE)
        }
    }


    fun getPaymentDataRequest(price: String): JSONObject? {
        try {
            return JSONObject(baseRequest.toString()).apply {
                put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod()))
                put("transactionInfo", getTransactionInfo(price))
                put("merchantInfo", merchantInfo)

                // An optional shipping address requirement is a top-level property of the
                // PaymentDataRequest JSON object.
                val shippingAddressParameters = JSONObject().apply {
                    put("phoneNumberRequired", false)
                    put("allowedCountryCodes", JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES))
                }
                put("shippingAddressRequired", true)
                put("shippingAddressParameters", shippingAddressParameters)
            }
        } catch (e: JSONException) {
            return null
        }

    }
}


fun Long.microsToString() = BigDecimal(this)
    .divide(GooglePaymentsUtil.MICROS)
    .setScale(2, RoundingMode.HALF_EVEN)
    .toString()
