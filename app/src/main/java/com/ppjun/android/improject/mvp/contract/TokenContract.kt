package com.ppjun.android.improject.mvp.contract

import android.content.Context
import com.ppjun.android.improject.app.BaseView
import com.ppjun.android.improject.mvp.model.TokenModel

class TokenContract {

    interface View : BaseView<Presenter> {
        fun setToken(token: String)
    }

    interface Presenter {
        fun getToken(context: Context, userId: String, name: String, portraitUrl: String)

    }

    interface Model {
        fun getToken(context: Context,userId: String, name: String, portraitUrl: String, listener: TokenModel.OnTokenListener)
    }
}