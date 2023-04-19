package com.example.github.data.models

import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.TypeItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "avatar_url")
    val avatar: String?,
    @Json(name = "login")
    val login: String?,
    @Json(name = "score")
    val score: String?,
    @Json(name = "html_url")
    val htmlUrl: String?
)

fun List<UserDTO>.toUser(): List<GitHubItem.User> {
    val newList = arrayListOf<GitHubItem.User>()
    this.forEach {
        newList.add(GitHubItem.User(
            avatar = it.avatar,
            login = it.login,
            score = it.score,
            htmlUrl = it.htmlUrl
        )
        )
    }
    return newList

}