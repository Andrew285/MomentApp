package com.rainyday.momentapp.features.events.domain.repository

import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import kotlinx.coroutines.flow.Flow

interface IEventsRemoteRepository {
    fun getEvents(): Flow<com.rainyday.momentapp.core.data.models.Result<List<Event>>>

    suspend fun getEventById(id: String): com.rainyday.momentapp.core.data.models.Result<Event>

    suspend fun createEvent(params: EventParamsRequest.CreateEventParamsReq): com.rainyday.momentapp.core.data.models.Result<Event>

    suspend fun updateEvent(params: EventParamsRequest.UpdateEventParamsReq): com.rainyday.momentapp.core.data.models.Result<Event>

    suspend fun deleteEvent(id: String): com.rainyday.momentapp.core.data.models.Result<Event>
}