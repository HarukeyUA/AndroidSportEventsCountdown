package com.nazar.assignment.di

import com.nazar.assignment.core.data.datasource.SportRemoteDataSourceImpl
import com.nazar.assignment.core.domain.datasource.SportsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDatasourceModule {

    @Binds
    @Singleton
    abstract fun bindSportsRemoteDataSource(remoteDataSourceImpl: SportRemoteDataSourceImpl): SportsRemoteDataSource
}
