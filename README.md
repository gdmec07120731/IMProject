# IMProject
融云集成Android端


在初始化的融云时候，出现异常libsqlite.so找不到，解决方法添加对应cpu架构的so包，详情通过链接了解一下http://support.rongcloud.cn/kb/NTQw

#### 接入融云
融云SDK满足 App 内的社交沟通需求，提供单群聊、超级群等多种聊天模式，支持红包、图片、语音和小视频，实时消息推送，高度自定义界面，高清音视频通话，有效提升用户粘性和活跃度，所以我用融云。

#### 添加应用
要接入第三方 SDK 都要在其开发者平台注册账号，添加应用，审核获取 appkey secretkey。这样子的流程。然后去看他的 SDK 接入指南。

#### 开始
下载融云 SDK，看你功能需要选择下载。
http://www.rongcloud.cn/downloads

新建项目,引入融云远程库，加入第三方库
```grovvy
    implementation 'cn.rongcloud.android:IMKit:2.8.6'
    implementation 'cn.rongcloud.android:IMLib:2.8.6'
```
把 下载好的 SDK 中 libs 里面的文件加入到项目lib里面（armeabi里面的libpush.so
以及libsqlite.so)

新建src/main/assets/ 并加入 SDK 里面的 armeabi/push_deamon 

Androidmanifest.xml
加入权限
```xml
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <!-- 获取机型信息权限 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL" />

    <!-- 查看 Wi-Fi 状态 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- 查看网络状态 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <!-- 录音 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <!-- 控制振动器 -->
    <uses-permission android:name="android.permission.VIBRATE" />
    <!-- 防止设备休眠 -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />

    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
    <!-- 获取联系人信息，demo 中演示发送通讯录消息 -->
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

    <!-- ⼩米 配置开始 < -->
    <permission
        android:name="cn.rongcloud.im.permission.MIPUSH_RECEIVE"
        android:protectionLevel="signature" />
    <uses-permission android:name="cn.rongcloud.im.permission.MIPUSH_RECEIVE" />
    <!-- ⼩米 配置结束 < -->
    <!-- GCM 配置开始 < -->
    <permission
        android:name="cn.rongcloud.im.permission.C2D_MESSAGE"
        android:protectionLevel="signature" />
    <uses-permission android:name="cn.rongcloud.im.permission.C2D_MESSAGE" />
    <!-- GCM 配置结束 < -->
    <!-- 华为 配置开始 < -->
    <!--HMS-SDK 引导升级 HMS 功能，访问 OTA 服务器需要网络权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!--HMS-SDK 引导升级 HMS 功能，保存下载的升级包需要 SD 卡写权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!-- 检测网络状态 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!-- 检测 wifi 状态 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- 为了获取用户手机的 IMEI，用来唯一的标识用户。-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

    <!-- 如果是安卓 8.0，应用编译配置的 targetSdkVersion>=26，请务必添加以下权限 -->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    <!-- 华为 配置结束 < -->

    <!-- MeiZu 配置开始 < -->
    <!-- 兼容 flyme5.0 以下版本，魅族内部集成 pushSDK 必填，不然无法收到 消息 -->
    <uses-permission
        android:name="com.meizu.flyme.push.permission.RECEIVE"/>
    <permission
        android:name="cn.rongcloud.im.push.permission.MESSAGE"
        android:protectionLevel="signature"/>
    <uses-permission android:name="cn.rongcloud.im.push.permission.MESSAGE"/>
    <!-- 兼容 flyme3.0 配置权限 -->
    <uses-permission android:name="com.meizu.c2dm.permission.RECEIVE" />
    <!-- MeiZu 配置结束 < -->
    
```

新建 Application 在 oncreate 初始化 sdk
因为接入了融云 SDK会有主进程，和推送进程，那我们在主进程进行初始化
```kotlin
    if (applicationInfo.packageName == getCurProcessName(applicationContext)) {
        try {
            RongIM.init(this,Constant.APP_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }
        }
 ```

 然后调用 RongIM.connect，在成功的回调进行内容提供者（设置头像和昵称），token要在后台获取，测试的时候你可以在 应用里面获取 token 
```kotlin
   RongIM.connect(token, object : RongIMClient.ConnectCallback() {
                override fun onTokenIncorrect() {
                    //get token again
                }
                override fun onSuccess(userid: String) {
                    progress.visibility = View.GONE
                    connectBtn.text = "欢迎回来$name$userId"
                    RongIM.getInstance().setCurrentUserInfo(UserInfo(userId,name, Uri.parse(image)))
                    RongIM.getInstance().setMessageAttachedUserInfo(true)
                    //用户内容提供者（userid+username+image）
                }
                override fun onError(errorCode: RongIMClient.ErrorCode) {
                    //Ooooop
                }
            }

```
#### 创建会话列表
创建ConversationListActivity ，在布局文件加入
```xml
  <fragment
        android:id="@+id/conversationlist"
        android:name="io.rong.imkit.fragment.ConversationListFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
再注册这个activity，因为是隐式跳转这个 activity 注意 data 的 host 是写自己包名
```xml
<activity android:name="com.ppjun.android.improject.mvp.ui.ConversationListActivity"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="stateHidden|adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data
                    android:host="com.ppjun.android.improject"
                    android:pathPrefix="/conversationlist"
                    android:scheme="rong" />
            </intent-filter>
        </activity>
```

#### 创建聚合会话列表
创建 SubConversationListActivity ,在其布局文件加入
```xml
    <fragment
        android:id="@+id/subconversationlist"
        android:name="io.rong.imkit.fragment.SubConversationListFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

```
再注册这个activity，因为是隐式跳转这个 activity 注意 data 的 host 是写自己包名
```xml
  <!--聚合会话列表-->
        <activity
            android:name="com.ppjun.android.improject.mvp.ui.SubConversationListActivity"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="stateHidden|adjustResize">

            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />

                <data
                    android:host="com.ppjun.android.improject"
                    android:pathPrefix="/subconversationlist"
                    android:scheme="rong" />
            </intent-filter>
        </activity>

```

#### 创建会话
创建 ConversationActivity ,在其布局文件加入
```xml
  <fragment
        android:id="@+id/conversation"
        android:name="io.rong.imkit.fragment.ConversationFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
再注册这个activity，因为是隐式跳转这个 activity 注意 data 的 host 是写自己包名
```xml
   <!--会话界面-->
        <activity
            android:name="com.ppjun.android.improject.mvp.ui.ConversationActivity"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="stateHidden|adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />

                <data
                    android:host="com.ppjun.android.improject"
                    android:pathPrefix="/conversation/"
                    android:scheme="rong" />
            </intent-filter>
        </activity>
```

#### 打开会话列表
```kotlin
  private fun startConversationList() {
        val map = HashMap<String, Boolean>()
        map.put(Conversation.ConversationType.PRIVATE.getName(), false) // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
        map.put(Conversation.ConversationType.GROUP.getName(), false)  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示
        RongIM.getInstance().startConversationList(this@MainActivity, map)
    }
```    
#### 创建私聊会话
```kotlin
     RongIM.getInstance().startPrivateChat(this@MainActivity, "123456", "userid")

```
项目地址 [https://github.com/gdmec07120731/IMProject](https://github.com/gdmec07120731/IMProject)

#### 常见问题
connect 无回调，报找不到 libsqlite.so 异常
解决方法 [http://support.rongcloud.cn/kb/NTQw](http://support.rongcloud.cn/kb/NTQw)


