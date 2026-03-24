package com.rainyday.momentapp.features.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.features.events.domain.usecases.GetEventByIdUseCase
import com.rainyday.momentapp.features.events.ui.state.EventDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.rainyday.momentapp.core.data.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Empty)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    fun getEventDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = EventDetailsUiState.Loading

            when (val eventResult = getEventByIdUseCase(id)) {
                is Result.Success -> _uiState.value = EventDetailsUiState.Success(eventResult.data)
                is Result.Error -> _uiState.value = EventDetailsUiState.Error(eventResult.message)
                is Result.Loading -> _uiState.value = EventDetailsUiState.Loading
            }
        }
    }
}