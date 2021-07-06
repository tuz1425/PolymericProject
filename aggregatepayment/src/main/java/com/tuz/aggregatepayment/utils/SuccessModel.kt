package com.tuz.aggregatepayment.utils

import com.google.firebase.auth.FirebaseUser
import com.tuz.aggregatepayment.model.PurchaseModel
import com.tuz.aggregatepayment.model.UserInfoModel

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/28
 * @param googleUser google sign in
 * @param faceUser face book sign in
 * @param purchase google pay in app pay
 */
class SuccessModel(val firebaseUser: FirebaseUser? = null) {
    var googleUser = UserInfoModel(firebaseUser)
    var faceUser = UserInfoModel(firebaseUser)
    var myPurchase: PurchaseModel? = null
}