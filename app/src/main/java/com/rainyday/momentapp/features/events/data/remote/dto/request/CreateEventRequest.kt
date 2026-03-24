package com.rainyday.momentapp.features.events.data.remote.dto.request

import com.google.firebase.Timestamp

data class CreateEventRequest(
    val title: String,
    val description: String,
    val image: String,
    val dateTime: Timestamp,
    val location: String,
)