package com.rainyday.momentapp.features.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.core.data.models.Result
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest
import com.rainyday.momentapp.features.events.domain.usecases.CreateEventUseCase
import com.rainyday.momentapp.features.events.domain.usecases.DeleteEventUseCase
import com.rainyday.momentapp.features.events.domain.usecases.GetActiveEvents
import com.rainyday.momentapp.features.events.domain.usecases.GetEventByIdUseCase
import com.rainyday.momentapp.features.events.domain.usecases.UpdateEventUseCase
import com.rainyday.momentapp.features.events.ui.intents.EventsListIntent
import com.rainyday.momentapp.features.events.ui.state.AddOrUpdateEventUiState
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getActiveEvents: GetActiveEvents,
    private val createEventUseCase: CreateEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val getEventByIdUseCase: GetEventByIdUseCase,
): ViewModel() {

    private val _eventsUiState = MutableStateFlow(EventsUiState())
    val eventsUiState: StateFlow<EventsUiState> = _eventsUiState.asStateFlow()

    private val _addOrUpdateEventUiState = MutableStateFlow(AddOrUpdateEventUiState())
    val addOrUpdateEventUiState: StateFlow<AddOrUpdateEventUiState> = _addOrUpdateEventUiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<EventsSideEffect>()
    val sideEffects: SharedFlow<EventsSideEffect> = _sideEffects.asSharedFlow()

    init {
        loadEvents()
    }

    fun handleIntent(intent: EventsListIntent) {
        when (intent) {
            is EventsListIntent.PreLoadEventBeforeEditing -> preLoadEvent(intent.eventId)
            is EventsListIntent.LoadEvents -> loadEvents()
            is EventsListIntent.RefreshEvents -> refreshEvents()
            is EventsListIntent.SaveEvent -> saveEvent(intent.params)
            is EventsListIntent.UpdateTitle -> updateTitle(intent.title)
            is EventsListIntent.UpdateDescription -> updateDescription(intent.description)
            is EventsListIntent.UpdateDate -> updateDate(intent.date)
        }
    }

    fun preLoadEvent(eventId: String) {
        viewModelScope.launch {
            try {
                val result = getEventByIdUseCase(eventId)
                when (result) {
                    is Result.Success -> {
                        _addOrUpdateEventUiState.update {
                            it.copy(
                                title = result.data.title,
                                description = result.data.description,
                                image = "",
                                dateTimeInMillis = result.data.date,
                                dateTimeDisplay = "",
                                location = "",
                                dateError = "",
                            )
                        }
                    }
                    is Result.Error -> {
                        _addOrUpdateEventUiState.update {
                            it.copy(
                                title = "",
                                description = "",
                                image = "",
                                dateTimeInMillis = 0,
                                dateTimeDisplay = "",
                                location = "",
                                dateError = "",
                            )
                        }
                    }
                    is Result.Loading -> Unit
                }
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
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
        _addOrUpdateEventUiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun updateDescription(description: String) {
        _addOrUpdateEventUiState.update {
            it.copy(
                description = description
            )
        }
    }

    fun updateDate(date: Long) {
        _addOrUpdateEventUiState.update {
            it.copy(
                dateTimeInMillis = date
            )
        }
    }

    fun saveEvent(params: EventParamsRequest) {
        when (params) {
            is EventParamsRequest.CreateEventParamsReq -> {
                createEvent(params)
            }
            is EventParamsRequest.UpdateEventParamsReq -> {
                updateEvent(params)
            }
        }
    }

    fun createEvent(createEventParams: EventParamsRequest.CreateEventParamsReq) {
        viewModelScope.launch {
            _eventsUiState.update { it.copy(isLoading = true) }

            try {
                when (val result = createEventUseCase(createEventParams)) {
                    is Result.Success -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = null) }
                        _addOrUpdateEventUiState.update{ AddOrUpdateEventUiState() }
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

    fun updateEvent(params: EventParamsRequest.UpdateEventParamsReq) {
        viewModelScope.launch {
            _eventsUiState.update { it.copy(isLoading = true) }

            try {
                when (val result = updateEventUseCase(params)) {
                    is Result.Success -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = null) }
                        _addOrUpdateEventUiState.update{ AddOrUpdateEventUiState() }
                        _sideEffects.emit(EventsSideEffect.EventUpdatedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = result.message) }
                        _sideEffects.emit(EventsSideEffect.EventUpdatedFailed)
                    }
                    is Result.Loading -> Unit
                }
            } catch (e: Exception) {
                _sideEffects.emit(EventsSideEffect.UnknownError(e.message.toString()))
            }
        }
    }

    fun deleteEvent(id: String) {
        viewModelScope.launch {
            _eventsUiState.update { it.copy(isLoading = true) }

            try {
                when (val result = deleteEventUseCase(id)) {
                    is Result.Success -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = null) }
                        _addOrUpdateEventUiState.update{ AddOrUpdateEventUiState() }
                        _sideEffects.emit(EventsSideEffect.EventDeletedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
                        _eventsUiState.update { it.copy(isLoading = false, error = result.message) }
                        _sideEffects.emit(EventsSideEffect.EventDeletedFailed)
                    }
                    is Result.Loading -> Unit
                }
            } catch (e: Exception) {
                _sideEffects.emit(EventsSideEffect.UnknownError(e.message.toString()))
            }
        }
    }
}