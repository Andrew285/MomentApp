package com.rainyday.momentapp.features.events.ui.state

import com.rainyday.momentapp.features.events.domain.models.Event

sealed interface EventDetailsUiState {
    data object Empty: EventDetailsUiState
    data object Loading: EventDetailsUiState
    data class Success(val event: Event): EventDetailsUiState
    data class Error(val message: String): EventDetailsUiState
}