package com.rainyday.momentapp.features.events.domain.repository

import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface IEventsRepository {
    suspend fun getEvents(): Flow<Result<List<Event>>>
}