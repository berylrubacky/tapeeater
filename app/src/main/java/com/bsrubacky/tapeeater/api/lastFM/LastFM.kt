package com.bsrubacky.tapeeater.api.lastFM

import com.bsrubacky.tapeeater.api.lastFM.responses.AuthResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LastFM {

    @FormUrlEncoded
    @POST("/")
    suspend fun authentication(@FieldMap field: MutableMap<String, String>): Response<AuthResponse>

    @FormUrlEncoded
    @POST("/")
    suspend fun scrobble(@FieldMap field: MutableMap<String, String>)
}