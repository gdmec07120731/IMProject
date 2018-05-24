package com.ppjun.android.improject.app

import android.app.Application
import com.ppjun.android.improject.app.utils.Constant
import io.rong.imkit.RongIM

class BaseApp : Application() {


    override fun onCreate() {
        super.onCreate()
        try {
            RongIM.init(this,Constant.APP_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }


    }
}