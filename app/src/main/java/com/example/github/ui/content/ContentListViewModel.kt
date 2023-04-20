package com.example.github.ui.content

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.domain.models.Content
import com.example.github.domain.models.GitHubItem
import com.example.github.domain.use_case.GetContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val contentUseCase: GetContentUseCase
): ViewModel() {

    val dirStack = Stack<String>()

    private val _contentLive = MutableLiveData<List<Content>>()
    val contentLive: LiveData<List<Content>>
    get() = _contentLive

    private val _failLive = MutableLiveData<Pair<String, Boolean>>()
    val failLive: LiveData<Pair<String, Boolean>>
    get() = _failLive

    fun getContent(owner: String, repo: String, path: String?) {
        viewModelScope.launch {
                val result = contentUseCase.execute(owner, repo, path)
                when(result) {
                    is com.example.github.domain.models.Result.Success<*> -> {
                        val r = result.data as List<Content>
                        _failLive.postValue(Pair("", false))
                        _contentLive.postValue(r)
                    }
                    is com.example.github.domain.models.Result.Error -> {
                        _failLive.postValue(Pair(result.message, true))
                    }
                }
        }
    }

    fun pushDir(dir: String) {
        dirStack.push(dir)
    }
    fun popDir(): String? {
        return if (dirStack.size >= 1) {
            dirStack.pop()
        } else {
            null
        }
    }
    fun peepDir(): String? {
        return if (dirStack.isNotEmpty()) {
            dirStack.peek()
        } else {
            null
        }
    }
}