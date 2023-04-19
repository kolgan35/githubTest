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
): ContentRepository {
    override suspend fun getContent(owner: String, repo: String, path: String?): List<Content> {
            return withContext(Dispatchers.IO) {
//                try {
                    api.getRepoContent(owner, repo, path).toContent()
//                } catch (e: Exception) {
//                    Log.d("CONTENTERROR", e.toString())
//                    emptyList()
//                }
            }
    }
}