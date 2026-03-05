package com.rainyday.momentapp.features.events.ui.state

import com.rainyday.momentapp.features.events.domain.models.Event

data class EventsUiState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)