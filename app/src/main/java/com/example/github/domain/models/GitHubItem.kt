package com.example.github.domain.models

import com.squareup.moshi.Json

sealed class GitHubItem() {
    abstract fun getViewType(): TypeItem

    data class User(
        val avatar: String?,
        val login: String?,
        val score: String?,
        val htmlUrl: String?
    ): GitHubItem() {
        override fun getViewType(): TypeItem = TypeItem.User
    }

    data class Repository(
        val name: String,
        val forksCount: Int,
        val description: String?,
        val owner: Owner
    ): GitHubItem() {
        override fun getViewType(): TypeItem = TypeItem.Repository
    }
}
