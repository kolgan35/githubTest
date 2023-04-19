package com.example.github.data.models

import com.example.github.domain.models.GitHubItem
import com.example.github.domain.models.TypeItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RepositoryDTO(
    @Json(name = "name")
    val name: String,
    @Json(name = "forks_count")
    val forksCount: Int,
    @Json(name = "description")
    val description: String?,
    @Json(name = "owner")
    val owner: OwnerDTO
    )


fun List<RepositoryDTO>.toRepository(): List<GitHubItem.Repository> {
    val newList = arrayListOf<GitHubItem.Repository>()
    this.forEach {
        newList.add(
            GitHubItem.Repository(
                name = it.name,
                forksCount = it.forksCount,
                description = it.description,
                owner = it.owner.toOwner()
            )
        )
    }
    return newList
}
