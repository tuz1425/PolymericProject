# TestGoogleDemmo

# Google sign in 登录 
> 请调用 OverseasUtils.build().apply {
>  setReqListener(this@GoogleSignInActivity,
>     InformationModel(getString(R.string.default_web_client_id))
> )
> 但是需要注意 在 onActivityResult中调用  OverseasUtils.setActivityResult(requestCode, resultCode, data)
> R.string.default_web_client_id = 是通过firebase通过google-services.json 生成的