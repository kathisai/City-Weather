package com.prathap.weather.network

import com.prathap.weather.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp Interceptor for Retrofit 2 to add appid for all calls
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appid", Constants.API_KEY)
                        .build()
                )
                .build()
        )
    }
}