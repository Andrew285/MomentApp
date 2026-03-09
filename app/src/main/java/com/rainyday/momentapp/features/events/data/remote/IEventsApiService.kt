package com.rainyday.momentapp.features.events.data.remote

import com.rainyday.momentapp.core.data.models.ApiResponse
import com.rainyday.momentapp.features.events.data.remote.dto.request.CreateEventRequest
import com.rainyday.momentapp.features.events.data.remote.dto.response.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IEventsApiService {

    @GET("events")
    suspend fun getEvents(): Response<ApiResponse<List<EventResponse>>>

    @POST("events")
    suspend fun createEvent(@Body event: CreateEventRequest): Response<ApiResponse<EventResponse>>
}