package com.dicodingeventstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dicodingeventstracker.data.remote.response.RemoteDataSource
import com.dicodingeventstracker.data.remote.retrofit.NetworkService

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideRemoteDataSource(networkService: NetworkService):RemoteDataSource{
        return RemoteDataSource(networkService)
    }

}