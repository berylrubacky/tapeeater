package com.bsrubacky.tapeeater.api.lastFM

import com.bsrubacky.tapeeater.BuildConfig
import retrofit2.Response
import java.security.MessageDigest

abstract class LastFMRequest<T> {
    abstract val name: String

    lateinit var api: LastFM
    protected val apiKey = BuildConfig.lastFM_apiKey
    protected val sharedSecret = BuildConfig.lastFM_sharedSecret
    protected val params = mutableMapOf(Pair("api_key",apiKey))


    protected abstract suspend fun call(): Response<T>

    open suspend fun setupRequest(api: LastFM){
        this.api = api
    }

    protected abstract suspend fun manageResponse(response: Response<T>)

    open fun shouldUpdate(): Boolean = true

    suspend fun run() {
        if (shouldUpdate()) {
            var response: Response<T>? = null
            try {
                response = call()
            } finally {
                if (response != null && response.isSuccessful) {
                    manageResponse(response)
                }
            }
        }
    }

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