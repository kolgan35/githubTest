package com.example.github.data.repository

import android.net.Uri
import com.example.github.data.config.AuthConfig
import com.example.github.data.config.Storage
import com.example.github.domain.repository.AuthRepository
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

): AuthRepository {

    override fun getAuthRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    override fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
            when {
                response != null -> {
                     Storage.ACCESS_TOKEN = response.accessToken.orEmpty()
                    onComplete()
                }
                else -> onError(ex.toString())
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }
}