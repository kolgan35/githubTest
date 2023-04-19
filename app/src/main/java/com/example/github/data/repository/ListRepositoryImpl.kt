package com.example.github.data.repository

import com.example.github.data.networking.Api
import com.example.github.data.models.*
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.repository.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val api: Api
) : ListRepository {

    override suspend fun getUsers(title: String): List<GitHubItem.User> {
        return withContext(Dispatchers.IO) {
                api.getUsersList(title).items.toUser()
        }
    }

    override suspend fun getRepositories(title: String): List<GitHubItem.Repository> {
        return withContext(Dispatchers.IO) {
                api.getRepositoriesList(title).items.toRepository()
        }
    }
}