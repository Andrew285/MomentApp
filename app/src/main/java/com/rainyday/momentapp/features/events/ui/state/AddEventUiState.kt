package com.rainyday.momentapp.features.events.ui.state

data class AddEventUiState(
    val title: String = "",
    val description: String = "",
    val image: String? = null,
    val dateTimeInMillis: Long = 0,
    val dateTimeDisplay: String = "",
    val location: String? = null,
    val dateError: String? = "",
)