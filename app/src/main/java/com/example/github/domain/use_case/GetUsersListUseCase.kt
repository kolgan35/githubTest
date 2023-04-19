package com.example.github.domain.use_case

import com.example.github.data.models.UserDTO
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.repository.ListRepository
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    private val repo: ListRepository
) {
    suspend fun execute(title: String): List<GitHubItem.User> {
        return repo.getUsers(title)
    }
}