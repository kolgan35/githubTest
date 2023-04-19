package com.example.github.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseDTO<T>(
    @Json(name = "items")
    val items: List<T>
)
