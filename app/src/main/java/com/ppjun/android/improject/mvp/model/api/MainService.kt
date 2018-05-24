package com.ppjun.android.improject.mvp.model.api

import com.ppjun.android.improject.mvp.model.entity.Token
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface MainService {

    @POST(Api.TOKEN)
    @FormUrlEncoded
    fun token(@HeaderMap headerMap: Map<String, String> , @FieldMap map: Map<String, String>): Observable<Token>

}