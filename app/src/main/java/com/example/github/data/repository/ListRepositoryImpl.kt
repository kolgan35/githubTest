package com.example.github.data.repository

import com.example.github.data.networking.Api
import com.example.github.data.models.*
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.Result
import com.example.github.domain.repository.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val api: Api
) : ListRepository {

    override suspend fun getUsers(title: String): Result<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getUsersList(title).items.toUser()
                Result.Success(result)
            } catch (e: java.lang.Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    override suspend fun getRepositories(title: String): Result<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getRepositoriesList(title).items.toRepository()
                Result.Success(result)
            } catch (e: java.lang.Exception) {
                Result.Error(e.message.toString())
            }
        }
    }
}