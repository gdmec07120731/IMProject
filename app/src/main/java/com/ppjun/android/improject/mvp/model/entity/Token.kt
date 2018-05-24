package com.ppjun.android.improject.mvp.model.entity
import com.google.gson.annotations.SerializedName


data class Token(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("userId") val userId: String = "",
    @SerializedName("token") val token: String = ""



)