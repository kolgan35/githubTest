package com.example.github.data.models

import com.example.github.domain.models.Content
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ContentDTO(
    @Json(name = "name")
    val name: String,
    @Json(name = "path")
    val path: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "type")
    val type: String
)

fun List<ContentDTO>.toContent(): List<Content> {
    val newList = arrayListOf<Content>()
    this.forEach {
        newList.add(
            Content(
                name = it.name,
                path = it.path,
                htmlUrl = it.htmlUrl,
                type = it.type
            )
        )
    }
    return newList
}
