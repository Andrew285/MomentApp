package com.rainyday.momentapp.features.auth.di

import com.rainyday.momentapp.features.auth.data.repository.AuthRepositoryImpl
import com.rainyday.momentapp.features.auth.domain.repository.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): IAuthRepository
}