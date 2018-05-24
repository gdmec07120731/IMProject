package com.ppjun.android.improject.mvp.model

import android.content.Context
import android.util.Log
import com.ppjun.android.improject.app.IMSDK
import com.ppjun.android.improject.app.utils.RxUtil
import com.ppjun.android.improject.app.utils.Sha1Util
import com.ppjun.android.improject.mvp.contract.TokenContract
import java.util.*

class TokenModel : TokenContract.Model {

    override fun getToken(context: Context, userId: String, name: String, portraitUrl: String, listener: OnTokenListener) {
        val map = HashMap<String, String>()
        val time = Date().time.toString()
        val nonce = getRandom()
        Log.i("debug=", time)
        Log.i("debug=", nonce)
        val headerMap = HashMap<String, String>()
        headerMap["App-Key"] = "kj7swf8ok1pk2"
        headerMap["Nonce"] = nonce
        headerMap["Timestamp"] = time
        headerMap["Signature"] = Sha1Util.encode("dsW0ksAcuV$nonce$time")
        map["userId"] = userId
        map["name"] = name
        map["portraitUrl"] = portraitUrl
        IMSDK.getInstance().getServer().token(headerMap, map)
                .compose(RxUtil.applyMainSchedulers())
                .subscribe({ result ->
                    if (result.code == 200) {
                        listener.onSuccess(result.token)
                    } else {
                        listener.onSuccess("")
                    }


                }, { t: Throwable? -> Log.i("debug", t!!.message) })
    }

    interface OnTokenListener {
        fun onSuccess(token: String)
        fun onFailure(msg: String)
    }


    fun getRandom(): String {
        val sources = "0123456789abcd"
        val rand = Random()
        val flag = StringBuffer()
        for (j in 0..9) {
            flag.append(sources[rand.nextInt(9)] + "")
        }
        return flag.toString()
    }
}