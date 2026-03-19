package com.rainyday.momentapp.features.events.domain.models

data class Event(
    val id: String? = "",
    val title: String,
    val description: String,
    val date: Long,
) {
    val isToday = false
    val isUpcoming = false
}