package com.javadsh98.movie.di

import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.repository.RepositoryImpl
import com.javadsh98.movie.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getIRepo(repositoryImpl: RepositoryImpl): IRepository

}