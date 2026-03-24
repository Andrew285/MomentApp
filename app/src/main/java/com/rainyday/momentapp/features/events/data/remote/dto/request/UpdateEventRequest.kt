package com.rainyday.momentapp.features.events.data.remote.dto.request

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class UpdateEventRequest(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("dateTime")
    val dateTime: Timestamp,

    @SerializedName("location")
    val location: String,
)