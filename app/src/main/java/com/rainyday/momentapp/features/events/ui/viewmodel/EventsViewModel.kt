package com.rainyday.momentapp.features.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.usecases.GetActiveEvents
import com.rainyday.momentapp.features.events.ui.state.EventsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getActiveEvents: GetActiveEvents
): ViewModel() {

    private val _eventsUiState = MutableStateFlow(EventsUiState())
    val eventsUiState: StateFlow<EventsUiState> = _eventsUiState.asStateFlow()

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            getActiveEvents()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _eventsUiState.update {
                                it.copy(
                                    events = result.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                        is Result.Error -> {
                            _eventsUiState.update {
                                it.copy(
                                    error = result.message,
                                    isLoading = false
                                )
                            }
                        }
                        is Result.Loading -> {
                            _eventsUiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
        }
    }
}