package com.rainyday.momentapp.features.events.ui.state

data class EventFormState(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val dateInMillis: Long? = null,
)