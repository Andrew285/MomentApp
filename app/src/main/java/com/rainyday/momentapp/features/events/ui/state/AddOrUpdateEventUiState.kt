package com.rainyday.momentapp.features.events.ui.state

import com.rainyday.momentapp.features.events.domain.models.Event

sealed interface AddOrUpdateEventUiState {
    data object Loading: AddOrUpdateEventUiState
    data class Success(val event: Event): AddOrUpdateEventUiState
    data class Error(val message: String): AddOrUpdateEventUiState
}