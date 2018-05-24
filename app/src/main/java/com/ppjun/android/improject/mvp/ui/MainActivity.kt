package com.ppjun.android.improject.mvp.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ppjun.android.improject.R
import com.ppjun.android.improject.mvp.contract.TokenContract
import com.ppjun.android.improject.mvp.presenter.TokenPresenter
import io.rong.imkit.RongIM
import io.rong.imlib.IRongCallback
import io.rong.imlib.ISendMessageCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.UserInfo
import io.rong.message.TextMessage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TokenContract.View {


    val name = "ppjun"
    val userId = "123456"
    val image="https://avatar.csdn.net/5/A/5/2_first_cooman.jpg"
    lateinit var mPresenter: TokenContract.Presenter
    override fun setToken(token: String) {
        Log.i("debug=", token)
        connect(token)
    }

    override fun setPresenter(t: TokenContract.Presenter) {
        mPresenter = t
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TokenPresenter(this)
        connectBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
            mPresenter.getToken(this@MainActivity, userId, name, image)
        }
        disconnectBtn.setOnClickListener {
            RongIM.getInstance().disconnect()
        }

        talkList.setOnClickListener {
            startConversationList()
        }
        talk.setOnClickListener {
            RongIM.getInstance().startPrivateChat(this@MainActivity, "123456", "faker")



        }
    }

    private fun getCurProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val mActivityManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager
                .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }


    private fun startConversationList() {
        val map = HashMap<String, Boolean>()
        map.put(Conversation.ConversationType.PRIVATE.getName(), false) // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
        map.put(Conversation.ConversationType.GROUP.getName(), false)  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示

        RongIM.getInstance().startConversationList(this@MainActivity, map)

        //startActivity(Intent(this@MainActivity, ConversationListActivity::class.java))
    }

    private fun connect(token: String) {

        if (applicationInfo.packageName == getCurProcessName(applicationContext)) {
            RongIM.connect(token, object : RongIMClient.ConnectCallback() {

                override fun onTokenIncorrect() {

                }

                override fun onSuccess(userid: String) {
                    progress.visibility = View.GONE
                    connectBtn.text = "欢迎回来$name$userId"


                    RongIM.getInstance().setCurrentUserInfo(UserInfo(userId,name, Uri.parse(image)))
                    RongIM.getInstance().setMessageAttachedUserInfo(true)
                    //用户内容提供者（userid+username+image）
                }

                override fun onError(errorCode: RongIMClient.ErrorCode) {
                }
            })
        }
    }
}
