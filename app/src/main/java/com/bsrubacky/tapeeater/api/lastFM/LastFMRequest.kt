package com.bsrubacky.tapeeater.api.lastFM

import com.bsrubacky.tapeeater.BuildConfig
import com.bsrubacky.tapeeater.api.ApiRequest
import retrofit2.Response
import java.security.MessageDigest

abstract class LastFMRequest<T>: ApiRequest<LastFM, T>() {

    override lateinit var api: LastFM
    protected val apiKey = BuildConfig.lastFM_apiKey
    protected val sharedSecret = BuildConfig.lastFM_sharedSecret
    protected val params = mutableMapOf(Pair("api_key",apiKey))

    fun sign(): String {
        params.toSortedMap()
        val signStringBuilder = StringBuilder()
        params.forEach { (key, value) ->
            signStringBuilder.append(key)
            signStringBuilder.append(value)
        }
        signStringBuilder.append(sharedSecret)
        val md5 = MessageDigest.getInstance("MD5")
        return md5.digest(signStringBuilder.toString().toByteArray()).toHexString()
    }
}