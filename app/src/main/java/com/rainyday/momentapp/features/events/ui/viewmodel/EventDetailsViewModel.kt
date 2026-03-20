package com.rainyday.momentapp.features.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.features.events.domain.usecases.GetEventByIdUseCase
import com.rainyday.momentapp.features.events.ui.state.EventDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.rainyday.momentapp.core.data.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    fun getEventDetails(id: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }

            val eventResult = getEventByIdUseCase(id)
            when (eventResult) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            event = eventResult.data,
                            isLoading = false,
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = it.error
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }
}