package com.tuz.aggregatepayment.model

import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.auth.MultiFactor
import com.google.firebase.auth.UserInfo

/**
 *二次封装FirebaseUser
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/7/6
 */
class UserInfoModel(firUser: FirebaseUser?) {

    val photoUrl: Uri? = firUser?.photoUrl
    val metadata: FirebaseUserMetadata? = firUser?.metadata
    val multiFactor: MultiFactor? = firUser?.multiFactor
    val displayName: String? = firUser?.displayName
    val email: String? = firUser?.email
    val phoneNumber: String? = firUser?.phoneNumber
    val providerId: String? = firUser?.providerId
    val tenantId: String? = firUser?.tenantId
    val uid: String = firUser?.uid ?: ""
    val providerData: List<UserInfo>? = firUser?.providerData
    val anonymous: Boolean? = firUser?.isAnonymous
    val zza: FirebaseApp? = firUser?.zza()
    val zzb: FirebaseUser? = firUser?.zzb()

    //val zzd = firUser.zzd()
    val zze: String? = firUser?.zze()
    val zzf: String? = firUser?.zzf()
    val zzg: List<String>? = firUser?.zzg()

}