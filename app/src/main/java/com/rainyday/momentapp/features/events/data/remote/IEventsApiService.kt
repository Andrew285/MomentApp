package com.rainyday.momentapp.features.events.data.remote

import com.rainyday.momentapp.core.data.models.ApiResponse
import com.rainyday.momentapp.features.events.data.remote.dto.request.CreateEventRequest
import com.rainyday.momentapp.features.events.data.remote.dto.request.UpdateEventRequest
import com.rainyday.momentapp.features.events.data.remote.dto.response.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IEventsApiService {

    @GET("events")
    suspend fun getEvents(): Response<ApiResponse<List<EventResponse>>>

    @GET("events/{eventId}")
    suspend fun getEventById(@Path("eventId") eventId: String): Response<ApiResponse<EventResponse>>

    @POST("events")
    suspend fun createEvent(@Body event: CreateEventRequest): Response<ApiResponse<EventResponse>>

    @PUT("events/{eventId}")
    suspend fun updateEvent(
        @Path("eventId") eventId: String,
        @Body event: UpdateEventRequest
    ): Response<ApiResponse<EventResponse>>

    @DELETE("events/{eventId}")
    suspend fun deleteEvent(
        @Path("eventId") eventId: String,
    ): Response<ApiResponse<EventResponse>>
}