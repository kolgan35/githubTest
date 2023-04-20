package com.example.github.domain.models

sealed class Result<out T : Any> {
    data class Success<out T: Any>(val data: List<T>): Result<List<T>>()
    data class Error(val message: String): Result<String>()
}
