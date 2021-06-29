/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.aggregatepayment

import com.google.android.gms.wallet.WalletConstants

/**
 * This file contains several constants you must edit before proceeding.
 * Please take a look at PaymentsUtil.java to see where the constants are used and to potentially
 * remove ones not relevant to your integration.
 *
 *
 * Required changes:
 *
 *  1.  Update SUPPORTED_NETWORKS and SUPPORTED_METHODS if required (consult your processor if
 * unsure)
 *  1.  Update CURRENCY_CODE to the currency you use.
 *  1.  Update SHIPPING_SUPPORTED_COUNTRIES to list the countries where you currently ship. If this
 * is not applicable to your app, remove the relevant bits from PaymentsUtil.java.
 *  1.  If you're integrating with your `PAYMENT_GATEWAY`, update
 * PAYMENT_GATEWAY_TOKENIZATION_NAME and PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS per the
 * instructions they provided. You don't need to update DIRECT_TOKENIZATION_PUBLIC_KEY.
 *  1.  If you're using `DIRECT` integration, please edit protocol version and public key as
 * per the instructions.
 *
 * * 此文件包含您必须在继续之前编辑的几个常量。
 * 请查看 PaymentsUtil.java 以了解常量的使用位置以及潜在的
 * 删除与您的集成无关的内容。
 *
 *
 * 所需更改：
 *
 * 1. 如果需要，请更新 SUPPORTED_NETWORKS 和 SUPPORTED_METHODS（如果需要，请咨询您的处理器
 *不确定）
 * 1. 将 CURRENCY_CODE 更新为您使用的货币。
 * 1. 更新 SHIPPING_SUPPORTED_COUNTRIES 以列出您当前发货的国家/地区。 如果这
 * 不适用于您的应用程序，请从 PaymentsUtil.java 中删除相关位。
 * 1. 如果您正在与您的`PAYMENT_GATEWAY` 集成，请更新
 * PAYMENT_GATEWAY_TOKENIZATION_NAME 和 PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS 每
 * 他们提供的说明。 您不需要更新 DIRECT_TOKENIZATION_PUBLIC_KEY。
 * 1.如果您使用`DIRECT`集成，请编辑协议版本和公钥为
 * 根据说明。
 *
 */
object Constants {
    /**
     * Changing this to ENVIRONMENT_PRODUCTION will make the API return chargeable card information.
     * Please refer to the documentation to read about the required steps needed to enable
     * ENVIRONMENT_PRODUCTION.
     *
     * @value #PAYMENTS_ENVIRONMENT
     */
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    /**
     * The allowed networks to be requested from the API. If the user has cards from networks not
     * specified here in their account, these will not be offered for them to choose in the popup.
     *
     * @value #SUPPORTED_NETWORKS
     */
    val SUPPORTED_NETWORKS = listOf(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA")

    /**
     * The Google Pay API may return cards on file on Google.com (PAN_ONLY) and/or a device token on
     * an Android device authenticated with a 3-D Secure cryptogram (CRYPTOGRAM_3DS).
     *
     * @value #SUPPORTED_METHODS
     */
    val SUPPORTED_METHODS = listOf(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS")

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #COUNTRY_CODE Your local country
     */
    const val COUNTRY_CODE = "US"

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #CURRENCY_CODE Your local currency
     */
    const val CURRENCY_CODE = "USD"

    /**
     * Supported countries for shipping (use ISO 3166-1 alpha-2 country codes). Relevant only when
     * requesting a shipping address.
     *
     * @value #SHIPPING_SUPPORTED_COUNTRIES
     */
    val SHIPPING_SUPPORTED_COUNTRIES = listOf("US", "GB")

    /**
     * The name of your payment processor/gateway. Please refer to their documentation for more
     * information.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_NAME
     */
    const val PAYMENT_GATEWAY_TOKENIZATION_NAME = "example"

    /**
     * Custom parameters required by the processor/gateway.
     * In many cases, your processor / gateway will only require a gatewayMerchantId.
     * Please refer to your processor's documentation for more information. The number of parameters
     * required and their names vary depending on the processor.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS
     */
    val PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = mapOf(
            "gateway" to PAYMENT_GATEWAY_TOKENIZATION_NAME,
            "gatewayMerchantId" to "exampleGatewayMerchantId"
    )

    /**
     * Only used for `DIRECT` tokenization. Can be removed when using `PAYMENT_GATEWAY`
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PUBLIC_KEY
     */
    const val DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME"

    /**
     * Parameters required for `DIRECT` tokenization.
     * Only used for `DIRECT` tokenization. Can be removed when using `PAYMENT_GATEWAY`
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PARAMETERS
     */
    val DIRECT_TOKENIZATION_PARAMETERS = mapOf(
            "protocolVersion" to "ECv1",
            "publicKey" to DIRECT_TOKENIZATION_PUBLIC_KEY
    )
}
