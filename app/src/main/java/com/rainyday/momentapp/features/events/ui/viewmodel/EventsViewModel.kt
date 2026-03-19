package com.rainyday.momentapp.features.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.usecases.CreateEventUseCase
import com.rainyday.momentapp.features.events.domain.usecases.GetActiveEvents
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.AddEventUiState
import com.rainyday.momentapp.features.events.ui.state.EventsSideEffect
import com.rainyday.momentapp.features.events.ui.state.EventsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getActiveEvents: GetActiveEvents,
    private val createEventUseCase: CreateEventUseCase,
): ViewModel() {

    private val _eventsUiState = MutableStateFlow(EventsUiState())
    val eventsUiState: StateFlow<EventsUiState> = _eventsUiState.asStateFlow()

    private val _addEventUiState = MutableStateFlow(AddEventUiState())
    val addEventUiState: StateFlow<AddEventUiState> = _addEventUiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<EventsSideEffect>()
    val sideEffects: SharedFlow<EventsSideEffect> = _sideEffects.asSharedFlow()

    init {
        loadEvents()
    }

    fun handleIntent(intent: EventsListIntent) {
        when (intent) {
            is EventsListIntent.LoadEvents -> loadEvents()
            is EventsListIntent.RefreshEvents -> refreshEvents()
            is EventsListIntent.AddEvent -> addEvent(intent.event)
            is EventsListIntent.UpdateTitle -> updateTitle(intent.title)
            is EventsListIntent.UpdateDescription -> updateDescription(intent.description)
            is EventsListIntent.UpdateDate -> updateDate(intent.date)
        }
    }

    fun loadEvents() {
        viewModelScope.launch {
            _eventsUiState.update {
                it.copy(
                    isLoading = true
                )
            }

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

    fun refreshEvents() {
        viewModelScope.launch {
            _eventsUiState.update {
                it.copy(
                    isRefreshing = true
                )
            }

            getActiveEvents()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _eventsUiState.update {
                                it.copy(
                                    events = result.data,
                                    isRefreshing = false,
                                    error = null
                                )
                            }
                        }
                        is Result.Error -> {
                            _eventsUiState.update {
                                it.copy(
                                    error = result.message,
                                    isRefreshing = false
                                )
                            }
                        }
                        is Result.Loading -> {
                            _eventsUiState.update {
                                it.copy(
                                    isRefreshing = true
                                )
                            }
                        }
                    }
                }
        }
    }

    fun updateTitle(title: String) {
        _addEventUiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun updateDescription(description: String) {
        _addEventUiState.update {
            it.copy(
                description = description
            )
        }
    }

    fun updateDate(date: Long) {
        _addEventUiState.update {
            it.copy(
                dateTimeInMillis = date
            )
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch {
            _eventsUiState.update { it.copy(isLoading = true) }

            try {
                when (val result = createEventUseCase(event)) {
                    is Result.Success -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = null) }
                        _addEventUiState.update{ AddEventUiState() }
                        _sideEffects.emit(EventsSideEffect.EventAddedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = result.message) }
                        _sideEffects.emit(EventsSideEffect.EventAddedFailed)
                    }
                    is Result.Loading -> Unit
                }
            } catch (e: Exception) {
                _sideEffects.emit(EventsSideEffect.UnknownError(e.message.toString()))
            }
        }
    }
}