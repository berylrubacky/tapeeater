package com.bsrubacky.tapeeater.api

import com.bsrubacky.tapeeater.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


abstract class DataOrchestrator<T> {
    val userAgent = "TapeEater/"+BuildConfig.VERSION_NAME+System.getProperty("http.agent")

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            chain.proceed(
                request.newBuilder()
                    .header("User-Agent", userAgent)
                    .build()
            )
        }
        .build()
    abstract val retrofit: Retrofit
    abstract suspend fun requestData(vararg requests: ApiRequest<T,*>): OrchestratorResponse<Int>
}
