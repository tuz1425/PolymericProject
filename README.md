# 接入注意事项

# Google sign in 
> 请调用 OverseasUtils.build().apply {
>  setReqListener(this@GoogleSignInActivity,
>     InformationModel(getString(R.string.default_web_client_id))
> )
> 但是需要注意 在 onActivityResult中调用  OverseasUtils.setActivityResult(requestCode, resultCode, data)
> R.string.default_web_client_id = 是通过firebase通过google-services.json 生成的

# faceBook sign in 
> 调用faceBuild = OverseasUtils.faceBuild().apply {
         setReqListener(
          this@FaceBookSignActivity,
          InformationModel(faceLoginButton = findViewById(R.id.face_book_sign_in))
         )
> 注意findViewById(R.id.face_book_sign_in) = 在xml中写好<com.facebook.login.widget.LoginButton>
>在 onActivityResult中调用  faceBuild.setActivityResult(requestCode, resultCode, data)

# google 自定义事件
> 调用GoogleAnalyticsUtils.init(this) 方法。
> val hashmap = hashMapOf<String,String>().apply {
                 put("user", "10001")
                 put("content", "test")
                 put("type", "image")
             }
> GoogleAnalyticsUtils.statistics(keyString = "testKey", hashmap)
>调用如上方法 注意keyString的长度不能小于1不能超过40字符长度