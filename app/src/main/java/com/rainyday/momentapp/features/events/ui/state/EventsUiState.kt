package com.rainyday.momentapp.features.events.ui.state

import com.rainyday.momentapp.features.events.domain.models.Event

sealed interface EventsUiState {
    data object Empty: EventsUiState
    data object Loading: EventsUiState
    data object Refreshing: EventsUiState
    data class Success(val events: List<Event>, val isRefreshing: Boolean): EventsUiState
    data class Error(val message: String): EventsUiState
}