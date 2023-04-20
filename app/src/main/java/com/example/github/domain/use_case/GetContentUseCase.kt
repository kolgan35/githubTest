package com.example.github.domain.use_case

import com.example.github.domain.repository.ContentRepository
import javax.inject.Inject

class GetContentUseCase @Inject constructor(
    private val repository: ContentRepository
) {
    suspend fun execute(owner: String, repo: String, path: String?): com.example.github.domain.models.Result<Any> {
        return repository.getContent(owner, repo, path)
    }
}