# <h1>接入注意事项</h1>
# <h3>Google sign in</h3> 
> 请调用 OverseasUtils.build().apply {
>  setReqListener(this@GoogleSignInActivity,
>     InformationModel(getString(R.string.default_web_client_id)))</br>
> 但是需要注意 在 onActivityResult中调用  OverseasUtils.setActivityResult(requestCode, resultCode, data)</br>
> R.string.default_web_client_id = 是通过firebase通过google-services.json 生成的

# <h3>faceBook sign in</h3> 
> 调用faceBuild = OverseasUtils.faceBuild().apply {
         setReqListener(
          this@FaceBookSignActivity,
          InformationModel(faceLoginButton = findViewById(R.id.face_book_sign_in)))</br>
> 注意findViewById(R.id.face_book_sign_in) = 在xml中写好<com.facebook.login.widget.LoginButton></br>
> 在onActivityResult中调用  faceBuild.setActivityResult(requestCode, resultCode, data)

# <h3>google 自定义事件</h3> 
> 调用GoogleAnalyticsUtils.init(this) 方法。</br>
> val hashmap = hashMapOf<String,String>().apply {
                 put("user", "10001")
                 put("content", "test")
                 put("type", "image")
             }</br>
> GoogleAnalyticsUtils.statistics(keyString = "testKey", hashmap)</br>
> 调用如上方法，注意keyString的长度不能小于1不能超过40字符长度</br>
> 过滤log tag = GoogleAnalyticsUtils 能看见输出日志

# <h3>code 参考  Parameter.kt</h3> 

# <h3>RequestListener</h3>
> 接口,Builder内,不用像“interface”全部重写,按需求复写方法.

# <h3>接入之前准备的事项</h3>
# <h4>google-services.json获取</h4>
> https://console.firebase.google.com/ 配置好内容。获取到.json文件放在app文件夹下</br>
# <h4>project---gradle、app---gradle</h4>
> 分别配置:</br>
> :classpath 'com.google.gms:google-services:xxx'</br>
> :id 'com.google.gms.google-services'
# <h4>app---清单文件，需要配置firebase文档中内容</h4>


