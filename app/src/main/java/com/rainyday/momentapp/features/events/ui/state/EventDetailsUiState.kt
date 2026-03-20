package com.rainyday.momentapp.features.events.ui.state

import com.rainyday.momentapp.features.events.domain.models.Event

data class EventDetailsUiState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)