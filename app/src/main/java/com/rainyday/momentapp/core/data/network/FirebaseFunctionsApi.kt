    package com.rainyday.momentapp.core.data.network

    import com.rainyday.momentapp.core.data.models.ApiResponse
    import com.rainyday.momentapp.features.events.data.models.EventResponse
    import retrofit2.Response
    import retrofit2.http.GET

    interface FirebaseFunctionsApi {
        @GET("events")
        suspend fun getEvents(): Response<ApiResponse<List<EventResponse>>>
    }