package com.rainyday.momentapp.core.notifications.di

import com.rainyday.momentapp.core.notifications.data.FCMRepositoryImpl
import com.rainyday.momentapp.core.notifications.data.remote.IFirebaseCloudMessagingApiService
import com.rainyday.momentapp.core.notifications.domain.repository.IFCMRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindFCMRepository(impl: FCMRepositoryImpl): IFCMRepository

    companion object {
        @Provides
        @Singleton
        fun providesFCMService(retrofit: Retrofit): IFirebaseCloudMessagingApiService {
            return retrofit.create(IFirebaseCloudMessagingApiService::class.java)
        }
    }
}