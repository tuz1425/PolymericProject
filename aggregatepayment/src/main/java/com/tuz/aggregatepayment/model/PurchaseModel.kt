package com.tuz.aggregatepayment.model

/**
 *封装google 支付返回结果实体类
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/29
 */
class PurchaseModel {
    var developerPayload = ""
    var orderId = ""
    var originalJson = ""
    var packageName = ""
    var purchaseState = ""
    var purchaseTime = ""
    var purchaseToken = ""
    var signature = ""
    var sku: String = ""
}