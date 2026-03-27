package com.rainyday.momentapp.features.auth.ui.states

import com.rainyday.momentapp.features.auth.domain.models.AuthUser

sealed interface AuthUiState {
    data object Loading: AuthUiState
    data class Success(val data: AuthUser): AuthUiState
    data class Error(val message: String): AuthUiState
}