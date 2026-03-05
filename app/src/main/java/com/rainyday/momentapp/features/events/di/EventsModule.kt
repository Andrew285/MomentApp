package com.rainyday.momentapp.features.events.di

import com.rainyday.momentapp.features.events.data.repository.EventsRepositoryImpl
import com.rainyday.momentapp.features.events.domain.repository.IEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(impl: EventsRepositoryImpl) : IEventsRepository
}