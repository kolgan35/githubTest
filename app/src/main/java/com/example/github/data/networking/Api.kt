package com.example.github.data.networking

import com.example.github.data.models.BaseResponseDTO
import com.example.github.data.models.ContentDTO
import com.example.github.data.models.RepositoryDTO
import com.example.github.data.models.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/repositories")
    suspend fun getRepositoriesList(
        @Query("q") title: String
    ) : BaseResponseDTO<RepositoryDTO>

    @GET("search/users")
    suspend fun getUsersList(
        @Query("q") title: String
    ): BaseResponseDTO<UserDTO>

    @GET("/repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String?
    ): List<ContentDTO>
}