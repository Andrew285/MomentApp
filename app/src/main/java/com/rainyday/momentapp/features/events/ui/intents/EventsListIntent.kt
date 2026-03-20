package com.rainyday.momentapp.features.events.ui.intents

import com.rainyday.momentapp.features.events.domain.models.Event
import com.rainyday.momentapp.features.events.domain.models.EventParamsRequest

sealed class EventsListIntent() {
    data object LoadEvents: EventsListIntent()
    data object RefreshEvents: EventsListIntent()
    data class SaveEvent(val params: EventParamsRequest): EventsListIntent()
    data class UpdateTitle(val title: String): EventsListIntent()
    data class UpdateDescription(val description: String): EventsListIntent()
    data class UpdateDate(val date: Long): EventsListIntent()
    data class PreLoadEventBeforeEditing(val eventId: String): EventsListIntent()
}