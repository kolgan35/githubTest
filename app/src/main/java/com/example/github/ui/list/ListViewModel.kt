package com.example.github.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.data.models.toRepository
import com.example.github.data.models.toUser
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.use_case.GetRepositoryListUseCase
import com.example.github.domain.use_case.GetUsersListUseCase
import com.example.github.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val usersUseCase: GetUsersListUseCase,
    private val repoUseCase: GetRepositoryListUseCase
) : ViewModel() {
    private val _itemsListLive = MutableLiveData<List<GitHubItem>>()
    val itemsList: LiveData<List<GitHubItem>>
        get() = _itemsListLive

    private val _searchLive = MutableLiveData<Boolean>()
    val searchLive: LiveData<Boolean>
        get() = _searchLive
    private val _failLive = SingleLiveEvent<Pair<String, Boolean>>()
    val failLive: LiveData<Pair<String, Boolean>>
        get() = _failLive


    fun getInfo(title: String) {
        _failLive.postValue(Pair("", false))
        viewModelScope.launch {
            kotlin.runCatching {
                _searchLive.postValue(true)
                val getUserTask = async {
                    blockTry {
                        usersUseCase.execute(title)
                    }
                }
                val getRepoTask = async {
                    blockTry {
                        repoUseCase.execute(title)
                    }
                }
                val users = getUserTask.await().sortedBy { it.login }
                val repos = getRepoTask.await().sortedBy { it.name }

                val resultList = mutableListOf<GitHubItem>()

                resultList.addAll(users)
                resultList.addAll(repos)
                _itemsListLive.postValue(resultList)
            }.onSuccess {
                _searchLive.postValue(false)
            }.onFailure {
                _failLive.postValue(Pair(it.message?: "", true))
                _searchLive.postValue(false)
            }
        }
    }

    private suspend fun <T> blockTry(block: suspend () -> List<T>): List<T> {
        return try {
            block.invoke()
        } catch (e: Exception) {
            _failLive.postValue(Pair(e.message?: "", true))
            emptyList()
        }
    }

}