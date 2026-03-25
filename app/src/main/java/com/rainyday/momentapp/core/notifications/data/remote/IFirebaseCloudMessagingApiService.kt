package com.rainyday.momentapp.core.notifications.data.remote

import com.rainyday.momentapp.core.notifications.data.remote.dto.SaveTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IFirebaseCloudMessagingApiService {
    @POST("/tokens")
    suspend fun saveToken(@Body tokenRequest: SaveTokenRequest): Response<Unit>
}