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
import com.rainyday.momentapp.features.events.ui.state.EventFormState
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

    private val _eventsUiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val eventsUiState: StateFlow<EventsUiState> = _eventsUiState.asStateFlow()

    private val _addOrUpdateEventUiState = MutableStateFlow<AddOrUpdateEventUiState>(AddOrUpdateEventUiState.Loading)
    val addOrUpdateEventUiState: StateFlow<AddOrUpdateEventUiState> = _addOrUpdateEventUiState.asStateFlow()

    private val _eventFormState = MutableStateFlow(EventFormState())
    val eventFormState: StateFlow<EventFormState> = _eventFormState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<EventsSideEffect>()
    val sideEffects: SharedFlow<EventsSideEffect> = _sideEffects.asSharedFlow()

    init {
        loadEvents()
    }

    fun handleIntent(intent: EventsListIntent) {
        when (intent) {
            is EventsListIntent.PreLoadEventBeforeEditing -> preLoadEvent(intent.eventId)
            is EventsListIntent.PreLoadEventBeforeCreating -> loadEmptyEvent()
            is EventsListIntent.LoadEvents -> loadEvents()
            is EventsListIntent.RefreshEvents -> refreshEvents()
            is EventsListIntent.SaveEvent -> saveEvent(intent.params)
            is EventsListIntent.DeleteEvent -> deleteEvent(intent.id)
            is EventsListIntent.UpdateTitle -> updateTitle(intent.title)
            is EventsListIntent.UpdateDescription -> updateDescription(intent.description)
            is EventsListIntent.UpdateDate -> updateDate(intent.date)
        }
    }

    fun preLoadEvent(eventId: String) {
        viewModelScope.launch {
            try {

                when (val result = getEventByIdUseCase(eventId)) {
                    is Result.Success -> {
                        val resultEvent = result.data
                        _addOrUpdateEventUiState.value = AddOrUpdateEventUiState.Success(
                            event = Event(
                                id = resultEvent.id,
                                title = resultEvent.title,
                                description = resultEvent.description,
                                date = resultEvent.date
                            )
                        )

                        _eventFormState.value = EventFormState(
                            id = resultEvent.id,
                            title = resultEvent.title,
                            description = resultEvent.description,
                            dateInMillis = resultEvent.date
                        )
                    }
                    is Result.Error -> {
                        _addOrUpdateEventUiState.value = AddOrUpdateEventUiState.Error(result.message)
                    }
                    is Result.Loading -> {
                        _addOrUpdateEventUiState.value = AddOrUpdateEventUiState.Loading
                    }
                }
            } catch (e: Exception) {
                _addOrUpdateEventUiState.value = AddOrUpdateEventUiState.Error(e.message.toString())
            }
        }
    }

    fun loadEmptyEvent() {
        _addOrUpdateEventUiState.value = AddOrUpdateEventUiState.Success(
            event = Event(
                id = "",
                title = "",
                description = "",
                date = 0,
            )
        )
        _eventFormState.value = EventFormState()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _eventsUiState.value = EventsUiState.Loading

            getActiveEvents()
                .collect { result ->
                    when (result) {
                        is Result.Success -> _eventsUiState.value = EventsUiState.Success(result.data, false)
                        is Result.Error -> _eventsUiState.value = EventsUiState.Error(result.message)
                        is Result.Loading -> _eventsUiState.value = EventsUiState.Loading
                    }
                }
        }
    }

    fun refreshEvents() {
        viewModelScope.launch {
            _eventsUiState.value = EventsUiState.Refreshing

            getActiveEvents()
                .collect { result ->
                    when (result) {
                        is Result.Success -> _eventsUiState.value = EventsUiState.Success(result.data, false)
                        is Result.Error -> _eventsUiState.value = EventsUiState.Error(result.message)
                        is Result.Loading -> _eventsUiState.value = EventsUiState.Loading
                    }
                }
        }
    }

    fun updateTitle(title: String) {
        _eventFormState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _eventFormState.update { it.copy(description = description) }
    }

    fun updateDate(date: Long) {
        _eventFormState.update { it.copy(dateInMillis = date) }
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
            try {
                when (val result = createEventUseCase(createEventParams)) {
                    is Result.Success -> {
                        _sideEffects.emit(EventsSideEffect.EventAddedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
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
            try {
                when (val result = updateEventUseCase(params)) {
                    is Result.Success -> {
                        _sideEffects.emit(EventsSideEffect.EventUpdatedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
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
            try {
                when (val result = deleteEventUseCase(id)) {
                    is Result.Success -> {
                        _sideEffects.emit(EventsSideEffect.EventDeletedSuccessfully)
                        _sideEffects.emit(EventsSideEffect.ReloadEvents)
                    }
                    is Result.Error -> {
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