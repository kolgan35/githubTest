package com.example.github.data.networking

import com.example.github.data.config.Storage
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val accessToken: Storage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = Storage.ACCESS_TOKEN
        val url = original.newBuilder()
            .addHeader("Authorization", "token $token").build()
        return chain.proceed(url)
    }
}