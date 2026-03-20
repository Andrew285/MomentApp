package com.rainyday.momentapp.features.events.data.repository

import com.google.firebase.Timestamp
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.data.mapper.toDomain
import com.rainyday.momentapp.features.events.data.remote.IEventsApiService
import com.rainyday.momentapp.features.events.data.remote.dto.request.CreateEventRequest
import com.rainyday.momentapp.features.events.data.remote.dto.request.UpdateEventRequest
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import com.rainyday.momentapp.features.events.domain.repository.IEventsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class EventsRemoteRepositoryImpl @Inject constructor(
    private val apiInstance: IEventsApiService
) : IEventsRemoteRepository {

    override fun getEvents(): Flow<Result<List<Event>>> = flow {
        emit(Result.Loading)

        try {
            val response = apiInstance.getEvents()
            if (response.isSuccessful && response.body()?.success == true) {
                val events = response.body()!!.data!!.map { it.toDomain() }
                emit(Result.Success(events))
            } else {
                emit(
                    Result.Error(
                        response.body()?.error ?: "Unknown error",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Result.Error(
                    e.message ?: "Network error"
                )
            )
        }
    }

    override suspend fun getEventById(id: String): Result<Event> {
        return try {
            val eventResponse = apiInstance.getEventById(id)
            if (eventResponse.isSuccessful && eventResponse.body()?.success == true) {
                Result.Success(eventResponse.body()?.data!!.toDomain())
            }
            else {
                Result.Error(
                    eventResponse.body()?.error ?: "Unknown error",
                    eventResponse.code()
                )
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    override suspend fun createEvent(params: EventParamsRequest.CreateEventParamsReq): Result<Event> {
        return try {
            val request = CreateEventRequest(
                title = params.title,
                description = params.description,
                image = "",
                dateTime = Timestamp(Date(params.date)),
                location = ""
            )

            val response = apiInstance.createEvent(request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.Success(
                    data = response.body()!!.data!!.toDomain()
                )
            } else {
                Result.Error(
                    message = "Event creation is failed"
                )
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Network error"
            )
        }
    }

    override suspend fun updateEvent(params: EventParamsRequest.UpdateEventParamsReq): Result<Event> {
        return try {
            val request = UpdateEventRequest(
                id = params.id,
                title = params.title,
                description = params.description,
                image = "",
                dateTime = Timestamp(Date(params.date)),
                location = ""
            )

            val response = apiInstance.updateEvent(params.id, request)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.Success(
                    data = response.body()!!.data!!.toDomain()
                )
            } else {
                Result.Error(
                    message = "Event update is failed"
                )
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Network error"
            )
        }
    }

    override suspend fun deleteEvent(id: String): Result<Event> {
        return try {
            val response = apiInstance.deleteEvent(id )
            if (response.isSuccessful && response.body()?.success == true) {
                Result.Success(
                    data = response.body()!!.data!!.toDomain()
                )
            } else {
                Result.Error(
                    message = "Event deletion is failed"
                )
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Network error"
            )
        }
    }
}