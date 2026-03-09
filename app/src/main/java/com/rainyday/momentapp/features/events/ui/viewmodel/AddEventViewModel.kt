package com.rainyday.momentapp.features.events.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainyday.momentapp.core.ui.utils.toFormattedDate
import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.usecases.CreateEventUseCase
import com.rainyday.momentapp.features.events.ui.state.AddEventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val createEventUseCase: CreateEventUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(AddEventUiState())
    val uiState: StateFlow<AddEventUiState> = _uiState.asStateFlow()

    fun updateDate(dateInMillis: Long) {
        _uiState.update {
            it.copy(
                dateTimeInMillis = dateInMillis,
                dateTimeDisplay = dateInMillis.toFormattedDate()
            )
        }
    }

    fun updateDescription(description: String) {
        _uiState.update {
            it.copy(
                description = description
            )
        }
    }

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun createEvent() {
        viewModelScope.launch {
            try {
                val event = Event(
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    date = _uiState.value.dateTimeInMillis
                )
                createEventUseCase(event)
            } catch (e: Exception) {
                Log.d("EVENT", e.message.toString())
            }
        }
    }
}