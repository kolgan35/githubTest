package com.example.github.data.config

import com.example.github.BuildConfig
import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = BuildConfig.AUTH_URI
    const val TOKEN_URI = BuildConfig.TOKEN_URI
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = BuildConfig.SCOPE

    const val CLIENT_ID = BuildConfig.CLIENT_ID
    const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    const val CALLBACK_URL = BuildConfig.CALLBACK_URL
}