package com.example.github.domain.repository

import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.Result

interface ListRepository {

    suspend fun getUsers(title: String): Result<Any>

    suspend fun getRepositories(title: String): Result<Any>


}