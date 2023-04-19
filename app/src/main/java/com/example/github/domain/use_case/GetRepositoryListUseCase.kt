package com.example.github.domain.use_case

import com.example.github.data.models.RepositoryDTO
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.repository.ListRepository
import javax.inject.Inject

class GetRepositoryListUseCase @Inject constructor(
    private val repo: ListRepository
) {
    suspend fun execute(title: String): List<GitHubItem.Repository> {
        return repo.getRepositories(title)
    }
}