package com.example.github.data.repository

import android.util.Log
import com.example.github.data.networking.Api
import com.example.github.data.models.toContent
import com.example.github.domain.models.Content
import com.example.github.domain.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val api: Api
) : ContentRepository {
    override suspend fun getContent(
        owner: String,
        repo: String,
        path: String?
    ): com.example.github.domain.models.Result<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getRepoContent(owner, repo, path).toContent()
                com.example.github.domain.models.Result.Success(result)
            } catch (e: Exception) {
                com.example.github.domain.models.Result.Error(e.message.toString())
            }
        }
    }
}