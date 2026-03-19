package com.rainyday.momentapp.features.events.ui.intents

import com.rainyday.momentapp.features.events.domain.models.Event

sealed class EventsListIntent() {
    data object LoadEvents: EventsListIntent()
    data object RefreshEvents: EventsListIntent()
    data class AddEvent(val event: Event): EventsListIntent()
    data class UpdateTitle(val title: String): EventsListIntent()
    data class UpdateDescription(val description: String): EventsListIntent()
    data class UpdateDate(val date: Long): EventsListIntent()
}