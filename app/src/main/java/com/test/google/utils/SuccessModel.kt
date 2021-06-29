package com.test.google.utils

import com.android.billingclient.api.Purchase
import com.google.firebase.auth.FirebaseUser
import com.test.google.model.PurchaseModel

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/28
 * @param googleUser google sign in
 * @param faceUser face book sign in
 * @param purchase google pay in app pay
 */
class SuccessModel(var googleUser: FirebaseUser? = null, var faceUser: FirebaseUser? = null,val purchase: PurchaseModel? = null) {
}