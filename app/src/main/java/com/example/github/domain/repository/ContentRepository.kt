package com.example.github.domain.repository

import com.example.github.data.models.ContentDTO
import com.example.github.domain.models.Content

interface ContentRepository {

    suspend fun getContent(owner: String, repo: String, path: String?): List<Content>

}