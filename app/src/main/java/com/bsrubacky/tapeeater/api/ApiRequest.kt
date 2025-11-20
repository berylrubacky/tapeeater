package com.bsrubacky.tapeeater.api

import retrofit2.Response


abstract class ApiRequest<T, V> {

    abstract val name: String

    abstract var api: T

    open suspend fun setupRequest(api: T){
        this.api = api
    }

    protected abstract suspend fun call(): Response<V>

    protected abstract suspend fun manageResponse(response: Response<V>)

    open fun shouldUpdate(): Boolean = true

    suspend fun run() {
        if (shouldUpdate()) {
            var response: Response<V>? = null
            try {
                response = call()
            } finally {
                if (response != null && response.isSuccessful) {
                    manageResponse(response)
                }
            }
        }
    }
}