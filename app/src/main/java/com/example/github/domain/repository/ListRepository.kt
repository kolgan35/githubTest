package com.example.github.domain.repository

import com.example.github.data.models.ContentDTO
import com.example.github.data.models.RepositoryDTO
import com.example.github.data.models.UserDTO
import com.example.github.domain.models.GitHubItem

interface ListRepository {

    suspend fun getUsers(title: String): List<GitHubItem.User>

    suspend fun getRepositories(title: String): List<GitHubItem.Repository>


}