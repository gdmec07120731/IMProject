package com.ppjun.android.improject.mvp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ppjun.android.improject.R

class ConversationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_ui)
        title = intent.data.getQueryParameter("title")
    }

}