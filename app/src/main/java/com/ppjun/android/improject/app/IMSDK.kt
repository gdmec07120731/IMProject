package com.ppjun.android.improject.app

import com.ppjun.android.improject.mvp.model.api.Api
import com.ppjun.android.improject.mvp.model.api.MainService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class IMSDK {


    private constructor()

    companion object {
        private   var mInstance: IMSDK?=null
        fun getInstance(): IMSDK {

            if (mInstance == null) {
                synchronized(IMSDK::class.java) {
                    if (mInstance == null) {
                        mInstance = IMSDK()
                    }
                }
            }
            return mInstance!!
        }

    }


    fun getServer(): MainService {
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(Api.DOMAIN_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(MainService::class.java)
    }
}