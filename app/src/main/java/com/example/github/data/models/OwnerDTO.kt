package com.example.github.data.models

import com.example.github.domain.models.Owner
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerDTO(
    @Json(name = "login")
    val login: String
)

fun OwnerDTO.toOwner(): Owner {
    return Owner(
        login = login
    )
}