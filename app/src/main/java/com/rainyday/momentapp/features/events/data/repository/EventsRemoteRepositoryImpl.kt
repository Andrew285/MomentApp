package com.rainyday.momentapp.features.events.data.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.data.mapper.toDomain
import com.rainyday.momentapp.features.events.data.remote.IEventsApiService
import com.rainyday.momentapp.features.events.data.remote.dto.request.CreateEventRequest
import com.rainyday.momentapp.features.events.domain.models.Event
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
            Log.d("NETWORK", "Response is retrieved successfully")
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

    override suspend fun createEvent(event: Event): Result<Event> {
        return try {
            val request = CreateEventRequest(
                title = event.title,
                description = event.description,
                image = "",
                dateTime = Timestamp(Date(event.date)),
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
}