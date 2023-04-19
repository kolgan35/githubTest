package com.example.github.di

import com.example.github.data.repository.AuthRepositoryImpl
import com.example.github.data.repository.ContentRepositoryImpl
import com.example.github.data.repository.ListRepositoryImpl
import com.example.github.domain.repository.AuthRepository
import com.example.github.domain.repository.ContentRepository
import com.example.github.domain.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideListRepo(repo: ListRepositoryImpl): ListRepository {
        return repo
    }
    @Provides
    fun provideContentRepo(repo: ContentRepositoryImpl): ContentRepository {
        return repo
    }

    @Provides
    fun provideAuthRepo(repo: AuthRepositoryImpl): AuthRepository {
        return repo
    }

}