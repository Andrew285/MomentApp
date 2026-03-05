package com.rainyday.momentapp.features.events.data.repository

import android.util.Log
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.core.data.network.FirebaseFunctionsApi
import com.rainyday.momentapp.features.events.data.mapper.toDomain
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.repository.IEventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val apiInstance: FirebaseFunctionsApi
) : IEventsRepository {

    override suspend fun getEvents(): Flow<Result<List<Event>>> = flow {
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
}