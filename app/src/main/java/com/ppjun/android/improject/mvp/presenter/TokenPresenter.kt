package com.ppjun.android.improject.mvp.presenter

import android.content.Context
import com.ppjun.android.improject.mvp.contract.TokenContract
import com.ppjun.android.improject.mvp.model.TokenModel

class TokenPresenter(var view:TokenContract.View) :TokenContract.Presenter{


    var model=TokenModel()
    init {
        view.setPresenter(this)
    }

    override fun getToken(context: Context, userId: String, name: String, portraitUrl: String) {
        model.getToken(context,userId,name,portraitUrl,object:TokenModel.OnTokenListener{
            override fun onSuccess(token: String) {
                 view.setToken(token)
            }

            override fun onFailure(msg: String) {
            }

        })
    }
}