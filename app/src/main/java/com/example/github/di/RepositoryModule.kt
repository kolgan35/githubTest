package com.example.github.di

import com.example.github.data.repository.AuthRepositoryImpl
import com.example.github.data.repository.ContentRepositoryImpl
import com.example.github.data.repository.ListRepositoryImpl
import com.example.github.domain.repository.AuthRepository
import com.example.github.domain.repository.ContentRepository
import com.example.github.domain.repository.ListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideListRepo(repo: ListRepositoryImpl): ListRepository
    @Binds
    abstract fun provideContentRepo(repo: ContentRepositoryImpl): ContentRepository

    @Binds
    abstract fun provideAuthRepo(repo: AuthRepositoryImpl): AuthRepository

}