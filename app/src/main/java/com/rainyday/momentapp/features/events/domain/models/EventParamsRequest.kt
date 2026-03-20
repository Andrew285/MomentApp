package com.rainyday.momentapp.features.events.domain.models

sealed class EventParamsRequest {
    data class CreateEventParamsReq(
        val title: String,
        val description: String,
        val date: Long,
    ): EventParamsRequest()

    data class UpdateEventParamsReq(
        val id: String,
        val title: String,
        val description: String,
        val date: Long,
    ): EventParamsRequest()
}