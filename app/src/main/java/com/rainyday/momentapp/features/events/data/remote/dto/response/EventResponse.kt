package com.rainyday.momentapp.features.events.data.remote.dto.response

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val id: String,
    val title: String,
    val description: String,

    @SerializedName("dateTime")
    val date: Timestamp,
)