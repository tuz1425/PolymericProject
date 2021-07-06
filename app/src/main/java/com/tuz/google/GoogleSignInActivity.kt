package com.tuz.polymeric

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import com.tuz.aggregatepayment.model.InformationModel
import com.tuz.aggregatepayment.toJson
import com.tuz.aggregatepayment.utils.GoogleSign
import com.tuz.aggregatepayment.utils.SignInUtils

/**
 *
 *@author lidexin
 *@email tuz1425@dingtalk.com
 *@date 2021/6/24
 */
class GoogleSignInActivity : AppCompatActivity() {
    private val tag = "GoogleActivity"
    private var googleBuild: GoogleSign? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.google_sigin_ac)

        googleBuild = SignInUtils.googleBuild().apply {
            setReqListener(this@GoogleSignInActivity,
                InformationModel(getString(R.string.default_web_client_id))
            ) {
                success {
                    Log.d(tag,"success 通过dsl回调更新ui")
                    updateUI(it?.googleUser)
                }
                error { i, s ->
                    Log.d(tag,"error $i + $s")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleBuild!!.setActivityResult(requestCode, resultCode, data)
    }

    private fun updateUI(user: FirebaseUser?) {
        /**
         *
         * {
                "zza":{
                "zzb":"AGEhc0C0I94wo73185a9vVnB4iIE4hcPXW_uXB5KFtsKKXyeRWTq6WemG2aDiuZ4l4tjkDop7UINAEid_jkCqGvE0O7FyFEiQCBK_ucFpUTFHWwMgw0GEfye-cUmEH9RwEsDCusftDvXyReiAWEMb294kew2E83RRj0LpBx24ICL2Vcgsm08p0b3Aq6yszu7tJci9ZI-oAafGemsQXMCaNbZMzo8fcY1jsgI4IKN9d8_55jeSFtq5xvxOMWE6inF6oZTd5qBQTTVXh7gOxMeImg41Fff0c42Z56Ka2-Qq6BW-If6ACLln9Wfafo8ZGxGo1n2cbjWtSJNl-HYFyvW6fViqDsUvVKWhorEvc8QasXW6x-rb0_5ChPnFGZKzGrCOaWxTmEqypeEJ16LDW-pE3bm-ZYEUTHtKdx5xg05uGQYEvqhvq5_JJM",
                "zzc":"eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg4ZGYxMzgwM2I3NDM2NjExYWQ0ODE0NmE4ZGExYjA3MTg2ZmQxZTkiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoi5p2O5b635pawIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdnakJDS0JBUEpSZ2ZPZTV5cXBsaGhUUk1BR19IYnloSVBCMTdiMz1zOTYtYyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9wdXJlLXF1YXNhci0zMTc4MDgiLCJhdWQiOiJwdXJlLXF1YXNhci0zMTc4MDgiLCJhdXRoX3RpbWUiOjE2MjQ1Mjk3ODYsInVzZXJfaWQiOiJxb2lmWWFLcjZJUlQzbE5oMTJ3T2pCSWlhSjIzIiwic3ViIjoicW9pZllhS3I2SVJUM2xOaDEyd09qQklpYUoyMyIsImlhdCI6MTYyNDUyOTc4NiwiZXhwIjoxNjI0NTMzMzg2LCJlbWFpbCI6ImR4MTI0NDMwMDc5NkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjExMTgxNTc5MTY5MzYyODQyNDQ5OCJdLCJlbWFpbCI6WyJkeDEyNDQzMDA3OTZAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.DOtek44kpZfgULY2A0QsdGRRuhIH5XWWi3-t2682gGJf1iB5Irr7vo79VV_7IheupOZO8Ys3kxvWRjVQCl1FFi7vGkRqfo5CXFOyB017wlHoLyF1VL9YGdoB3vwi_glXqWqgQKMKTx4nGJuGHjvc8rugoI9l_Y0YBngSYWhs61OJVMTe3zZ6KsOsNj_sDPJGz-7WQxB4E1vDSP5PxabijcXmzKbw7hmjJEBxn-6Pv_X0lA1SzSKp7mtMqQYSxLjJOM8t1Bx8Fz2ZyMg0_TUIibCgC5Qdzgl4Gt26LBC0CW2hA9V9TyuMi3Q1rwwd_2QBpcjKSXxZifZ4gpAxgkbXyA",
                "zzd":3600,
                "zze":"Bearer",
                "zzf":1624529785886
                },
                "zzb":{
                "zza":"qoifYaKr6IRT3lNh12wOjBIiaJ23",
                "zzb":"firebase",
                "zzc":"李德新",
                "zzd":"https://lh3.googleusercontent.com/a-/AOh14GgjBCKBAPJRgfOe5yqplhhTRMAG_HbyhIPB17b3\u003ds96-c",
                "zze":{

                },
                "zzf":"dx1244300796@gmail.com",
                "zzg":"",
                "zzh":true,
                "zzi":""
                },
                "zzc":"[DEFAULT]",
                "zzd":"com.google.firebase.auth.internal.DefaultFirebaseUser",
                "zze":[
                {
                "zza":"qoifYaKr6IRT3lNh12wOjBIiaJ23",
                "zzb":"firebase",
                "zzc":"李德新",
                "zzd":"https://lh3.googleusercontent.com/a-/AOh14GgjBCKBAPJRgfOe5yqplhhTRMAG_HbyhIPB17b3\u003ds96-c",
                "zze":{

                },
                "zzf":"dx1244300796@gmail.com",
                "zzg":"",
                "zzh":true,
                "zzi":""
                },
                {
                "zza":"111815791693628424498",
                "zzb":"google.com",
                "zzc":"李德新",
                "zzd":"https://lh3.googleusercontent.com/a-/AOh14GgjBCKBAPJRgfOe5yqplhhTRMAG_HbyhIPB17b3\u003ds96-c",
                "zze":{

                },
                "zzf":"dx1244300796@gmail.com",
                "zzg":"",
                "zzh":false,
                "zzi":"{\"iss\":\"https://accounts.google.com\",\"azp\":\"790739313549-t94bqgor2g003s6ua29g0577tr4d6k6k.apps.googleusercontent.com\",\"aud\":\"790739313549-0m66gj1gada0bl8s3a7m9rr5eoamcoe5.apps.googleusercontent.com\",\"sub\":\"111815791693628424498\",\"email\":\"dx1244300796@gmail.com\",\"email_verified\":true,\"name\":\"李德新\",\"picture\":\"https://lh3.googleusercontent.com/a-/AOh14GgjBCKBAPJRgfOe5yqplhhTRMAG_HbyhIPB17b3\u003ds96-c\",\"given_name\":\"德新\",\"family_name\":\"李\",\"locale\":\"zh-CN\",\"iat\":1624529781,\"exp\":1624533381}"
                }
                ],
                "zzf":[
                "google.com"
                ],
                "zzg":"2",
                "zzh":false,
                "zzi":{
                "zza":1624529786786,
                "zzb":1624529449579
                },
                "zzj":false
             }
         *
         *
         *
         * */

        Log.d(tag, "updateUI ${user?.toJson()}")
    }
}