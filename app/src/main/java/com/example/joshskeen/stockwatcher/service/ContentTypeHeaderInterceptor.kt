package com.example.joshskeen.stockwatcher.service

import okhttp3.Interceptor
import okhttp3.Response


private const val CONTENT_TYPE = "Content-Type"
private const val APPLICATION_JSON = "application/json"

class ContentTypeHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()
        return chain.proceed(request)
    }

}
