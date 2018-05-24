package com.ppjun.android.improject.mvp.ui

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ppjun.android.improject.R
import io.rong.imkit.RongIM
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation

class ConversationListActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conver_list_ui)

//
//        var listFragment = ConversationListFragment.instantiate(this, ConversationListFragment::class.java.name) as ConversationListFragment
//        var uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversationlist")
//                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
//                .build();
//        listFragment.setUri(uri)
//        var  transaction = supportFragmentManager.beginTransaction()
//        //将融云的Fragment界面加入到我们的页面。
//        transaction.add(R.id.conversationlist, listFragment)
//        transaction.commitAllowingStateLoss()
//        //为了更加直观，服务器建立连接后进入此界面，直接调用如下代码，执行单人聊天，第二个参数代表对方用户ID，第三个参数代表聊天窗口标题，为了方便测试聊天，需要两个手机测试，所以登陆第一个token的用户与第二个用户"chao"建立聊天，在运行第二个手机之前，记得改"chao"的token登录，然后聊天这里改为第一个的ID"text"。
//        RongIM.getInstance().startPrivateChat(this, "chao", "聊天中");

    }
}