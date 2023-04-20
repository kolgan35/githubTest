package com.example.github.domain.repository


interface ContentRepository {

    suspend fun getContent(owner: String, repo: String, path: String?): com.example.github.domain.models.Result<Any>

}